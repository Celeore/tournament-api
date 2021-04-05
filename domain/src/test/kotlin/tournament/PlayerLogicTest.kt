package tournament

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort


class PlayerLogicTest {
    private val repository = mockk<PlayerPersistencePort>()
    private val tournament = PlayerLogic(repository)

    @Test
    fun `create a player`() {
        // Given
        val playerPseudo = "toto"
        val playerExpected = Player(playerPseudo)
        every { repository.save(any()) }.returns(playerExpected)

        // When
        tournament.addPlayer(playerPseudo)

        // Then
        verify {
            repository.save(withArg {
                assertThat(it).usingRecursiveComparison().isEqualTo(playerExpected)
            })
        }
    }

    @Test
    fun `should return player created when admin add a player `() {
        // Given
        val playerPseudo = "toto"
        val playerExpected = Player(playerPseudo)

        every { repository.save(any()) }.returns(playerExpected)

        // When
        val playerCreated = tournament.addPlayer(playerPseudo)

        // Then
        assertThat(playerCreated).usingRecursiveComparison().isEqualTo(playerExpected)
    }

    @Test
    fun `should called just once the repository when admin create a player`() {
        // Given
        val playerPseudo = "toto"
        val playerExpected = Player(playerPseudo)

        every { repository.save(any()) }.returns(playerExpected)

        // When
        tournament.addPlayer(playerPseudo)

        // Then
        verify(exactly = 1) {
            repository.save(any())
        }
    }

    @Test
    fun `should return all players from repository`() {
        // Given
        val firstPlayerPseudo = "toto"
        val secondPlayerPseudo = "tata"
        val firstPlayer = Player(firstPlayerPseudo)
        val secondPlayer = Player(secondPlayerPseudo)
        every { repository.getAll() }.returns(listOf(firstPlayer, secondPlayer))

        // When
        val listPlayers = tournament.getAll()

        //Then
        assertThat(listPlayers).containsExactlyInAnyOrder(firstPlayer, secondPlayer)
    }
}

