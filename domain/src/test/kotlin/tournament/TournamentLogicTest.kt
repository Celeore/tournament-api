package tournament

import org.junit.jupiter.api.Test
import tournament.data.Player
import tournament.data.PlayerId
import tournament.ports.spi.PlayerPersistencePort
import org.assertj.core.api.Assertions.*

class TournamentLogicTest : TournamentContract() {
    private val repository = InMemoryPlayerRepository()
    private val recordedPlayerIdFactory = RecordingPlayerIdFactory();
    override val tournament = TournamentLogic(repository, recordedPlayerIdFactory)

    @Test
    fun createsAnPlayer() {
        val playerPseudo = "toto"
        val playerAdded = tournament.addPlayer(playerPseudo)

        val id = recordedPlayerIdFactory.last()
        val playerExpected = Player(id, playerPseudo)
        assertThat(playerAdded).usingRecursiveComparison().isEqualTo(playerExpected)
    }

    @Test
    fun listPlayers() {
        val firstPlayerPseudo = "toto"
        val secondPlayerPseudo = "tata"
        val firstPlayerAdded = tournament.addPlayer(firstPlayerPseudo)
        val secondPlayerAdded = tournament.addPlayer(secondPlayerPseudo)

        val listPlayers = tournament.listPlayers()
        assertThat(listPlayers).containsExactlyInAnyOrder(firstPlayerAdded, secondPlayerAdded)
    }

}

class InMemoryPlayerRepository : PlayerPersistencePort {
    private val players = mutableMapOf<PlayerId, Player>()

    override fun save(player: Player) {
        players[player.id] = player
    }

    override fun getAll(): List<Player> = players.values.toList()

}

class RecordingPlayerIdFactory : () -> PlayerId {
    private val values = mutableListOf<PlayerId>()

    override fun invoke(): PlayerId {
        return PlayerId.random().also { values.add(it) }
    }

    fun last(): PlayerId {
        return values.last()
    }
}
