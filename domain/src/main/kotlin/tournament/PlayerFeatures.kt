package tournament

import tournament.entities.Player
import tournament.entities.PlayerWithRanking
import tournament.ports.api.PlayerServicePort
import tournament.ports.spi.PlayerPersistencePort

class PlayerFeatures(
    private val repositoryPlayer: PlayerPersistencePort,
) : PlayerServicePort {
    override fun `add new player`(pseudo: String): Player =
        Player(pseudo, 0)
            .also { newPlayer -> repositoryPlayer.save(newPlayer) }


    override fun `retrieve all players sorted by points`(): List<PlayerWithRanking> = `get all players sorted by points`()

    override fun `update points player`(pseudo: String, points: Int): Boolean =
        repositoryPlayer.updatePoints(pseudo, points)


    override fun `get informations`(pseudo: String): PlayerWithRanking {
        val allPlayers = `get all players sorted by points`()
        return allPlayers.find { it.pseudo == pseudo } ?: throw PlayerNotExistsException(pseudo)
    }

    override fun `remove all`() {
        TODO("Not yet implemented")
    }

    private fun `get all players sorted by points`() = repositoryPlayer.getAll().sortedByDescending { it.points }.withRankingInformation()

    private fun List<Player>.withRankingInformation(): List<PlayerWithRanking> {
        return this.map {
            val ranking = this.indexOf(it) + 1
            PlayerWithRanking(it.pseudo,it.points, ranking) }
    }
}


