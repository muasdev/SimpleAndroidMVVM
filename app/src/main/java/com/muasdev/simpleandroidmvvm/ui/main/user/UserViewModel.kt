package com.muasdev.simpleandroidmvvm.ui.main.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.domain.usecase.normal_user.GetAllPlaceholderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
    private val useCase: GetAllPlaceholderUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state

    fun onEvent(event: UserEvent) {
        when(event) {
            is UserEvent.Logout -> {
                viewModelScope.launch {
                    preferenceHelper.setUserLogout()
                    _state.value = UserState(userLogout = true)
                }
            }
        }
    }

    init {
        getPlaceholder()
    }

    private fun getPlaceholder() {
        viewModelScope.launch {
            useCase.invoke(1,10)
                .onEach { data ->
                    _state.value = UserState(pagingData = data)
                }
                .catch {
                    _state.value = UserState(errorMessage = it.message)
                }
                .launchIn(viewModelScope)
        }
    }
}
