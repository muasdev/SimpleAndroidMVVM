package com.muasdev.simpleandroidmvvm.ui.main.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.domain.usecase.admin.GetUsersUseCase
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val preferenceHelper: PreferenceHelper
): ViewModel() {

    private val _state = MutableStateFlow(AdminMainState())
    val state = _state

    init {
        getUsers()
    }

    fun onEvent(event: AdminMainEvent) {
        when(event) {
            is AdminMainEvent.Logout -> {
                viewModelScope.launch {
                    preferenceHelper.setUserLogout()
                    _state.value = AdminMainState(userLogout = true)
                }
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                val user = getUsersUseCase().first()
                _state.emit(AdminMainState(users = user))
            } catch(e: InvalidException) {
                _state.value = AdminMainState(errorMessage = "Oops!. Something went wrong")
            }
        }
    }
}