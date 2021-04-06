package tournament.adapter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import tournament.fixtures.PlayerRepositoryFixture
import tournament.repository.PlayerInMemoryRepository

class PlayerInMemoryPersistenceAdapterTest {
    private val inMemoryRepository = mockk<PlayerInMemoryRepository>()

    private val repository = PlayerInMemoryPersistenceAdapter(inMemoryRepository)

    @Test
    fun `should return player repository saved when in memory repository`(){
        // Given
        val playerSaved = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        val player = playerSaved.toPlayer()
        every { inMemoryRepository.save(player)}.returns(playerSaved)

        // When
        val save = repository.save(player)

        // Then
        assertThat(save).usingRecursiveComparison().isEqualTo(player)

    }

    @Test
    fun `should return all players from repository`(){
        // Given
        val playersFromRepository = PlayerRepositoryFixture.hasPlayerRepositoryList()
        val playersExpected = playersFromRepository.map { it.toPlayer() }
        every { inMemoryRepository.getAll() }.returns(playersFromRepository)

        // When
        val allPlayers = repository.getAll()

        // Then
        assertThat(allPlayers).usingRecursiveComparison().isEqualTo(playersExpected)
    }

    @Test
    fun `should return true when admin modify player points`(){
        // Given
        val pointsToUpdate = 10
        val playerToto = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        every { inMemoryRepository.notExistsPlayer(playerToto.pseudo) }.returns(false)
        every { inMemoryRepository.updatePlayer(playerToto.pseudo, pointsToUpdate) }.returns(Unit)

        // When
        val success = repository.updatePoints(playerToto.pseudo, pointsToUpdate)

        // Then
        verify(exactly = 1) { inMemoryRepository.updatePlayer(any(),any()) }
        assertThat(success).isTrue
    }

    @Test
    fun `should return false when admin modify player points and player not exists in repository`(){
        // Given
        val playerToto = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        every { inMemoryRepository.notExistsPlayer(playerToto.pseudo) }.returns(true)

        // When
        val success = repository.updatePoints(playerToto.pseudo, 10)

        // Then
        verify(exactly = 0) { inMemoryRepository.updatePlayer(any(),any()) }
        assertThat(success).isFalse
    }
}