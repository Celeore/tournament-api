package tournament.api

import fixtures.PlayerApiFixture
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import io.dropwizard.testing.junit5.ResourceExtension
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import javax.ws.rs.client.Entity
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@ExtendWith(DropwizardExtensionsSupport::class)
class PlayerResourceIntegrationTest {
    private val playerServicePort: PlayerServicePort = mockk()
    private val playerResource = PlayerResource(playerServicePort)
    private val playerResources = ResourceExtension.builder()
            .addResource( playerResource).build()

    @Test
    fun `should return all players when i called get player`(){
        // Given
        val listExpected = PlayerApiFixture.hasPlayerApiList()
        every { playerServicePort.`retrieve all players sorted by points`() } returns listOf(Player("toto", 0), Player("tata", 0))

        // When
        val response:List<PlayerApi> = playerResources.target("/players")
                .request().get(object : GenericType<List<PlayerApi>>() {})

        // Then
        verify { playerServicePort.`retrieve all players sorted by points`() }
        assertThat(response).usingElementComparatorIgnoringFields().containsExactlyInAnyOrderElementsOf(listExpected)

    }

    @Test
    fun `should return player saved when admin called post player`(){
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        every { playerServicePort.`add new player`(playerExpected.pseudo) } returns Player("toto", 0)

        // When
        val response: PlayerApi = playerResources.target("/players").queryParam("pseudo", playerExpected.pseudo)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null).readEntity(PlayerApi::class.java)

        // Then
        verify { playerServicePort.`add new player`(playerExpected.pseudo) }
        assertThat(response).isEqualTo(playerExpected)

    }

    @Test
    fun `should return true when i called patch player`(){
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        val pointsToUpdate = 10
        every { playerServicePort.`update points player`(playerExpected.pseudo, pointsToUpdate) } returns true
        playerExpected.points = pointsToUpdate

        // When
        val response: Boolean = playerResources.target("/players/${playerExpected.pseudo}")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .method("PATCH", Entity.entity(playerExpected, MediaType.APPLICATION_JSON_TYPE)).readEntity(Boolean::class.java)

        // Then
        assertThat(response).isTrue

    }
}