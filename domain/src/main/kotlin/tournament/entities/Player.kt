package tournament.data

import java.util.*

class Player(val id: PlayerId, val pseudo: String) {

}
data class PlayerId(val value: UUID) {
    companion object {
        fun random() = PlayerId(UUID.randomUUID())
    }
}
