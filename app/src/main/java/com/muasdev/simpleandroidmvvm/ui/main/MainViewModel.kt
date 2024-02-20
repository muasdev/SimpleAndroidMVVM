package com.muasdev.simpleandroidmvvm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenceHelper: PreferenceHelper
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.CheckUserAuth -> {
                viewModelScope.launch {
                    val isUserLogin = preferenceHelper.isUserLogin()
                    if (isUserLogin) {
                        val user = preferenceHelper.getUserLoginInfo()
                        _state.value = MainState(
                            userLogin = user
                        )
                    }
                }
            }
        }
    }
}