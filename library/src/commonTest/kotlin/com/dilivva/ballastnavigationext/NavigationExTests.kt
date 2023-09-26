package com.dilivva.ballastnavigationext

import com.copperleaf.ballast.navigation.routing.pathParameter
import com.copperleaf.ballast.navigation.routing.queryParameter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationExTests {

    @Test
    fun navigate_addScreenToStack() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()

        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        runCurrent()

        val expected = listOf("/login","/sign-up")
        val actual = state.value.backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun navigateUp_removeScreenFromStack() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()
        navigator.navigate(Screens.Home)
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        navigator.navigateUp()

        runCurrent()

        val expected = listOf("/home","/login")
        val actual = state.value.backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)

    }

    @Test
    fun navigate_addScreenToStackWithExtras() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.Profile){
            pathParameter("user","john").queryParameter("email","john@gmail.com")
        }
        runCurrent()

        val expected = listOf("/login","/profile/john?email=john%40gmail%2Ecom")
        val actual = state.value.backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)

    }

    @Test
    fun navigateWithPop_popToParticularScreen() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()
        navigator.navigate(Screens.Home)
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        navigator.navigateWithPop(Screens.Password, Screens.Home)

        runCurrent()

        val expected = listOf("/home","/password")
        val actual = state.value.backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun navigateWithPopAll_popAllScreens() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()


        navigator.navigate(Screens.Home)
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        navigator.navigateWithPopAll(Screens.Password)
        runCurrent()

        val expected = listOf("/password")
        val actual = state.value.backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
    }


}

enum class Screens(
    override val route: String
): BaseRoute{
    Home("/home"),
    Login("/login"),
    SignUp("/sign-up"),
    Password("/password"),
    Profile("/profile/:user?email={!}");
}