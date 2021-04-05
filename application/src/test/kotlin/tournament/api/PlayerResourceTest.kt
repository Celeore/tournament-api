package tournament.api

import fixtures.PlayerApiFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.ports.api.PlayerServicePort

class PlayerResourceTest {
    private val playerServicePort: PlayerServicePort = mockk()
    private val playerResource = PlayerResource(playerServicePort)

    @Test
    fun `should return all players when admin call get all players`() {
        // Given
        val listExpected = PlayerApiFixture.hasPlayerApiList();
        every { playerServicePort.getAll() } returns listOf(Player("toto"), Player("tata"))

        // When
        val list = playerResource.`get all players`()

        // Then
        assertThat(list).usingElementComparatorIgnoringFields().containsExactlyInAnyOrderElementsOf(listExpected)
    }

    @Test
    fun `should return pseudo player when admin add a new player`() {
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        every { playerServicePort.addPlayer(playerExpected.pseudo) } returns Player("toto")

        // When
        val playerAdded = playerResource.`save a new player`(playerExpected.pseudo)

        // Then
        assertThat(playerAdded).isEqualTo(playerExpected)
    }



}