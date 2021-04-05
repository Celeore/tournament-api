package tournament

import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class PlayerFeatures(
        private val repository: PlayerPersistencePort) : PlayerServicePort {
    override fun addPlayer(pseudo: String): Player =
            Player(pseudo, 0)
                    .also { newPlayer -> repository.save(newPlayer) }


    override fun getAll(): List<Player> =
            repository.getAll()

    fun updatePoints(pseudo: String, points: Int): Boolean = repository.updatePoints(pseudo, points)

}
