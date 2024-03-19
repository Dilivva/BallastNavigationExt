package com.dilivva.ballastnavigationex.presentation.navigation


import com.dilivva.ballastnavigationext.BaseRoute
import com.dilivva.ballastnavigationext.annotation.InitialRoute
import com.dilivva.ballastnavigationext.annotation.Routes

enum class MainRoute(
    override val route: String
): BaseRoute{
    Home("/home"),
    SignIn("/sign-in"),
    SignUp("/sign-up"),
    ForgotPassword("/forgot-password"),
    Dashboard("/dashboard?email={?}"),
    Profile("/profile");
}

@Routes
sealed class Screen{
    @InitialRoute
    data object Home: Screen()

    data class Login(val email: String?): Screen()

    data class PostDetails(val postId: Int, val postCount: Double?): Screen()
    data class PostList(val sort: String?, val query: Long?): Screen()

    data object Settings: Screen()
    data class Profile(
        val id: Int,
        val uuid: String,
        val isAdmin: Boolean,
        val height: Double,
        val balance: Float
    ): Screen()

}