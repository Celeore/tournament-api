package tournament.repository

import tournament.entities.Player


class PlayerInMemoryRepository {
    private val players = mutableMapOf<String, PlayerRepository>()

    internal fun save(player: Player): PlayerRepository {
        val playerRepository = PlayerRepository(player.pseudo, player.points)
        players[player.pseudo] = playerRepository
        return playerRepository
    }

    internal fun getAll(): List<PlayerRepository>{
        return players.values.toList()
    }

    internal fun updatePlayer(pseudo: String, points: Int) {
        players[pseudo]?.points = points
    }

    internal fun notExistsPlayer(pseudo: String): Boolean = !players.containsKey(pseudo)

    internal fun deleteAll() = players.clear()

}
