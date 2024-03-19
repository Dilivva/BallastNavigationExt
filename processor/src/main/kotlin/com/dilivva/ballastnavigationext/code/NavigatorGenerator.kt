package com.dilivva.ballastnavigationext.code

class NavigatorGenerator(
    private val enumFileName: String,
    private val parentName: String,
    private val routes: List<GeneratedRoute>
): Generator {
    override fun generate(): String {
        return buildString {
            append(compositionLocal()).appendLine().appendLine().appendLine()
            append(header()).appendLine().appendLine()
            append(routerFunc()).appendLine().appendLine()
            append(navigateFunction()).appendLine().appendLine()
            append(routeMatcher()).appendLine().appendLine()
            append(mapRouteFunc()).appendLine().appendLine()
            append(mapScreenToRouteFunc()).appendLine().appendLine()
            append(routesFunc()).appendLine().appendLine()
            append(footer()).appendLine().appendLine()
            append(generateRememberController()).appendLine().appendLine()
            append(screenMapper()).appendLine().appendLine()
        }
    }

    private fun compositionLocal(): String{
        return "val LocalController = staticCompositionLocalOf<${parentName}Controller> { error(\"No controller created\") }"
    }
    private fun header(): String{
        return "public class ${parentName}Controller(private val router: Router<$enumFileName>): Controller<$parentName,$enumFileName>{"
    }

    private fun routerFunc(): String{
        return buildString {
            append("override fun getRouter(): Router<$enumFileName> {").appendLine()
            append("  return router").appendLine()
            append("}")
        }
    }
    private fun navigateFunction(): String{
        return buildString {
            append("override fun navigate(to: $parentName){").appendLine()
            append("    val route = getRoute(to)").appendLine()
            append("    router.trySend(RouterContract.Inputs.GoToDestination(route))").appendLine()
            append("}").appendLine().appendLine()

            append("override fun navigateUp() {").appendLine()
            append("   router.trySend(RouterContract.Inputs.GoBack())").appendLine()
            append("}").appendLine().appendLine()

            append("override fun popUntil(inclusive: Boolean, destination: $parentName) {").appendLine()
            append("   val route = getScreen(destination)").appendLine()
            append("   router.trySend(RouterContract.Inputs.PopUntilRoute(inclusive = inclusive, route = route))").appendLine()
            append("}").appendLine().appendLine()

            append("@Composable").appendLine()
            append("override fun provider(block: @Composable () -> Unit) {").appendLine()
            append("    CompositionLocalProvider(LocalController provides this){").appendLine()
            append("        block()").appendLine()
            append("    }").appendLine()
            append("}")
        }
    }

    private fun routeMatcher(): String{
        return buildString {
            append("override fun matchRoute(match: Destination.Match<$enumFileName>, appScreen: $enumFileName): $parentName {").appendLine()
            append("     return when (appScreen) {").appendLine()
            routes.forEach {
                append("         $enumFileName.${it.name} -> match${it.name}(match)").appendLine()
            }
            append("    }").appendLine()
            append("}")
        }
    }

    private fun mapRouteFunc(): String{
        return buildString {
            append("private fun getRoute(screen: $parentName): String{").appendLine()
            append("    return when(screen){").appendLine()
            routes.forEach {
                if (it.parameters.isNotEmpty()){
                    append("         is $parentName.${it.name} -> ${it.name.lowercase()}Route(${getParametersValue(it.parameters)})").appendLine()
                }else{
                    append("         is $parentName.${it.name} -> ${it.name.lowercase()}Route()").appendLine()
                }
            }
            append("    }").appendLine()
            append("}")
        }
    }

    private fun mapScreenToRouteFunc(): String{
        return buildString {
            append("private fun getScreen(screen: $parentName): $enumFileName{").appendLine()
            append("    return when(screen){").appendLine()
            routes.forEach {
                append("         is $parentName.${it.name} -> $enumFileName.${it.name}").appendLine()
            }
            append("    }").appendLine()
            append("}")
        }
    }

    private fun routesFunc(): String{
        return buildString {
            routes.forEach {
                if (it.parameters.isNotEmpty()){
                    append("private fun ${it.name.lowercase()}Route(${getParameters(it.parameters)}): String{").appendLine()
                    append("    return ${enumFileName}.${it.name}.directions().let{ it.")
                    append(getParametersForQuery(it.parameters))
                    append(" }.build()").appendLine()
                    append("}").appendLine()
                }else{
                    append("private fun ${it.name.lowercase()}Route(): String{").appendLine()
                    append("    return ${enumFileName}.${it.name}.directions().build()").appendLine()
                    append("}").appendLine()
                }
            }
        }
    }

    private fun footer(): String{
        return "}"
    }

    //generate something like postId: Int?, name: String
    private fun getParameters(parameters: Set<Parameters>): String{
        return parameters.joinToString(", ") {
            val required = if (it.isNullable) "?" else ""
            "${it.name}: ${it.type}$required"
        }
    }
    private fun getParametersValue(parameters: Set<Parameters>): String{
        return parameters.joinToString(",") {
            "screen.${it.name}"
        }
    }
    private fun getParametersForQuery(parameters: Set<Parameters>): String{
        return parameters.joinToString(".") {
            """queryParameter("${it.name}", ${it.name}.toString())"""
        }
    }

    private fun generateRememberController(): String{
        return buildString {
            append("@Composable").appendLine()
            append("fun rememberController(scope: CoroutineScope, initialRoute: (() -> $parentName)? = null): Controller<$parentName,$enumFileName>{").appendLine()
            append("   val initial = initialRoute?.invoke()?.toRoute()").appendLine()
            append("   val router = createRouter(scope, initial)").appendLine()
            append("  return remember(router) { ${parentName}Controller(router) }").appendLine()
            append("}")
        }
    }

    private fun screenMapper(): String{
        return buildString {
            append("private fun $parentName.toRoute(): $enumFileName{").appendLine()
            append("    return when(this){").appendLine()
            routes.forEach {
                append("         is $parentName.${it.name} -> $enumFileName.${it.name}").appendLine()
            }
            append("    }").appendLine()
            append("}")
        }
    }

}