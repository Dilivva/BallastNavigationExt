package com.dilivva.ballastnavigationext

import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.pathParameter
import com.copperleaf.ballast.navigation.routing.queryParameter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationExTests {
    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val scope = TestScope(dispatcher)
    private lateinit var navigator: NavigationController<Screens>
    private lateinit var state: StateFlow<RouterContract.State<Screens>>
    private val states = mutableListOf<RouterContract.State<Screens>>()

    @BeforeTest
    fun setUp(){
        navigator = NavigationController(scope, Screens.values()){ Screens.Home }
        state = navigator.observeStates()
        states.clear()
    }


    @Test
    fun navigate_addScreenToStack() = runTest{
        scope.launch(dispatcher){
            state.toList(states)
        }
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        val expected = listOf("/login","/sign-up")
        scope.runCurrent()
        val actual = states.last().backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
        scope.cancel()
    }

    @Test
    fun navigateUp_removeScreenFromStack() = runTest{
        scope.launch(dispatcher){
            state.toList(states)
        }
        navigator.navigate(Screens.Home)
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        navigator.navigateUp()
        val expected = listOf("/home","/login")
        scope.runCurrent()
        val actual = states.last().backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
        scope.cancel()
    }

    @Test
    fun navigate_addScreenToStackWithExtras() = runTest{
        scope.launch(dispatcher){
            state.toList(states)
        }
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.Profile){
            pathParameter("user","john").queryParameter("email","john@gmail.com")
        }
        val expected = listOf("/login","/profile/john?email=john%40gmail%2Ecom")
        scope.runCurrent()
        println("Destinations: $states")
        val actual = states.last().backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
        scope.cancel()
    }

    @Test
    fun navigateWithPop_popToParticularScreen() = runTest{
        scope.launch(dispatcher){
            state.toList(states)
        }
        navigator.navigate(Screens.Home)
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        navigator.navigateWithPop(Screens.Password, Screens.Home)
        val expected = listOf("/home","/password")
        scope.runCurrent()
        val actual = states.last().backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
        scope.cancel()
    }

    @Test
    fun navigateWithPopAll_popAllScreens() = runTest{
        scope.launch(dispatcher){
            state.toList(states)
        }
        navigator.navigate(Screens.Home)
        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        navigator.navigateWithPopAll(Screens.Password)
        val expected = listOf("/password")
        scope.runCurrent()
        val actual = states.last().backstack.map { it.originalDestinationUrl }

        assertEquals(expected = expected, actual = actual)
        scope.cancel()
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