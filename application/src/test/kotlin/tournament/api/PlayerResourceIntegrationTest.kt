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
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@ExtendWith(DropwizardExtensionsSupport::class)
class PlayerResourceIntegrationTest {
    private val playerServicePort: PlayerServicePort = mockk()
    private val playerResource = PlayerResource(playerServicePort)
    private val playerResources = ResourceExtension.builder()
            .addResource( playerResource).build()

    @Test
    fun `when i called get player then i have all player `(){
        // Given
        val listExpected = PlayerApiFixture.hasPlayerApiList();
        every { playerServicePort.getAll() } returns listOf(Player("toto"), Player("tata"))

        // When
        val response:List<PlayerApi> = playerResources.target("/players")
                .request().get(object : GenericType<List<PlayerApi>>() {})

        // Then
        verify { playerServicePort.getAll() }
        assertThat(response).usingElementComparatorIgnoringFields().containsExactlyInAnyOrderElementsOf(listExpected)

    }

    @Test
    fun `when i called post player then i have all player `(){
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        every { playerServicePort.addPlayer(playerExpected.pseudo) } returns Player("toto")

        // When
        val response: PlayerApi = playerResources.target("/players").queryParam("pseudo", playerExpected.pseudo)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null).readEntity(PlayerApi::class.java)

        // Then
        verify { playerServicePort.addPlayer(playerExpected.pseudo) }
        assertThat(response).isEqualTo(playerExpected)

    }
}