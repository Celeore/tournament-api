package tournament.repository.inmemory

import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort

class PlayerInMemoryPersistenceAdapter(private val playerInMemoryRepository: PlayerInMemoryRepository = PlayerInMemoryRepository()) : PlayerPersistencePort {

    override fun save(player: Player): Player = playerInMemoryRepository.save(player).toPlayer()

    override fun getAll(): List<Player> = playerInMemoryRepository.getAll().map { it.toPlayer() }

    override fun updatePoints(pseudo: String, points: Int): Boolean {
        return playerInMemoryRepository.updatePlayer(pseudo, points)
    }

    override fun deleteAll() {
        playerInMemoryRepository.removeAll()
    }


}
