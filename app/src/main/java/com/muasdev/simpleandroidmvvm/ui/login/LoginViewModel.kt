package com.muasdev.simpleandroidmvvm.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.domain.usecase.auth.UserLoginVerifyUseCase
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import com.muasdev.simpleandroidmvvm.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: UserLoginVerifyUseCase,
    private val preferenceHelper: PreferenceHelper
): ViewModel() {

    private val _state = MutableSharedFlow<LoginState>()
    val state: SharedFlow<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.UserLogin -> {
                viewModelScope.launch {
                    try {
                        if (event.userEmail.isBlank()) {
                            throw InvalidException("The email can't be empty")
                        }
                        if (!event.userEmail.isValidEmail()) {
                            throw InvalidException("The email not valid")
                        }
                        if (event.userPass.isBlank()) {
                            throw InvalidException("The password can't be empty")
                        }
                        val user = useCase(
                            event.userEmail,
                            event.userPass
                        ).firstOrNull()
                        _state.emit(LoginState(user = user))
                        user?.let { preferenceHelper.setUserIsLogin(it) }
                    } catch(e: InvalidException) {
                        _state.emit(LoginState(errorMessage = e.message))
                    }
                }
            }
            is LoginEvent.BackToMainPage -> {
                viewModelScope.launch {
                    _state.emit(LoginState(backToPrevPage = true))
                }
            }
        }
    }
}