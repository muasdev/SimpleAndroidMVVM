package com.muasdev.simpleandroidmvvm.ui.main.admin.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muasdev.simpleandroidmvvm.databinding.ActivityDetailBinding
import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import com.muasdev.simpleandroidmvvm.ui.main.admin.AdminActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    lateinit var user: User

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            toAdminPage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        val userId = intent.getStringExtra(KEY_USER_ID)
        userId?.let { id ->
            viewModel.onEvent(DetailEvent.GetUserById(id.toInt()))
        }

        binding.apply {
            btnDelete.setOnClickListener {
                val userData = viewModel.state.value.user
                userData?.let {
                    showDeleteDialog(
                        this@DetailActivity,
                        userData
                        )
                }

            }

            btnSave.setOnClickListener {
                val email = edtEmail.text.toString()
                val userName = edtUserName.text.toString()
                viewModel.onEvent(DetailEvent.UpdateUser(
                    user = User(
                        id = userId?.toInt(),
                        email= email,
                        userName = userName,
                        password = viewModel.state.value.user?.password ?: "",
                        role = viewModel.state.value.user?.role ?: UserRole.USER,
                    )
                ))
            }

            backToolbar.setOnClickListener {
                toAdminPage()
            }
        }

        observeState()
        observeErrorMessage()
    }

    private fun observeErrorMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMessage.collectLatest { state ->
                    if (state.errorMessage?.isNotEmpty() == true) {
                        Toast.makeText(
                            this@DetailActivity,
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.user != null) {
                        binding.apply {
                            edtEmail.setText(state.user.email)
                            edtUserName.setText(state.user.userName)
                        }
                    }
                    if (state.userUpdatedSuccessfully == true) {
                        toAdminPage()
                    }
                    if (state.userDeletedSuccessfully == true) {
                        toAdminPage()
                    }
                }
            }
        }
    }

    private fun toAdminPage() {
        val intent = Intent(
            this@DetailActivity,
            AdminActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun showDeleteDialog(
        context: Context,
        userData: User
    ) {
        val edtPassword = EditText(context)
        val dialog: AlertDialog = AlertDialog.Builder(context)
            .setTitle("Delete data")
            .setMessage("Enter your password to delete the data")
            .setView(edtPassword)
            .setPositiveButton("Delete"
            ) { _, _ ->
                val password = edtPassword.text.toString()
                viewModel.onEvent(DetailEvent.RemoveUser(
                    userData, password
                ))
            }
            .create()
        dialog.show()
    }

    companion object {
        const val KEY_USER_ID = "USER_ID"
    }
}