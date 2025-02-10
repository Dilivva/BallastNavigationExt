package com.dilivva.ballastnavigationext.code

class EnumGenerator(
    private val routes: List<GeneratedRoute>
): Generator {

    private val parent = routes.first().parent
    override fun generate(): String {
       return buildString {
           append(header()).appendLine()
           append(enums()).appendLine()
           append(footer())
       }
    }

    private fun header(): String{
        return  "enum class ${parent.name}_Route(routeFormat: String): Route{"
    }
    private fun enums(): String{
        return buildString {
            routes.forEach {
                if (it.parameters.isNotEmpty()) {
                    append("""  ${it.name}("/${it.name.lowercase()}?${flattenParams(it.parameters)}"),""").appendLine()
                }else{
                    append("""  ${it.name}("/${it.name.lowercase()}"),""").appendLine()
                }
            }
        }
    }
    private fun footer(): String{
        return buildString {
            append("  ;").appendLine()
            append("  override val annotations: Set<RouteAnnotation> = emptySet()").appendLine()
            append("  override val matcher: RouteMatcher = RouteMatcher.create(routeFormat)")
            appendLine()
            appendLine()
            append("}")
            appendLine()
        }
    }


    private fun flattenParams(parameters: Set<Parameters>): String{
        return parameters.joinToString("&") {
            val required = if (it.isNullable) "{?}" else "{!}"
            "${it.name}=$required"
        }
    }
}