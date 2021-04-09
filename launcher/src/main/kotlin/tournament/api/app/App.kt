package tournament.api.app

import io.dropwizard.Application
import io.dropwizard.setup.Environment
import tournament.PlayerFeatures
import tournament.adapter.PlayerInMemoryPersistenceAdapter
import tournament.api.rest.PlayerResource
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort


class App : Application<MyAppConfig>() {
    override fun run(myAppConfig: MyAppConfig, environment: Environment) {
        val repository: PlayerPersistencePort = PlayerInMemoryPersistenceAdapter()
        val domain: PlayerServicePort = PlayerFeatures(repository)
        val playerResource = PlayerResource(domain)

        environment.jersey().register(playerResource)
    }
}
fun main(args: Array<String>){
    App().run(*args)
}