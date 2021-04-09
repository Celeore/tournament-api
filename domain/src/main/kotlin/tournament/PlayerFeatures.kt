package tournament

import tournament.entities.Player
import tournament.entities.PlayerWithRanking
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class PlayerFeatures(
    private val repositoryPlayer: PlayerPersistencePort
) : PlayerServicePort {
    override fun `add new player`(pseudo: String): Player =
        Player(pseudo, 0)
            .also { newPlayer -> repositoryPlayer.save(newPlayer) }


    override fun `retrieve all players sorted by points`(): List<Player> = `get all players sorted by points`()

    override fun `update points player`(pseudo: String, points: Int): Boolean {
        val canUpdatePlayer = repositoryPlayer.exists(pseudo)
        if (canUpdatePlayer) repositoryPlayer.updatePoints(pseudo, points)
        return canUpdatePlayer
    }


    override fun `get informations`(pseudo: String): PlayerWithRanking {

        val exists = repositoryPlayer.exists(pseudo)
        when {
            exists -> {
                val allPlayers = `get all players sorted by points`()
                val playerFound = allPlayers.find { it.pseudo == pseudo }!!
                val ranking = allPlayers.indexOf(playerFound) + 1
                return PlayerWithRanking(playerFound.pseudo, playerFound.points, ranking)
            } else -> throw PlayerNotExistsException(pseudo)
        }
    }

    override fun `remove all`() = repositoryPlayer.deleteAll()

    private fun `get all players sorted by points`() = repositoryPlayer.getAll().sortedByDescending { it.points }

}
