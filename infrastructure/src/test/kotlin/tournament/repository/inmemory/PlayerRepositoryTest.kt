package tournament.repository.inmemory

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.repository.inmemory.PlayerRepository

class PlayerRepositoryTest {
    @Test
    fun `should return player when called toPlayer function`(){
        // Given
        val playerExpected = Player("toto", 0)

        // When
        val player = PlayerRepository("toto").toPlayer()

        // Then
        assertThat(player).usingRecursiveComparison().isEqualTo(playerExpected)
    }

    @Test
    fun `should return player points when called toPlayer function`(){
        // Given
        val playerExpected = Player("toto", 10)

        // When
        val player = PlayerRepository("toto",10).toPlayer()

        // Then
        assertThat(player).usingRecursiveComparison().isEqualTo(playerExpected)
    }
}
