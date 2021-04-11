package tournament

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.entities.PlayerWithRanking
import tournament.ports.spi.PlayerPersistencePort

class PlayerFeaturesTest {
    private val repository = mockk<PlayerPersistencePort>()
    private val playerFeatures = PlayerFeatures(repository)
    private val firstPlayerPseudo = "toto"
    private val secondPlayerPseudo = "tata"
    var firstPlayerWith0Point = Player(firstPlayerPseudo, 0)
    var secondPlayerWith10Points = Player(secondPlayerPseudo, 10)

    @BeforeEach
    fun init() {
        clearMocks(repository)
    }

    @Nested
    inner class CreatePlayer {
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
    }

    @Nested
    inner class GetAllPlayers {
        @Test
        fun `should return all players sorted by points from repository`() {
            // Given
            every { repository.getAll() }.returns(listOf(firstPlayerWith0Point, secondPlayerWith10Points))

            // When
            val listPlayers = playerFeatures.`retrieve all players sorted by points`()

            //Then
            assertThat(listPlayers.first()).usingRecursiveComparison().isEqualTo(secondPlayerWith10Points)
            assertThat(listPlayers.component2()).usingRecursiveComparison().isEqualTo(firstPlayerWith0Point)
        }
    }

    @Nested
    inner class UpdatePlayerPoints {
        @Test
        fun `should return true when player points can be modified`() {
            //Given
            val player = Player("toto",10)
            every { repository.updatePoints(player.pseudo, player.points) }.returns(true)

            // When
            val success = playerFeatures.`update points player`(player.pseudo, player.points)

            // Then
            verify(exactly = 1) { repository.updatePoints(any(),any()) }
            assertThat(success).isTrue
        }

        @Test
        fun `should return false when player points are modify and player not exists`() {
            //Given
            val player = Player("toto")
            every { repository.updatePoints(player.pseudo, player.points) }.returns(false)

            // When
            val success = playerFeatures.`update points player`(player.pseudo, player.points)

            // Then
            verify(exactly = 0) { repository.updatePoints(any(),any()) }
            assertThat(success).isFalse
        }
    }

    @Nested
    inner class GetPlayerInformations {

        @Test
        fun `cannot get informations when player does not exist`(){
            // Given
            val unexistingPlayer = "unexisting player"
            every{ repository.getAll() }.returns(emptyList())

            // When
            // Then
            assertThatThrownBy { playerFeatures.`get informations`(unexistingPlayer)}
                .isInstanceOf(PlayerNotExistsException::class.java)
                .hasMessage("Player $unexistingPlayer does not exist")
        }


        @Test
        fun `should get player information with first ranking from repository`(){
            // Given
            val firstPlace = 1
            val playerExpected = PlayerWithRanking(secondPlayerWith10Points.pseudo,secondPlayerWith10Points.points, firstPlace)
            every{ repository.getAll() }.returns(listOf(firstPlayerWith0Point, secondPlayerWith10Points))

            // When
            val player = playerFeatures.`get informations`(secondPlayerWith10Points.pseudo)

            // Then
            assertThat(player).usingRecursiveComparison().isEqualTo(playerExpected)
        }
    }
    @Nested
    inner class DeleteAll {

        @Test
        fun `delete all players in repository`() {
            every { repository.deleteAll() }.returns(Unit)
            playerFeatures.`remove all`()
            verify(exactly = 1) { repository.deleteAll() }
        }
    }
}

