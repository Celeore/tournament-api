package fixtures

import tournament.api.PlayerApi

class PlayerApiFixture {

    companion object {
        private val PLAYER_API_WITH_PSEUDO_TOTO: PlayerApi = PlayerApi(pseudo = "toto")
        private val PLAYER_API_WITH_PSEUDO_TATA: PlayerApi = PlayerApi(pseudo = "tata")

        fun hasPlayerToto(): PlayerApi {
            return PLAYER_API_WITH_PSEUDO_TOTO;
        }
        fun hasPlayerTata(): PlayerApi {
            return PLAYER_API_WITH_PSEUDO_TATA;
        }

        fun hasPlayerApiList(): List<PlayerApi> {
            return listOf(PLAYER_API_WITH_PSEUDO_TATA, PLAYER_API_WITH_PSEUDO_TOTO)
        }
    }

}