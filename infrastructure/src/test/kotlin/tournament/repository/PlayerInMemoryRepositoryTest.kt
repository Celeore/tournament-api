package tournament.repository

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tournament.entities.Player

internal class PlayerInMemoryRepositoryTest {
    private var inMemoryRepository = PlayerInMemoryRepository()

    @BeforeEach
    fun clear(){
        inMemoryRepository = PlayerInMemoryRepository()
    }
    @Test
    fun `save a player`(){
        val player = Player("toto")
        val save = inMemoryRepository.save(player)
        assertThat(save).usingRecursiveComparison().isEqualTo(player)
    }

    @Test
    fun `get all players`(){
        val player1 = Player("toto")
        val player2 = Player("tata")
        inMemoryRepository.save(player1)
        inMemoryRepository.save(player2)
        val allPlayer = inMemoryRepository.getAll()
        assertThat(allPlayer).containsAll(listOf(player1, player2))
    }

}