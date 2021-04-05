package tournament.adapter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import tournament.fixtures.PlayerRepositoryFixture
import tournament.repository.PlayerInMemoryRepository

class PlayerInMemoryPersistenceAdapterTest {
    private val inMemoryRepository = mockk<PlayerInMemoryRepository>()

    private val repository = PlayerInMemoryPersistenceAdapter(inMemoryRepository)

    @Test
    fun `should return player repository saved when in memory repository`(){
        val playerSaved = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        val player = playerSaved.toPlayer()
        every { inMemoryRepository.save(player)}.returns(playerSaved)
        val save = repository.save(player)
        assertThat(save).usingRecursiveComparison().isEqualTo(player)

    }

    @Test
    fun `should return all players from repository`(){
        val playersFromRepository = PlayerRepositoryFixture.hasPlayerRepositoryList()
        val playersExpected = playersFromRepository.map { it.toPlayer() }
        every { inMemoryRepository.getAll() }.returns(playersFromRepository)
        val allPlayers = repository.getAll()

        assertThat(allPlayers).usingRecursiveComparison().isEqualTo(playersExpected)
    }
}