package com.dilivva.ballastnavigationex.presentation.navigation


import com.dilivva.ballastnavigationext.BaseRoute

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