package tournament

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort


class PlayerFeaturesTest {
    private val repository = mockk<PlayerPersistencePort>()
    private val playerFeatures = PlayerFeatures(repository)

    @Test
    fun `create a player`() {
        // Given
        val playerPseudo = "toto"
        val playerExpected = Player(playerPseudo, 0)
        every { repository.save(any()) }.returns(playerExpected)

        // When
        playerFeatures.`add new player`(playerPseudo)

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
        val playerExpected = Player(playerPseudo, 0)

        every { repository.save(any()) }.returns(playerExpected)

        // When
        val playerCreated = playerFeatures.`add new player`(playerPseudo)

        // Then
        assertThat(playerCreated).usingRecursiveComparison().isEqualTo(playerExpected)
    }

    @Test
    fun `should called just once the repository when admin create a player`() {
        // Given
        val playerPseudo = "toto"
        val playerExpected = Player(playerPseudo, 0)

        every { repository.save(any()) }.returns(playerExpected)

        // When
        playerFeatures.`add new player`(playerPseudo)

        // Then
        verify(exactly = 1) {
            repository.save(any())
        }
    }

    @Test
    fun `should return all players sorted by points from repository`() {
        // Given
        val firstPlayerPseudo = "toto"
        val secondPlayerPseudo = "tata"
        val firstPlayer = Player(firstPlayerPseudo, 0)
        val secondPlayer = Player(secondPlayerPseudo, 10)
        every { repository.getAll() }.returns(listOf(firstPlayer, secondPlayer))

        // When
        val listPlayers = playerFeatures.`retrieve all players sorted by points`()

        //Then
        assertThat(listPlayers.first()).usingRecursiveComparison().isEqualTo(secondPlayer)
        assertThat(listPlayers.component2()).usingRecursiveComparison().isEqualTo(firstPlayer)
    }

    @Test
    fun `should return true when player points are modify`(){
        //Given
        val player = Player("toto")
        every { repository.updatePoints(player.pseudo, player.points) }.returns(true)

        // When
        val success = playerFeatures.`update points player`(player.pseudo, player.points)

        // Then
        assertThat(success).isTrue
    }

    @Test
    fun `should return false when player points are modify and player not exists`(){
        //Given
        val player = Player("toto")
        every { repository.updatePoints(player.pseudo, player.points) }.returns(false)

        // When
        val success = playerFeatures.`update points player`(player.pseudo, player.points)

        // Then
        assertThat(success).isFalse
    }
}

