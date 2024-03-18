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
                val queryType = if (it.isNullable) "optionalStringQuery(\"${it.name}\")" else "stringQuery(\"${it.name}\")"
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
}