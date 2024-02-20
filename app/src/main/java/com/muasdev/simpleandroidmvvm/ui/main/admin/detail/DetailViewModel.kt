package com.muasdev.simpleandroidmvvm.ui.main.admin.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.domain.usecase.admin.DeleteUserUseCase
import com.muasdev.simpleandroidmvvm.domain.usecase.admin.GetUserUseCase
import com.muasdev.simpleandroidmvvm.domain.usecase.admin.UpdateUserUseCase
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val preferenceHelper: PreferenceHelper
): ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state

    private val _errorMessage = MutableSharedFlow<DetailState>()
    val errorMessage = _errorMessage


    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.GetUserById -> {
                viewModelScope.launch {
                    try {
                        val user = getUserUseCase(
                            event.userId
                        ).first()
                        _state.emit(DetailState(user = user))
                    } catch(e: InvalidException) {
                        _errorMessage.emit(DetailState(errorMessage = e.message))
                    }
                }
            }
            is DetailEvent.UpdateUser -> {
                viewModelScope.launch {
                    try {
                        updateUserUseCase(
                            event.user
                        )
                        _state.emit(DetailState(userUpdatedSuccessfully = true))
                    } catch(e: InvalidException) {
                        _errorMessage.emit(DetailState(errorMessage = e.message))
                    }
                }
            }
            is DetailEvent.RemoveUser -> {
                viewModelScope.launch {
                    try {
                        val user = preferenceHelper.getUserLoginInfo()
                        when {
                            event.passwordAdmin.isBlank() -> {
                                throw InvalidException("The password is empty")
                            }
                            user.email == event.user.email -> {
                                throw InvalidException("You cannot delete your own account")
                            }
                            user.password == event.passwordAdmin -> {
                                deleteUserUseCase(
                                    event.user
                                )
                                _state.emit(DetailState(userDeletedSuccessfully = true))
                            }
                            else -> {
                                throw InvalidException("The password not match")
                            }
                        }
                    } catch(e: InvalidException) {
                        _errorMessage.emit(DetailState(errorMessage = e.message))
                    }
                }
            }
            is DetailEvent.BackToAdminPage -> {
                _state.value = DetailState(isNavigateToAdminPage = true)
            }
        }
    }
}