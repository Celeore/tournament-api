package tournament

import tournament.data.Player
import tournament.data.PlayerId
import tournament.ports.spi.PlayerPersistencePort

class TournamentLogic(
        private val repository: PlayerPersistencePort,
        private val idFactory: () -> PlayerId
) : Tournament {
    override fun addPlayer(pseudo: String): Player =
            Player(idFactory(), pseudo)
                    .also { newPlayer -> repository.save(newPlayer) }


    override fun listPlayers(): List<Player> =
            repository.getAll()

}
