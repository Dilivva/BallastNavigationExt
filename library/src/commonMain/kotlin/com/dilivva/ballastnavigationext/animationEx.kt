package com.dilivva.ballastnavigationext

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith

fun <T> AnimatedContentTransitionScope<T>.defaultAnimateIn(): ContentTransform {
    return slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(400)) togetherWith
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(400))
}
fun <T> AnimatedContentTransitionScope<T>.defaultAnimateOut(): ContentTransform {
    return slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(400)) togetherWith
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(400))
}