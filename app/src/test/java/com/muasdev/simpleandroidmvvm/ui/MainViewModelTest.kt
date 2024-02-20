package com.muasdev.simpleandroidmvvm.ui

import app.cash.turbine.test
import com.muasdev.simpleandroidmvvm.domain.model.UserLogin
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.ui.main.MainEvent
import com.muasdev.simpleandroidmvvm.ui.main.MainState
import com.muasdev.simpleandroidmvvm.ui.main.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @MockK
    private lateinit var preferenceHelper: PreferenceHelper

    @get:Rule
    val mockkRule = MockKRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserLoginInfo should be success`() {
        runTest {
            val userLogin = UserLogin(
                email = "muasdev@gmail.com",
                password = "pass",
                role = UserRole.USER
            )

            coEvery { preferenceHelper.isUserLogin() } returns true
            coEvery { preferenceHelper.getUserLoginInfo() } returns userLogin

            val viewModel = MainViewModel(preferenceHelper)
            viewModel.onEvent(MainEvent.CheckUserAuth)

            viewModel.state.test {
                assertEquals(MainState(userLogin = userLogin), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) { preferenceHelper.getUserLoginInfo() }
        }
    }

}