package com.muasdev.simpleandroidmvvm.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muasdev.simpleandroidmvvm.databinding.ActivityMainBinding
import com.muasdev.simpleandroidmvvm.domain.model.UserLogin
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import com.muasdev.simpleandroidmvvm.ui.login.LoginActivity
import com.muasdev.simpleandroidmvvm.ui.main.admin.AdminActivity
import com.muasdev.simpleandroidmvvm.ui.main.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.onEvent(MainEvent.CheckUserAuth)
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    if (state.userLogin != null) {
                        checkUserRole(state.userLogin)
                    } else {
                        val intent = Intent(
                            this@MainActivity,
                            LoginActivity::class.java
                        )
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun checkUserRole(user: UserLogin) {
        if (user.role == UserRole.USER) {
            toUserPage()
        } else {
            toAdminPage()
        }
    }

    private fun toUserPage() {
        val intent = Intent(
            this@MainActivity,
            UserActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun toAdminPage() {
        val intent = Intent(
            this@MainActivity,
            AdminActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}