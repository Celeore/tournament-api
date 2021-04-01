package tournament

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*


abstract class TournamentContract {
    abstract val tournament: Tournament

    @Test
    fun `list players`(){
        val player1 = tournament.addPlayer("toto")
        val player2 = tournament.addPlayer("tata")

        val players = tournament.listPlayers()

        assertThat(players).containsExactlyInAnyOrder(player1, player2)


    }
}