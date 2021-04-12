package tournament.api.app

import io.dropwizard.Application
import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import tournament.PlayerFeatures
import tournament.repository.dynamodb.PlayerDynamoDbPersistenceAdapter
import tournament.api.rest.PlayerResource
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort
import java.util.*
import javax.servlet.DispatcherType

class App : Application<MyAppConfig>() {
    override fun run(myAppConfig: MyAppConfig, environment: Environment ) {
        val repository: PlayerPersistencePort = PlayerDynamoDbPersistenceAdapter()
        val domain: PlayerServicePort = PlayerFeatures(repository)
        val playerResource = PlayerResource(domain)

        environment.apply {
            configureCors(this)
            jersey().apply {
                register(playerResource)
            }
        }
    }

    private fun configureCors(environment: Environment) {
        val cors = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:4200")
        cors.setInitParameter(
            CrossOriginFilter.ALLOWED_HEADERS_PARAM,
            "Content-Type,X-Requested-With,Accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers"
        )
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PATCH,POST,HEAD")
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true")

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }
}

fun main(args: Array<String>) {
    App().run(*args)
}
