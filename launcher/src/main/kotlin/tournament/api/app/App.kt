package tournament.api.app

import io.dropwizard.Application
import io.dropwizard.setup.Environment
import tournament.PlayerFeatures
import tournament.adapter.PlayerDynamoDbPersistenceAdapter
import tournament.api.rest.PlayerResource
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class App : Application<MyAppConfig>() {
    override fun run(myAppConfig: MyAppConfig, environment: Environment) {
        val repository: PlayerPersistencePort = PlayerDynamoDbPersistenceAdapter()
        val domain: PlayerServicePort = PlayerFeatures(repository)
        val playerResource = PlayerResource(domain)

        var jersey = environment.jersey()
        jersey.register(playerResource)
        jersey.register(CORSFilter())
    }
}
fun main(args: Array<String>){
    App().run(*args)
}
