package tournament.ports.spi

import tournament.data.Player

interface PlayerPersistencePort {
    fun save(player: Player)
    fun getAll(): List<Player>
}
