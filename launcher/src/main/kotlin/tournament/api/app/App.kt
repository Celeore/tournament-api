/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package tournament.api.app

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Application
import io.dropwizard.setup.Environment
import tournament.api.rest.PlayerResource
import io.dropwizard.Configuration
import tournament.PlayerFeatures
import tournament.adapter.PlayerInMemoryPersistenceAdapter
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort


class App : Application<MyAppConfig>() {
    companion object {
        @JvmStatic fun main(args: Array<String>) = App().run(*args)
    }
    override fun run(myAppConfig: MyAppConfig, environment: Environment) {
        val repository: PlayerPersistencePort = PlayerInMemoryPersistenceAdapter()
        val domain: PlayerServicePort = PlayerFeatures(repository)
        val playerResource = PlayerResource(domain)

        environment.jersey().register(playerResource)
    }
}

class MyAppConfig(@JsonProperty("configTest") val configTest: String) : Configuration()