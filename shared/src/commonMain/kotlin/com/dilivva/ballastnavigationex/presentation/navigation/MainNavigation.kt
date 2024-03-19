package com.dilivva.ballastnavigationex.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.dilivva.ballastnavigationext.Destination


//@Composable
//fun MainNavigation() {
//    var isLoggedIn by remember { mutableStateOf(false) }
//    val applicationScope = rememberCoroutineScope()
//    val navigator = rememberNavigator(applicationScope, MainRoute.values()){
//        if (isLoggedIn) MainRoute.Dashboard else MainRoute.Home
//    }
//
//    Destination(
//        navigator = navigator,
//        onNavigate = {
//            when (it) {
//                MainRoute.Home -> HomeScreen(navigator)
//                MainRoute.SignIn -> SignInScreen(navigator){ isAuthed ->
//                    isLoggedIn = isAuthed
//                }
//                MainRoute.SignUp -> SignUpScreen(navigator)
//                MainRoute.ForgotPassword -> ForgotPasswordScreen(navigator)
//                MainRoute.Dashboard -> {
//                    //Passing data between screens
//                    val email by optionalStringQuery("email")
//                    DashboardScreen(email.orEmpty())
//                }
//                MainRoute.Profile -> {
//                    Box(modifier = Modifier.fillMaxSize())
//                }
//            }
//        },
////        onDeviceBackPressed = {
////            if (it == MainRoute.Dashboard){
////                //close app
////            }else{
////                navigator.navigateUp()
////            }
////        }
//    )
//
//}

@Composable
fun MainNavigation() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val applicationScope = rememberCoroutineScope()
    val controller = rememberController(applicationScope){
        if (isLoggedIn) Screen.Settings else Screen.Home
    }


    Destination(
        controller,
        onNavigate = {
            val navigator = LocalController.current
            when(it){
                is Screen.Home -> {
                    TestScreen(Color.Black, "Home"){ controller.navigate(Screen.Login(email = "jazzedayo@gmail.com")) }
                }
                is Screen.Login -> {
                    TestScreen(Color.Blue, "Login, Email: ${it.email}"){
                        navigator.navigate(Screen.PostDetails(postId = 12345, postCount = null))
                    }
                }
                is Screen.PostDetails -> {
                    TestScreen(Color.Red, "PostDetails, PostId: ${it.postId}"){
                        navigator.navigate(Screen.PostList(sort = "desc", query = 2))
                    }
                }
                is Screen.PostList -> TestScreen(Color.Cyan,  "PostList, Sort: ${it.sort}"){
                    navigator.popUntil(false, Screen.Home)
                    navigator.navigate(Screen.Settings)
                }
                is Screen.Settings -> TestScreen(Color.Magenta, "Settings"){
                    navigator.navigate(Screen.Profile(
                        id = 3196,
                        uuid = "4525ad8f-220c-4d35-9dc1-c43611726862",
                        isAdmin = true,
                        height = 24.429,
                        balance = .252f
                    ))
                }

                is Screen.Profile -> TestScreen(Color.DarkGray, "${it.id}\n${it.uuid}\n${it.height}\n${it.isAdmin}\n${it.balance}"){
                    navigator.navigateUp()
                }
            }
        },
    )

}

@Composable
fun TestScreen(color: Color, name: String, onClick: () -> Unit){
    Box(Modifier.fillMaxSize().background(color), contentAlignment = Alignment.Center){
        Column {
            Text(name, style = TextStyle(color = Color.White, fontSize = 16.sp))
            Button(onClick = onClick){
                Text("Click me!")
            }
        }
    }
}