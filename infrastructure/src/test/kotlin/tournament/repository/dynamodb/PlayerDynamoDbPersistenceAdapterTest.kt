package tournament.repository.dynamodb

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.assertj.core.api.AssertionsForInterfaceTypes
import org.junit.jupiter.api.Test
import tournament.fixtures.PlayerRepositoryFixture

internal class PlayerDynamoDbPersistenceAdapterTest{
    private val dbRepository = mockk<PlayerDynamoDbRepository>()
    private val dynamoAdapter = PlayerDynamoDbPersistenceAdapter(dbRepository)

    @Test
    fun `should return player repository saved when db repository`() {
        // Given
        val playerSaved = PlayerRepositoryFixture.hasPlayerRepositoryToto().toPlayer()
        every { dbRepository.save(playerSaved) }.returns(playerSaved)

        // When
        val save = dynamoAdapter.save(playerSaved)

        // Then
        AssertionsForInterfaceTypes.assertThat(save).usingRecursiveComparison().isEqualTo(playerSaved)

    }

    @Test
    fun `should return all players from repository`() {
        // Given
        val playersFromRepository = PlayerRepositoryFixture.hasPlayerRepositoryList()
        val playersExpected = playersFromRepository.map { it.toPlayer() }
        every { dbRepository.readPlayers() }.returns(playersExpected)

        // When
        val allPlayers = dynamoAdapter.getAll()

        // Then
        AssertionsForInterfaceTypes.assertThat(allPlayers).usingRecursiveComparison().isEqualTo(playersExpected)
    }

    @Test
    fun `should return true when admin modify player points`() {
        // Given
        val pointsToUpdate = 10
        val playerToto = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        every { dbRepository.updateTableItem(playerToto.pseudo, pointsToUpdate) }.returns(true)

        // When
        val success = dynamoAdapter.updatePoints(playerToto.pseudo, pointsToUpdate)

        // Then
        assertThat(success).isTrue
    }

    @Test
    fun `should return false when db returns false`() {
        // Given
        val pointsToUpdate = 10
        val playerToto = PlayerRepositoryFixture.hasPlayerRepositoryToto()
        every { dbRepository.updateTableItem(playerToto.pseudo, pointsToUpdate) }.returns(false)

        // When
        val success = dynamoAdapter.updatePoints(playerToto.pseudo, pointsToUpdate)

        // Then
        assertThat(success).isFalse
    }

}
