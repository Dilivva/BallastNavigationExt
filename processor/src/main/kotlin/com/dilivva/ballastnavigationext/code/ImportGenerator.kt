package com.dilivva.ballastnavigationext.code

class ImportGenerator: Generator {
    override fun generate(): String {
        return """
                import com.copperleaf.ballast.BallastViewModelConfiguration
                import androidx.compose.runtime.Composable
                import com.copperleaf.ballast.navigation.routing.Destination
                import com.copperleaf.ballast.navigation.routing.Route
                import com.copperleaf.ballast.navigation.routing.RouteAnnotation
                import com.copperleaf.ballast.navigation.routing.RouteMatcher
                import com.copperleaf.ballast.navigation.routing.RoutingTable
                import com.copperleaf.ballast.navigation.routing.Tag
                import com.copperleaf.ballast.navigation.routing.build
                import com.copperleaf.ballast.navigation.routing.directions
                import com.copperleaf.ballast.navigation.routing.fromEnum
                import com.copperleaf.ballast.navigation.routing.stringQuery
                import com.copperleaf.ballast.navigation.routing.optionalStringQuery
                import com.copperleaf.ballast.navigation.routing.pathParameter
                import com.copperleaf.ballast.navigation.routing.queryParameter
                import com.copperleaf.ballast.navigation.vm.RouterBuilder
                import com.copperleaf.ballast.navigation.vm.withRouter
                import kotlinx.coroutines.CoroutineScope
                import androidx.compose.runtime.staticCompositionLocalOf
                import androidx.compose.runtime.remember
                import com.copperleaf.ballast.build
                import com.copperleaf.ballast.eventHandler
                import com.copperleaf.ballast.navigation.vm.BasicRouter
                import com.copperleaf.ballast.navigation.vm.Router   
                import com.copperleaf.ballast.navigation.routing.RouterContract
                import com.dilivva.ballastnavigationext.Controller
            """.trimIndent()
    }
}