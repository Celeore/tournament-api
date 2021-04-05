import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import tournament.api.PlayerApi
import tournament.api.app.App
import tournament.api.app.MyAppConfig
import javax.ws.rs.client.Entity
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(DropwizardExtensionsSupport::class)
class IntegrationTest {
    private val CONFIG_PATH = ResourceHelpers.resourceFilePath("integration-config.yml")
    private val APP: DropwizardAppExtension<MyAppConfig> = DropwizardAppExtension(App::class.java, CONFIG_PATH)

    @Nested
    inner class PlayerFeature {
        @Test
        fun `should save a new player`() {
            // Given
            val playerApi = PlayerApi("toto")

            // When
            val response: PlayerApi = `call POST player resource`(playerApi).readEntity(PlayerApi::class.java)

            // Then
            assertThat(response).usingRecursiveComparison().isEqualTo(playerApi)
        }

        @Test
        fun `should get all players`() {
            // Given
            val playerApi = PlayerApi("toto")
            val playerApi2 = PlayerApi("tata")
            val playersExpected = listOf(playerApi, playerApi2)
            playersExpected.forEach { `call POST player resource`(it) }

            // When
            val response: List<PlayerApi> = `call GET players resource`()

            // Then
            assertThat(response).usingRecursiveComparison().isEqualTo(playersExpected)
        }

        @Test
        fun `should modify point of player`() {
            // Given
            val playerApi = PlayerApi("toto")
            `call POST player resource`(playerApi)
            playerApi.points = 10
            val success = APP.client()
                .target("http://localhost:${APP.localPort}/players/${playerApi.pseudo}")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .method("PATCH", Entity.entity(playerApi, MediaType.APPLICATION_JSON_TYPE)).readEntity(Boolean::class.java)

            // When
            val response: List<PlayerApi> = `call GET players resource`()

            // Then
            assertThat(success).isTrue
                //assertThat(response.find { it.pseudo == playerApi.pseudo }?.points).isEqualTo(playerApi.points)
        }


    }


    private fun `call GET players resource`() = APP
        .client()
        .target("http://localhost:${APP.localPort}/players")
        .request().get(object : GenericType<List<PlayerApi>>() {})

    private fun `call POST player resource`(playerApi: PlayerApi) =
        APP.client().target("http://localhost:${APP.localPort}/players").queryParam("pseudo", playerApi.pseudo)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .post(null)
}