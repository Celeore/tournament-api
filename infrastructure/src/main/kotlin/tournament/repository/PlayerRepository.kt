package tournament.repository

import tournament.entities.Player
data class PlayerRepository(internal val pseudo: String) {
    internal fun toPlayer(): Player {
        return Player(this.pseudo)
    }
}

