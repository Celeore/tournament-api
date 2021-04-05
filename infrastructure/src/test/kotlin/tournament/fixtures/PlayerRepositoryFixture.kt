package tournament.fixtures

import tournament.repository.PlayerRepository

class PlayerRepositoryFixture {
    companion object {
        private val PLAYER_REPOSITORY_WITH_PSEUDO_TOTO: PlayerRepository = PlayerRepository(pseudo = "toto")
        private val PLAYER_REPOSITORY_WITH_PSEUDO_TATA: PlayerRepository = PlayerRepository(pseudo = "tata")

        fun hasPlayerRepositoryToto(): PlayerRepository {
            return PLAYER_REPOSITORY_WITH_PSEUDO_TOTO
        }
        fun hasPlayerRepositoryTata(): PlayerRepository {
            return PLAYER_REPOSITORY_WITH_PSEUDO_TATA
        }

        fun hasPlayerRepositoryList(): List<PlayerRepository> {
            return listOf(PLAYER_REPOSITORY_WITH_PSEUDO_TATA, PLAYER_REPOSITORY_WITH_PSEUDO_TOTO)
        }
    }
}