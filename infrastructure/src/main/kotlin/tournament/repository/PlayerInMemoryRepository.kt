package tournament.repository

import tournament.entities.Player


class PlayerInMemoryRepository {
    private val players = mutableMapOf<String, PlayerRepository>()

    fun save(player: Player): PlayerRepository {
        val playerRepository = PlayerRepository(player.pseudo, player.points)
        players[player.pseudo] = playerRepository
        return playerRepository
    }

    fun getAll(): List<PlayerRepository>{
        return players.values.toList()
    }

    fun updatePlayer(pseudo: String, points: Int) {
        players[pseudo]?.points = points
    }

    fun notExistsPlayer(pseudo: String): Boolean = !players.containsKey(pseudo)

    fun removeAll(): Unit = players.clear()
}
