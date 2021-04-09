package tournament

import java.lang.IllegalArgumentException

class PlayerNotExistsException(pseudo: String): IllegalArgumentException("Player $pseudo does not exist")