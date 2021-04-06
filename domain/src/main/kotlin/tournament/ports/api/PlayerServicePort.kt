package tournament.ports.api

import tournament.entities.Player

interface PlayerServicePort {
    fun `add new player`(pseudo: String) : Player
    fun `retrieve all players`(): List<Player>
    fun `update points player`(pseudo: String, points: Int): Boolean
}