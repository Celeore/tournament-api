package tournament.ports.api

import tournament.entities.Player
import tournament.entities.PlayerWithRanking

interface PlayerServicePort {
    fun `add new player`(pseudo: String) : Player
    fun `retrieve all players sorted by points`(): List<Player>
    fun `update points player`(pseudo: String, points: Int): Boolean
    fun `get informations`(pseudo: String): PlayerWithRanking
}