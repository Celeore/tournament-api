package tournament.adapter

import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort
import tournament.repository.PlayerInMemoryRepository

class PlayerInMemoryPersistenceAdapter(private val playerInMemoryRepository: PlayerInMemoryRepository = PlayerInMemoryRepository()) : PlayerPersistencePort {

    override fun save(player: Player): Player = playerInMemoryRepository.save(player).toPlayer()

    override fun getAll(): List<Player> = playerInMemoryRepository.getAll().map { it.toPlayer() }
    override fun updatePoints(pseudo: String, points: Int) {
        playerInMemoryRepository.updatePlayer(pseudo, points)
    }

    override fun exists(pseudo: String): Boolean =
        !playerInMemoryRepository.notExistsPlayer(pseudo)

    override fun deleteAll() {
        playerInMemoryRepository.removeAll()
    }


}
