package com.muasdev.simpleandroidmvvm.ui.main.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muasdev.simpleandroidmvvm.databinding.ActivityUserBinding
import com.muasdev.simpleandroidmvvm.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserViewModel by viewModels()

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            closeApp()
        }
    }

    private fun closeApp() {
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.apply {
            btnLogout.setOnClickListener {
                viewModel.onEvent(UserEvent.Logout)
            }
        }

        observeUsers()
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    if (state.pagingData != null) {
                        val pagingAdapter = PlaceholderAdapter()
                        binding.apply {
                            rvPlaceholder.layoutManager = LinearLayoutManager(this@UserActivity)
                            rvPlaceholder.adapter = pagingAdapter
                        }
                        pagingAdapter.submitData(state.pagingData)
                    }
                    if (state.userLogout) {
                        val intent = Intent(
                            this@UserActivity,
                            MainActivity::class.java
                        )
                        startActivity(intent)
                        finish()
                    }
                    if (state.errorMessage?.isNotEmpty() == true) {
                        Toast.makeText(
                            this@UserActivity,
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}