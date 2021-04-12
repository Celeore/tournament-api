package tournament.fixtures

import tournament.repository.inmemory.PlayerRepository

class PlayerRepositoryFixture {
    companion object {
        private val PLAYER_REPOSITORY_WITH_PSEUDO_TOTO: PlayerRepository = PlayerRepository(pseudo = "toto")
        private val PLAYER_REPOSITORY_WITH_PSEUDO_TATA: PlayerRepository = PlayerRepository(pseudo = "tata")

        fun hasPlayerRepositoryToto(): PlayerRepository {
            return PLAYER_REPOSITORY_WITH_PSEUDO_TOTO.copy()
        }
        fun hasPlayerRepositoryTata(): PlayerRepository {
            return PLAYER_REPOSITORY_WITH_PSEUDO_TATA.copy()
        }

        fun hasPlayerRepositoryList(): List<PlayerRepository> {
            return listOf(PLAYER_REPOSITORY_WITH_PSEUDO_TATA, PLAYER_REPOSITORY_WITH_PSEUDO_TOTO)
        }
    }
}
