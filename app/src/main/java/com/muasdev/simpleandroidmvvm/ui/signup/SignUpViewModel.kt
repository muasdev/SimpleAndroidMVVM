package com.muasdev.simpleandroidmvvm.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muasdev.simpleandroidmvvm.domain.usecase.auth.InsertUserUseCase
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
): ViewModel() {

    private val _state = MutableSharedFlow<SignUpState>()
    val state = _state

    fun onEvent(event: SignUpEvent) {
        when(event) {
            is SignUpEvent.InsertUser -> {
                viewModelScope.launch {
                    try {
                        insertUserUseCase(
                            event.user
                        )
                        _state.emit(SignUpState(userSignUpSuccessfully = true))
                    } catch(e: InvalidException) {
                        _state.emit(SignUpState(errorMessage = e.message))
                    }
                }
            }
            is SignUpEvent.BackToLoginPage -> {
                viewModelScope.launch {
                    _state.emit(SignUpState(backToPrevPage = true))
                }
            }
        }
    }
}