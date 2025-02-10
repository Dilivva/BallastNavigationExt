package com.dilivva.ballastnavigationext.code

class ConfigGenerator(
    private val enumFileName: String,
    private val initialRoute: String?
): Generator {
    override fun generate(): String {
        return buildString {
            append(configuration()).appendLine()
            append(router()).appendLine()
        }
    }

    private fun configuration(): String{
        val firstRoute = if (initialRoute != null) "$enumFileName.$initialRoute" else "null"
        return """
            private fun BallastViewModelConfiguration.Builder.withAppScreenRouter(
                deepLinkDestination: (() -> String?)? = null,
                initial: $enumFileName? = null
            ): RouterBuilder<$enumFileName> {
                return withRouter(
                    routingTable = RoutingTable.fromEnum(${enumFileName}.entries),
                    initialRoute = initial ?: $firstRoute,
                    deepLinkUrl = deepLinkDestination,
                )
            }
        """.trimIndent()
    }

    private fun router(): String{
        return """
            private fun createRouter(viewModelCoroutineScope: CoroutineScope, initial: $enumFileName? = null): Router<$enumFileName> {
                return BasicRouter(
                    config = BallastViewModelConfiguration.Builder()
                        .withAppScreenRouter(initial = initial)
                        .build(),
                    eventHandler = eventHandler { },
                    coroutineScope = viewModelCoroutineScope,
                )
            }
        """.trimIndent()
    }
}