package tournament.repository

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tournament.entities.Player
import tournament.fixtures.PlayerRepositoryFixture

internal class PlayerInMemoryRepositoryTest {
    private var inMemoryRepository = PlayerInMemoryRepository()

    @BeforeEach
    fun clear(){
        inMemoryRepository = PlayerInMemoryRepository()
    }
    @Test
    fun `save a player repo when received player`(){
        val playerSavedExpected = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        val player = playerSavedExpected.toPlayer()

        val save = inMemoryRepository.save(player)

        assertThat(save).usingRecursiveComparison().isEqualTo(playerSavedExpected)
    }

    @Test
    fun `get all players`(){
        val player1 = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        val player2 = PlayerRepositoryFixture.hasPlayerRepositoryTata()
        inMemoryRepository.save(player1.toPlayer())
        inMemoryRepository.save(player2.toPlayer())

        val allPlayer = inMemoryRepository.getAll()

        assertThat(allPlayer).containsAll(listOf(player1, player2))
    }

}