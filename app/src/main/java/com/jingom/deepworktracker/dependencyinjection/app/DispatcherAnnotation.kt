package com.jingom.deepworktracker.dependencyinjection.app

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher