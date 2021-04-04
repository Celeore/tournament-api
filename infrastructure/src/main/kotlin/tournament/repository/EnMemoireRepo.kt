package tournament.repository

import tournament.entities.Player

class EnMemoireRepo {
    private val players = mutableMapOf<String, Player>()

    fun save(player: Player): Player {
        players[player.pseudo] = player
        return player
    }

    fun getAll(): List<Player> = players.values.toList()

}
