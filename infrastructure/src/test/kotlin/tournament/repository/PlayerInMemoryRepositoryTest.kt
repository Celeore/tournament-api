package tournament.repository

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tournament.fixtures.PlayerRepositoryFixture

internal class PlayerInMemoryRepositoryTest {
    private var inMemoryRepository = PlayerInMemoryRepository()

    @BeforeEach
    fun clear(){
        inMemoryRepository = PlayerInMemoryRepository()
    }
    @Test
    fun `save a player repo when received player`(){
        // Given
        val playerSavedExpected = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        val player = playerSavedExpected.toPlayer()

        // When
        val save = inMemoryRepository.save(player)

        // Then
        assertThat(save).isEqualTo(playerSavedExpected)
    }

    @Test
    fun `get all players`(){
        // Given
        val player1 = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        val player2 = PlayerRepositoryFixture.hasPlayerRepositoryTata()
        inMemoryRepository.save(player1.toPlayer())
        inMemoryRepository.save(player2.toPlayer())

        // When
        val allPlayer = inMemoryRepository.getAll()

        // Then
        assertThat(allPlayer).containsAll(listOf(player1, player2))
    }

}