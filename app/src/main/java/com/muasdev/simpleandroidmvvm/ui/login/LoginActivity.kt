package com.muasdev.simpleandroidmvvm.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muasdev.simpleandroidmvvm.databinding.ActivityLoginBinding
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import com.muasdev.simpleandroidmvvm.ui.main.admin.AdminActivity
import com.muasdev.simpleandroidmvvm.ui.main.user.UserActivity
import com.muasdev.simpleandroidmvvm.ui.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.apply {
            btnSignup.setOnClickListener {
                toSignUpPage()
            }

            btnLogin.setOnClickListener {
                viewModel.onEvent(LoginEvent.UserLogin(
                    userEmail = edtEmail.text.toString(),
                    userPass = edtPassword.text.toString()
                ))
            }
        }

        observeState()
    }

    private fun toSignUpPage() {
        val intent = Intent(
            this@LoginActivity,
            SignupActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    if (state.user != null) {
                        if (state.user.role == UserRole.USER) {
                            toUserPage()
                        } else {
                            toAdminPage()
                        }
                    }
                    if (state.backToPrevPage) {
                        closeApp()
                    }
                    if (state.errorMessage?.isNotEmpty() == true) {
                        Toast.makeText(
                            this@LoginActivity,
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun toAdminPage() {
        val intent = Intent(
            this@LoginActivity,
            AdminActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun toUserPage() {
        val intent = Intent(
            this@LoginActivity,
            UserActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}