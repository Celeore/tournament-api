import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import tournament.api.rest.PlayerApi
import tournament.api.rest.PlayerWithRankingApi
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
            val response: PlayerApi = `call create player resource`(playerApi).readEntity(PlayerApi::class.java)

            // Then
            assertThat(response).usingRecursiveComparison().isEqualTo(playerApi)
        }

        @Test
        fun `should get all players`() {
            // Given
            val playerApi = PlayerApi("toto")
            val playerApi2 = PlayerApi("tata")
            val playersExpected = listOf(playerApi, playerApi2)
            playersExpected.forEach { `call create player resource`(it) }

            // When
            val response: List<PlayerApi> = `call get players sorted resource`()

            // Then
            assertThat(response).usingRecursiveComparison().isEqualTo(playersExpected)
        }

        @Test
        fun `should get all players sorted by points`() {
            // Given
            val playerApi = PlayerApi("toto")
            val playerApi2 = PlayerApi("tata")
            val playerApi3 = PlayerApi("titi")
            val playersSortedExpected = listOf(playerApi2, playerApi, playerApi3)
            playersSortedExpected.forEach { `call create player resource`(it) }
            playerApi.points = 20
            playerApi2.points = 30
            playerApi3.points = 1
            playersSortedExpected.forEach { `call modify player points resource`(it) }

            // When
            val response: List<PlayerApi> = `call get players sorted resource`()

            // Then
            assertThat(response).usingRecursiveComparison().isEqualTo(playersSortedExpected)
        }

        @Test
        fun `should modify point of player`() {
            // Given
            val playerApi = PlayerApi("toto")
            `call create player resource`(playerApi)
            playerApi.points = 10
            val success = `call modify player points resource`(playerApi)

            // When
            val response: List<PlayerApi> = `call get players sorted resource`()

            // Then
            assertThat(success).isTrue
            assertThat(response.find { it.pseudo == playerApi.pseudo }?.points).isEqualTo(playerApi.points)
        }

        @Test
        fun `should get player pseudo, points and his ranking (`() {
            // Given
            val playerApi = PlayerApi("toto")
            `call create player resource`(playerApi)
            playerApi.points = 10
            `call modify player points resource`(playerApi)
            val playerWithRankingApiExpected = PlayerWithRankingApi(playerApi.pseudo, playerApi.points, 1)

            // When
            val response = `call get player informations`(playerApi)

            // Then
            assertThat(response).isEqualTo(playerWithRankingApiExpected)
        }
    }

    private fun `call get player informations`(playerApi: PlayerApi): PlayerWithRankingApi =
        APP
            .client()
            .target("http://localhost:${APP.localPort}/players/${playerApi.pseudo}")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get(PlayerWithRankingApi::class.java)

    private fun `call modify player points resource`(playerApi: PlayerApi) =
        APP
            .client()
            .target("http://localhost:${APP.localPort}/players/${playerApi.pseudo}")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .method("PATCH", Entity.entity(playerApi, MediaType.APPLICATION_JSON_TYPE))
            .readEntity(Boolean::class.java)


    private fun `call get players sorted resource`() =
        APP
            .client()
            .target("http://localhost:${APP.localPort}/players")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get(object : GenericType<List<PlayerApi>>() {})

    private fun `call create player resource`(playerApi: PlayerApi) =
        APP
            .client()
            .target("http://localhost:${APP.localPort}/players")
            .queryParam("pseudo", playerApi.pseudo)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .post(null)
}