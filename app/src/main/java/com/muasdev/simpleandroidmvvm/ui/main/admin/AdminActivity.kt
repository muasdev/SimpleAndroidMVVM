package com.muasdev.simpleandroidmvvm.ui.main.admin

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
import com.muasdev.simpleandroidmvvm.databinding.ActivityAdminBinding
import com.muasdev.simpleandroidmvvm.ui.main.MainActivity
import com.muasdev.simpleandroidmvvm.ui.main.admin.adapter.UsersAdapter
import com.muasdev.simpleandroidmvvm.ui.main.admin.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private val viewModel: AdminViewModel by viewModels()

    private lateinit var usersAdapter: UsersAdapter

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.apply {
            btnLogout.setOnClickListener {
                viewModel.onEvent(AdminMainEvent.Logout)
            }
        }

        usersAdapter = UsersAdapter {
            val intent = Intent(
                this@AdminActivity,
                DetailActivity::class.java
            )
            intent.putExtra(DetailActivity.KEY_USER_ID, "${it.id}")
            startActivity(intent)
            finish()
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this@AdminActivity)
        binding.rvUsers.adapter = usersAdapter
        observeUsers()
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    if (state.users.isNotEmpty()) {
                        usersAdapter.submitList(state.users)
                    }
                    if (state.userLogout) {
                        val intent = Intent(
                            this@AdminActivity,
                            MainActivity::class.java
                        )
                        startActivity(intent)
                        finish()
                    }
                    if (state.errorMessage?.isNotEmpty() == true) {
                        Toast.makeText(
                            this@AdminActivity,
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


}