package tournament.api.rest

import fixtures.PlayerApiFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import tournament.api.PlayerWithRankingApi
import tournament.entities.Player
import tournament.entities.PlayerWithRanking
import tournament.ports.api.PlayerServicePort
import java.lang.IllegalArgumentException

class PlayerResourceTest {
    private val playerServicePort: PlayerServicePort = mockk()
    private val playerResource = PlayerResource(playerServicePort)

    @Test
    fun `should return all players when admin call get all players`() {
        // Given
        val listExpected = PlayerApiFixture.hasPlayerApiList()
        every { playerServicePort.`retrieve all players sorted by points`() } returns listOf(
            Player("toto", 0),
            Player("tata", 0)
        )

        // When
        val list = playerResource.`get all players sorted by points`()

        // Then
        assertThat(list).usingElementComparatorIgnoringFields().containsExactlyInAnyOrderElementsOf(listExpected)
    }

    @Test
    fun `should return pseudo player when admin add a new player`() {
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        every { playerServicePort.`add new player`(playerExpected.pseudo) } returns Player("toto", 0)

        // When
        val playerAdded = playerResource.`save a new player`(playerExpected.pseudo)

        // Then
        assertThat(playerAdded).isEqualTo(playerExpected)
    }

    @Test
    fun `should return true when admin modify points for player and service returns true`() {
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        playerExpected.points = 10
        every { playerServicePort.`update points player`(playerExpected.pseudo, playerExpected.points) } returns true

        // When
        val success = playerResource.`modify player points`(playerExpected.pseudo, playerExpected)

        // Then
        assertThat(success).isEqualTo(true)
    }

    @Test
    fun `should return false when admin modify points for player and service returns false`() {
        // Given
        val playerExpected = PlayerApiFixture.hasPlayerToto()
        playerExpected.points = 10
        every { playerServicePort.`update points player`(playerExpected.pseudo, playerExpected.points) } returns true

        // When
        val success = playerResource.`modify player points`(playerExpected.pseudo, playerExpected)

        // Then
        assertThat(success).isEqualTo(true)
    }

    @Test
    fun `should return players informations`() {
        // Given
        val playerToto = PlayerApiFixture.hasPlayerToto()
        val playerExpected = PlayerWithRankingApi(playerToto.pseudo, playerToto.points, 1)
        every { playerServicePort.`get informations`(playerExpected.pseudo) } returns PlayerWithRanking(
            playerExpected.pseudo,
            playerExpected.points,
            1
        )

        // When
        val response = playerResource.`get player informations`(playerExpected.pseudo)

        // Then
        assertThat(response).isEqualTo(playerExpected)
    }

    @Test
    fun `should return error informations`() {
        // Given
        val playerToto = PlayerApiFixture.hasPlayerToto()
        val playerExpected = PlayerWithRankingApi(playerToto.pseudo, playerToto.points, 1)
        val exceptionMessage = "exception"
        every { playerServicePort.`get informations`(playerExpected.pseudo) } throws
                IllegalArgumentException(exceptionMessage)

        // When
        // Then
        assertThatThrownBy { playerResource.`get player informations`(playerExpected.pseudo) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(exceptionMessage)
    }


}