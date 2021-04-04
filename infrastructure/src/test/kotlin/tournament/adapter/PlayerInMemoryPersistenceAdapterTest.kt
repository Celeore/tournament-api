package tournament.adapter

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.repository.EnMemoireRepo

class PlayerInMemoryPersistenceAdapterTest {
    private val enMemoireRepo = mockk<EnMemoireRepo>()

    private val repository = PlayerInMemoryPersistenceAdapter(enMemoireRepo)

    @Test
    fun `should return player saved when in memory repository`(){
        val player = Player("toto")
        every { enMemoireRepo.save(player)}.returns(player)
        val save = repository.save(player)
        assertThat(save).usingRecursiveComparison().isEqualTo(player)

    }

    @Test
    fun `should return all players from repository`(){
        val playersExpected = listOf(Player("toto"), Player("tata"))
        every { enMemoireRepo.getAll() }.returns(playersExpected)
        val allPlayers = repository.getAll()

        assertThat(allPlayers).containsAll(playersExpected)
    }
}