package com.dilivva.ballastnavigationext.code

class ImportGenerator: Generator {
    override fun generate(): String {
        return """
                import com.copperleaf.ballast.BallastViewModelConfiguration
                import androidx.compose.runtime.Composable
                import androidx.compose.runtime.CompositionLocalProvider
                import kotlinx.coroutines.CoroutineScope
                import androidx.compose.runtime.staticCompositionLocalOf
                import androidx.compose.runtime.remember
                import com.copperleaf.ballast.build
                import com.copperleaf.ballast.eventHandler
                import com.copperleaf.ballast.navigation.vm.*
                import com.copperleaf.ballast.navigation.routing.*
                import com.dilivva.ballastnavigationext.Controller
            """.trimIndent()
    }
}