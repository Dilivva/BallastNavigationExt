package com.dilivva.ballastnavigationex.presentation.navigation


import com.dilivva.ballastnavigationext.BaseRoute

enum class MainRoute(
    override val route: String
): BaseRoute{
    Home("/app/home"),
    SignIn("/app/sign-in"),
    SignUp("/app/sign-up"),
    ForgotPassword("/app/forgot-password"),
    Dashboard("/app/dashboard?email={?}"),
    Profile("/app/dashboard/profile");
}