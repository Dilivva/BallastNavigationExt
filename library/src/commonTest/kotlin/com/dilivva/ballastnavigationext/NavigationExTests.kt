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
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationExTests {
    private lateinit var dispatcher: TestDispatcher
    private lateinit var scope: TestScope
    private lateinit var navigator: NavigationController<Screens>
    private lateinit var state: StateFlow<RouterContract.State<Screens>>
    private lateinit var states: MutableList<RouterContract.State<Screens>>

    @BeforeTest
    fun setUp(){
        dispatcher = StandardTestDispatcher(TestCoroutineScheduler())
        scope = TestScope(dispatcher)
        navigator = NavigationController(scope, Screens.values()){ Screens.Home }
        state = navigator.observeStates()
        states = mutableListOf()
    }
    @AfterTest
    fun tearDown(){
        states.clear()
    }


    @Test
    fun navigate_addScreenToStack() = runTest{
        scope.launch(dispatcher){
            state.toList(states)
        }

        navigator.navigate(Screens.Login)
        navigator.navigate(Screens.SignUp)
        scope.runCurrent()
        val expected = listOf("/login","/sign-up")
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

        scope.runCurrent()

        val expected = listOf("/home","/login")
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
        scope.runCurrent()

        val expected = listOf("/login","/profile/john?email=john%40gmail%2Ecom")
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
        scope.runCurrent()

        val expected = listOf("/home","/password")
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

        scope.runCurrent()

        val expected = listOf("/password")
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