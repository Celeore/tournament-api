package tournament.repository

import tournament.entities.Player
data class PlayerRepository(internal val pseudo: String, var points: Int = 0) {
    internal fun toPlayer(): Player {
        return Player(this.pseudo)
    }
}

