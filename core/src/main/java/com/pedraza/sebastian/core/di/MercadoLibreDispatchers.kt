package com.pedraza.sebastian.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: MercadoLibreDispatchers)

enum class MercadoLibreDispatchers {
    IO
}
