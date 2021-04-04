package tournament.ports.api

import tournament.entities.Player

interface PlayerServicePort {
    fun addPlayer(pseudo: String) : Player
    fun getAll(): List<Player>
}