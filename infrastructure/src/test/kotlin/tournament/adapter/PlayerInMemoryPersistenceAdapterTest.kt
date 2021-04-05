package tournament.adapter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.repository.PlayerInMemoryRepository
import tournament.repository.PlayerRepository

class PlayerInMemoryPersistenceAdapterTest {
    private val inMemoryRepository = mockk<PlayerInMemoryRepository>()

    private val repository = PlayerInMemoryPersistenceAdapter(inMemoryRepository)

    @Test
    fun `should return player repository saved when in memory repository`(){
        val player = Player("toto")
        val playerSaved = PlayerRepository("toto")
        every { inMemoryRepository.save(player)}.returns(playerSaved)
        val save = repository.save(player)
        assertThat(save).usingRecursiveComparison().isEqualTo(player)

    }

    @Test
    fun `should return all players from repository`(){
        val playersFromRepository = listOf(PlayerRepository("toto"), PlayerRepository("tata"))
        val playersExpected = listOf(Player("toto"), Player("tata"))
        every { inMemoryRepository.getAll() }.returns(playersFromRepository)
        val allPlayers = repository.getAll()

        assertThat(allPlayers).usingRecursiveComparison().isEqualTo(playersExpected)
    }
}