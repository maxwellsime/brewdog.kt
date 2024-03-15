package com.punk

import com.punk.libraries.PunkClient
import com.punk.modules.punkModule
import com.punk.routes.beerRouting
import com.punk.services.BeerService
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.context.loadKoinModules
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

private val punkClient = PunkClient()
private val beerService = BeerService(punkClient)

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) { json() }
        install(Koin) { modules(punkModule) }
        configureRouting()
    }.start(wait = true)
}

fun Application.configureRouting() {
    routing {
        beerRouting(beerService)
    }
}
