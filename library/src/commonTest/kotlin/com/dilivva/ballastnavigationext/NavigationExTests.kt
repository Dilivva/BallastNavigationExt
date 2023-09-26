package com.dilivva.ballastnavigationext

import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.pathParameter
import com.copperleaf.ballast.navigation.routing.queryParameter
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationExTests {

    private suspend fun TurbineTestContext<RouterContract.State<Screens>>.getRoutes(): List<String>{
        return awaitItem().backstack.map { it.originalDestinationUrl }
    }
    @Test
    fun navigate_addScreenToStack() =  runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()

        state.test {
            navigator.navigate(Screens.Login)
            navigator.navigate(Screens.SignUp)

            assertEquals(expected = listOf(), actual = getRoutes())
            assertEquals(expected = listOf("/login"), actual = getRoutes())
            assertEquals(expected = listOf("/login","/sign-up"), actual = getRoutes())

        }

    }

    @Test
    fun navigateUp_removeScreenFromStack() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()
        state.test {
            navigator.navigate(Screens.Home)
            navigator.navigate(Screens.Login)
            navigator.navigate(Screens.SignUp)
            navigator.navigateUp()

            assertEquals(expected = listOf(), actual = getRoutes())
            assertEquals(expected = listOf("/home"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login", "/sign-up"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login"), actual = getRoutes())
        }

    }


    @Test
    fun navigate_addScreenToStackWithExtras() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()
        state.test{
            navigator.navigate(Screens.Login)
            navigator.navigate(Screens.Profile){
                pathParameter("user","john").queryParameter("email","john@gmail.com")
            }
            assertEquals(expected = listOf(), actual = getRoutes())
            assertEquals(expected = listOf("/login"), actual = getRoutes())
            assertEquals(expected = listOf("/login","/profile/john?email=john%40gmail%2Ecom"), actual = getRoutes())
        }

    }

    @Test
    fun navigateWithPop_popToParticularScreen() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()
        state.test {
            navigator.navigate(Screens.Home)
            navigator.navigate(Screens.Login)
            navigator.navigate(Screens.SignUp)
            navigator.navigateWithPop(Screens.Password, Screens.Home)

            assertEquals(expected = listOf(), actual = getRoutes())
            assertEquals(expected = listOf("/home"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login", "/sign-up"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/password"), actual = getRoutes())
        }
    }

    @Test
    fun navigateWithPopAll_popAllScreens() = runTest{
        val navigator = NavigationController(backgroundScope, Screens.values()){ Screens.Home }
        val state = navigator.observeStates()

        state.test {
            navigator.navigate(Screens.Home)
            navigator.navigate(Screens.Login)
            navigator.navigate(Screens.SignUp)
            navigator.navigateWithPopAll(Screens.Password)

            assertEquals(expected = listOf(), actual = getRoutes())
            assertEquals(expected = listOf("/home"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login"), actual = getRoutes())
            assertEquals(expected = listOf("/home","/login", "/sign-up"), actual = getRoutes())
            assertEquals(expected = listOf("/password"), actual = getRoutes())
        }

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