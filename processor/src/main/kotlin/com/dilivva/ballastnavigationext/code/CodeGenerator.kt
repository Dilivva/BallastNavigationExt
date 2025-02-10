package com.dilivva.ballastnavigationext.code

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies


data class GeneratedRoute(
    val name: String,
    val isInitialRoute: Boolean,
    val parent: GeneratedRouteParent,
    val parameters: Set<Parameters> = setOf()
)
data class GeneratedRouteParent(
    val name: String,
    val packageName: String
)

data class Parameters(
    val name: String,
    val isNullable: Boolean,
    val type: String
)

class BallastCodeGenerator(
    private val codeGenerator: CodeGenerator
){

    private val routes = mutableMapOf<String, List<GeneratedRoute>>()

    fun addRoutes(parent: String, generatedRoute: GeneratedRoute){
        val generatedRoutes = routes[parent] ?: mutableListOf()
        routes[parent] = generatedRoutes.plus(generatedRoute)
    }
    fun generate(){
        routes.forEach { (_, generatedRoutes) ->
            generate(generatedRoutes)
        }
    }

    private fun generate(routes: List<GeneratedRoute>){
        val parent = routes.first().parent
        val fileName = "${parent.name}_Route"
        val packageName = parent.packageName
        val initialRoute = routes.find { it.isInitialRoute }

        val navigatorGenerator = NavigatorGenerator(fileName, parent.name, routes)
        val matcherGenerator = MatcherGenerator(parent.name, routes)
        val importGenerator = ImportGenerator()
        val enumGenerator = EnumGenerator(routes)
        val configGenerator = ConfigGenerator(fileName, initialRoute?.name)

        val genCodes = buildString {
            append("package $packageName").appendLine().appendLine()
            append(importGenerator.generate()).appendLine().appendLine()

            append(enumGenerator.generate()).appendLine().appendLine()
            append(configGenerator.generate()).appendLine().appendLine()

            append(navigatorGenerator.generate())
            append(matcherGenerator.generate())
        }
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = packageName,
            fileName = fileName
        )
        file.write(genCodes.toByteArray())
        file.close()
    }





}