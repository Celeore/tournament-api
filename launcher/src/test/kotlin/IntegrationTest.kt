import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import tournament.api.PlayerApi
import tournament.api.app.App
import tournament.api.app.MyAppConfig
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(DropwizardExtensionsSupport::class)
class IntegrationTest {
    private val CONFIG_PATH = ResourceHelpers.resourceFilePath("integration-config.yml")

        val APP: DropwizardAppExtension<MyAppConfig> = DropwizardAppExtension(
        App::class.java, CONFIG_PATH
    )


    @Test
    fun `should save a new player`() {
        val playerApi = PlayerApi("toto")
        val response: PlayerApi = `call POST player`(playerApi).readEntity(PlayerApi::class.java)
        assertThat(response).usingRecursiveComparison().isEqualTo(playerApi)
    }

    @Test
    fun `should get all players`() {
        val playerApi = PlayerApi("toto")
        val playerApi2 = PlayerApi("tata")
        val playersExpected = listOf(playerApi, playerApi2)
        playersExpected.forEach{`call POST player`(it)}
        val response:List<PlayerApi> = APP.client().target("http://localhost:${APP.localPort}/players")
            .request().get(object : GenericType<List<PlayerApi>>() {})
        assertThat(response).usingRecursiveComparison().isEqualTo(playersExpected)
    }

    private fun `call POST player`(playerApi: PlayerApi) =
        APP.client().target("http://localhost:${APP.localPort}/players").queryParam("pseudo", playerApi.pseudo)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .post(null)
}