//package com.mongodb.app.presentation.login
//
//import kotlinx.coroutines.Dispatchers
//import org.junit.jupiter.api.Assertions.*
//
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.BeforeEach
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//
//class LoginViewModelTest {
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Mock
//    private lateinit var yourApp: YourApp
//
//    annotation class Mock
//
//    private lateinit var yourClass: LoginViewModel
//
//    @BeforeEach
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher)
//        yourClass = YourClass(yourApp)
//    }
//
//    @AfterEach
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
////    @Test
////    fun login_success_shouldEmitSuccessEvent() {
////        // Mock the behavior of login to simulate a successful login
////        Mockito.`when`(yourApp.login(Mockito.any())).thenAnswer {
////            LoginViewModel._event.emit(LoginEvent.GoToTasks(EventSeverity.INFO, "User logged in successfully."))
////        }
////        // Call the  function to be tested with random value
////        LoginViewModel.login("abdo@gmail.com", "sword", )
////
////    }
////
////    @Test
////    fun login_invalidCredentials_shouldEmitErrorMessage() {
////        // Mock the behavior of login to simulate an invalid credentials exception
////        Mockito.`when`(yourApp.login(Mockito.any())).thenAnswer {
////            throw InvalidCredentialsException("Invalid username or password.")
////        }
////        // Call the login function with invalid email and password
////        LoginViewModel.login("abdo.com", "word")
////    }
//
//    @Test
//    fun login() {
//    }
//}