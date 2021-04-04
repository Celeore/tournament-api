package tournament.adapter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.repository.PlayerInMemoryRepository

class PlayerInMemoryPersistenceAdapterTest {
    private val inMemoryRepository = mockk<PlayerInMemoryRepository>()

    private val repository = PlayerInMemoryPersistenceAdapter(inMemoryRepository)

    @Test
    fun `should return player saved when in memory repository`(){
        val player = Player("toto")
        every { inMemoryRepository.save(player)}.returns(player)
        val save = repository.save(player)
        assertThat(save).usingRecursiveComparison().isEqualTo(player)

    }

    @Test
    fun `should return all players from repository`(){
        val playersExpected = listOf(Player("toto"), Player("tata"))
        every { inMemoryRepository.getAll() }.returns(playersExpected)
        val allPlayers = repository.getAll()

        assertThat(allPlayers).containsAll(playersExpected)
    }
}