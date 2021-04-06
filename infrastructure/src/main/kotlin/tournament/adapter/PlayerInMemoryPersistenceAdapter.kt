package tournament.adapter

import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort
import tournament.repository.PlayerInMemoryRepository
import tournament.repository.PlayerRepository

class PlayerInMemoryPersistenceAdapter(private val playerInMemoryRepository: PlayerInMemoryRepository = PlayerInMemoryRepository()) : PlayerPersistencePort {

    override fun save(player: Player): Player = playerInMemoryRepository.save(player).toPlayer()

    override fun getAll(): List<Player> = playerInMemoryRepository.getAll().map { it.toPlayer() }
    override fun updatePoints(pseudo: String, points: Int): Boolean {
        if(playerInMemoryRepository.notExistsPlayer(pseudo)) return false
        playerInMemoryRepository.updatePlayer(pseudo, points)
        return true
    }


}
