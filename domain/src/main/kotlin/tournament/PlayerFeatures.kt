package tournament

import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class PlayerFeatures(
        private val repository: PlayerPersistencePort) : PlayerServicePort {
    override fun `add new player`(pseudo: String): Player =
            Player(pseudo, 0)
                    .also { newPlayer -> repository.save(newPlayer) }


    override fun `retrieve all players`(): List<Player> =
            repository.getAll()

    override fun `update points player`(pseudo: String, points: Int): Boolean = repository.updatePoints(pseudo, points)

}
