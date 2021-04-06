package tournament

import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class PlayerFeatures(
        private val repositoryPlayer: PlayerPersistencePort) : PlayerServicePort {
    override fun `add new player`(pseudo: String): Player =
            Player(pseudo, 0)
                    .also { newPlayer -> repositoryPlayer.save(newPlayer) }


    override fun `retrieve all players sorted by points`(): List<Player> =
            repositoryPlayer.getAll().sortedByDescending { it.points }

    override fun `update points player`(pseudo: String, points: Int): Boolean = repositoryPlayer.updatePoints(pseudo, points)

}
