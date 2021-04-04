package tournament.adapter

import tournament.entities.Player
import tournament.ports.spi.PlayerPersistencePort
import tournament.repository.EnMemoireRepo

class PlayerInMemoryPersistenceAdapter(private val enMemoireRepo: EnMemoireRepo = EnMemoireRepo()) : PlayerPersistencePort {

    override fun save(player: Player): Player = enMemoireRepo.save(player)

    override fun getAll(): List<Player> = enMemoireRepo.getAll()

}