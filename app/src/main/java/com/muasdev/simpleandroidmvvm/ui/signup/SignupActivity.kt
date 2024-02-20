package com.muasdev.simpleandroidmvvm.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muasdev.simpleandroidmvvm.ui.main.MainActivity
import com.muasdev.simpleandroidmvvm.R
import com.muasdev.simpleandroidmvvm.databinding.ActivitySignupBinding
import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignUpViewModel by viewModels()

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            toMainPage()
        }
    }

    private fun toMainPage() {
        val intent = Intent(
            this@SignupActivity,
            MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.apply {
            btnSignup.setOnClickListener {
                val role = userRole()
                viewModel.onEvent(SignUpEvent.InsertUser(
                    user = User(
                        userName = edtUserName.text.toString(),
                        email = edtEmail.text.toString(),
                        password = edtPassword.text.toString(),
                        role = role,
                    )
                ))
            }

             btnBack.setOnClickListener {
                 viewModel.onEvent(SignUpEvent.BackToLoginPage)
             }
        }

        observeUser()
    }

    private fun userRole(): UserRole {
        binding.apply {
            val selectedOption: Int = rgRoleUser.checkedRadioButtonId
            val radioButton: RadioButton = findViewById(selectedOption)
            val role = if (radioButton.text.equals(getString(R.string.normal_user))) {
                UserRole.USER
            } else {
                UserRole.ADMIN
            }
            return role
        }
    }

    private fun observeUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    if (state.userSignUpSuccessfully == true) {
                        toMainPage()
                    }
                    if (state.backToPrevPage) {
                        toMainPage()
                    }
                    if (state.errorMessage?.isNotEmpty() == true) {
                        Toast.makeText(
                            this@SignupActivity,
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}