package tournament.repository

import tournament.entities.Player


class PlayerInMemoryRepository {
    private val players = mutableMapOf<String, PlayerRepository>()

    fun save(player: Player): PlayerRepository {
        val playerRepository = PlayerRepository(player.pseudo)
        players[player.pseudo] = playerRepository
        return playerRepository
    }

    fun getAll(): List<PlayerRepository> = players.values.toList()

}
