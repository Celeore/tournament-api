package tournament.adapter

import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort
import tournament.repository.PlayerInMemoryRepository

class PlayerInMemoryPersistenceAdapter(private val playerInMemoryRepository: PlayerInMemoryRepository = PlayerInMemoryRepository()) : PlayerPersistencePort {

    override fun save(player: Player): Player = playerInMemoryRepository.save(player)

    override fun getAll(): List<Player> = playerInMemoryRepository.getAll()

}