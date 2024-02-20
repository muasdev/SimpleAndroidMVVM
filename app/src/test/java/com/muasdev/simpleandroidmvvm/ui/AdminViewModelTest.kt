package com.muasdev.simpleandroidmvvm.ui

import app.cash.turbine.test
import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.domain.usecase.admin.GetUsersUseCase
import com.muasdev.simpleandroidmvvm.ui.main.admin.AdminMainState
import com.muasdev.simpleandroidmvvm.ui.main.admin.AdminViewModel
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AdminViewModelTest {

    @MockK
    private lateinit var preferenceHelper: PreferenceHelper

    @MockK
    private lateinit var getUsersUseCase: GetUsersUseCase

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
    fun `getUsers should be success`() {
        runTest {
            val listUser = listOf<User>(
                User(
                  id = 1,
                    email = "muas@gmail.com",
                    password = "pass",
                    userName = "muas",
                    role = UserRole.USER
                ),
                User(
                    id = 2,
                    email = "tes@gmail.com",
                    password = "pass",
                    userName = "tes",
                    role = UserRole.ADMIN
                ),
            )

            coEvery { getUsersUseCase.invoke() } returns flowOf(listUser)

            val viewModel = AdminViewModel(
                getUsersUseCase,
                preferenceHelper
            )

            viewModel.state.test {
                assertEquals(AdminMainState(
                    users = listUser
                ), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) { getUsersUseCase.invoke() }
        }
    }

    @Test
    fun `getUsers should be fail`() {
        runTest {
            coEvery { getUsersUseCase.invoke() } throws InvalidException("Oops!. Something went wrong")

            val viewModel = AdminViewModel(
                getUsersUseCase,
                preferenceHelper
            )

            viewModel.state.test {
                assertEquals(
                    AdminMainState(
                        users = emptyList(),
                        userLogout = false,
                        errorMessage = "Oops!. Something went wrong"
                    ),
                    awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) { getUsersUseCase.invoke() }
        }
    }

}