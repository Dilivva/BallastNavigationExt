package com.dilivva.ballastnavigationext.code

class MatcherGenerator(
    private val parentName: String,
    private val routes: List<GeneratedRoute>
): Generator {

    override fun generate(): String {
        return generateMatchers()
    }

    private fun generateMatchers(): String{
        return buildString {
            routes.forEach {
                append(generateMatcher(it)).appendLine().appendLine()
            }
        }
    }



    private fun generateMatcher(route: GeneratedRoute): String{
        return buildString {
            append("private fun <S: Route> match${route.name}(match: Destination.Match<S>): $parentName.${route.name} = with(match) {").appendLine()
            route.parameters.forEach {
                val queryType = parameters(it)
                append("    val ${it.name} by $queryType").appendLine()
            }
            if (route.parameters.isNotEmpty()) {
                append("    return@with $parentName.${route.name}(${route.parameters.joinToString(",") { it.name }})").appendLine()
            }else{
                append("    return@with $parentName.${route.name}").appendLine()
            }
            append("}")
        }
    }

    private fun parameters(parameters: Parameters): String{
        val type = parameters.type.lowercase()
        val optional = "optional${type.replaceFirstChar { it.uppercase() }}Query(\"${parameters.name}\")"
        val query = type.plus("Query(\"${parameters.name}\")")
        return if (parameters.isNullable) optional else query
    }
}