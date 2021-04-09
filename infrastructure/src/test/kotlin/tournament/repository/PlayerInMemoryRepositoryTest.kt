package tournament.repository

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tournament.fixtures.PlayerRepositoryFixture

internal class PlayerInMemoryRepositoryTest {
    private var inMemoryRepository = PlayerInMemoryRepository()

    @BeforeEach
    fun clear(){
        inMemoryRepository.removeAll()
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

    @Test
    fun `should modify points player`(){
        // Given
        val player1 = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        inMemoryRepository.save(player1.toPlayer())
        player1.points = 10
        // When
        inMemoryRepository.updatePlayer(player1.pseudo, player1.points)
        // Then
        val playerUpdated = inMemoryRepository.getAll().first{it.pseudo==player1.pseudo}
        assertThat(playerUpdated.points).isEqualTo(player1.points)
    }

    @Test
    fun `should notExistsPlayer() return false when player exists`(){
        // Given
        val player1 = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        inMemoryRepository.save(player1.toPlayer())
        // When
        val notExistsPlayer = inMemoryRepository.notExistsPlayer(player1.pseudo)
        // Then
        assertThat(notExistsPlayer).isFalse
    }

    @Test
    fun `should notExistsPlayer() return true when player exists`(){
        // Given
        // When
        val notExistsPlayer = inMemoryRepository.notExistsPlayer(PlayerRepositoryFixture.hasPlayerRepositoryToto().pseudo)
        // Then
        assertThat(notExistsPlayer).isTrue
    }

    @Test
    fun `should clear players when removeAll called`(){
        // Given
        inMemoryRepository.save(PlayerRepositoryFixture.hasPlayerRepositoryToto().toPlayer())
        // When
        inMemoryRepository.removeAll()
        // Then
        assertThat(inMemoryRepository.getAll()).isEmpty()
    }

}