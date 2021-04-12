package tournament.repository.dynamodb

import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort

class PlayerDynamoDbPersistenceAdapter(private val playerDynamoDbRepository: PlayerDynamoDbRepository = PlayerDynamoDbRepository()) : PlayerPersistencePort {
    override fun save(player: Player) = playerDynamoDbRepository.save(player)

    override fun getAll(): List<Player> = playerDynamoDbRepository.readPlayers()

    override fun updatePoints(pseudo: String, points: Int) = playerDynamoDbRepository.updateTableItem(pseudo, points)

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}
