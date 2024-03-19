package com.dilivva.ballastnavigationext.processor

import com.dilivva.ballastnavigationext.annotation.InitialRoute
import com.dilivva.ballastnavigationext.annotation.Routes
import com.dilivva.ballastnavigationext.code.BallastCodeGenerator
import com.dilivva.ballastnavigationext.code.GeneratedRoute
import com.dilivva.ballastnavigationext.code.GeneratedRouteParent
import com.dilivva.ballastnavigationext.code.Parameters
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.toClassName

class BallastProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return BallastProcessor(environment)
    }
}

class BallastProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {

    private val ballastCodeGenerator = BallastCodeGenerator(environment.codeGenerator)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        //Getting the sealed class/interface
        val symbols: Sequence<KSClassDeclaration> = resolver
            .getSymbolsWithAnnotation(Routes::class.java.name)
            .filterIsInstance<KSClassDeclaration>()

        //Making sure its only sealed class/interface

        val isRouteProperlyAnnotated = symbols.all {  it.modifiers.containsIgnoreCase("sealed") }
        if (!isRouteProperlyAnnotated){
            environment.logger.error("You can only use this on sealed interface/class")
            return emptyList()
        }

        if (symbols.iterator().hasNext().not()) return emptyList()

        symbols.forEach { symbol ->
            val clazzes = symbol.getSealedSubclasses()
            processRoutes(symbol, clazzes)
        }
        ballastCodeGenerator.generate()

        return symbols.filterNot { it.validate() }.toList()
    }

    private fun processRoutes(parent: KSClassDeclaration, routes: Sequence<KSClassDeclaration>){
        routes.forEach {
            if (it.annotations.hasAnnotation(InitialRoute::class.java.simpleName)){
                processInitialRoute(parent, it)
            }else{
                processOtherRoutes(parent, it)
            }
        }
    }

    private fun processInitialRoute(parent: KSClassDeclaration, clazz: KSClassDeclaration){
        if (clazz.classKind != ClassKind.OBJECT){
            environment.logger.error("InitialRoute must be an object to be static")
            return
        }
        val parentName = parent.simpleName.asString()
        val generatedRouteParent = GeneratedRouteParent(parentName, parent.packageName.asString())
        val generatedRoute = GeneratedRoute(clazz.simpleName.asString(),true, generatedRouteParent)
        ballastCodeGenerator.addRoutes(parentName,generatedRoute)
    }

    private fun processOtherRoutes(parent: KSClassDeclaration, clazz: KSClassDeclaration){
        val primitives = setOf("String","Int","Boolean","Long","Double","Float")
        val params = clazz.getDeclaredProperties()
        val isParametersPrimitives = params.all { primitives.contains(it.type.toString()) }
        if (!isParametersPrimitives){
            environment.logger.error("You can only pass primitives as arguments: ${clazz.simpleName.asString()}")
            return
        }

        val parentName = parent.simpleName.asString()
        val generatedRouteParent = GeneratedRouteParent(parentName, parent.packageName.asString())
        val parameters = params.map {
            val type = it.type.resolve()
            Parameters(it.simpleName.asString(), type.isMarkedNullable, type.toClassName().simpleName)
        }
        val generatedRoute = GeneratedRoute(
            name = clazz.simpleName.asString(),
            isInitialRoute = false,
            parent = generatedRouteParent,
            parameters = parameters.toSet()
        )
        ballastCodeGenerator.addRoutes(parentName,generatedRoute)
    }


}


// return true if an object has a specific annotation
fun Sequence<KSAnnotation>.hasAnnotation(target: String): Boolean {
    for (element in this) if (element.shortName.asString() == target) return true
    return false
}

// return true if a particular modifier is included in a list
fun Collection<Modifier>.containsIgnoreCase(name: String): Boolean {
    return any { it.name.equals(name, true) }
}