package tournament.ports.spi

import tournament.entities.Player

interface PlayerPersistencePort {
    fun save(player: Player): Player
    fun getAll(): List<Player>
    fun updatePoints(pseudo: String, points: Int)
    fun exists(pseudo: String): Boolean
}
