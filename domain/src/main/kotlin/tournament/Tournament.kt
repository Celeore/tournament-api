package tournament

import tournament.data.Player

interface Tournament {
    fun addPlayer(pseudo: String) : Player
    fun listPlayers(): List<Player>

}
