package tournament.ports.spi

import tournament.entities.Player

interface PlayerPersistencePort {
    fun save(player: Player): Player
    fun getAll(): List<Player>
}
