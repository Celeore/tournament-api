package tournament

import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class PlayerLogic(
        private val repository: PlayerPersistencePort) : PlayerServicePort {
    override fun addPlayer(pseudo: String): Player =
            Player(pseudo)
                    .also { newPlayer -> repository.save(newPlayer) }


    override fun getAll(): List<Player> =
            repository.getAll()

}
