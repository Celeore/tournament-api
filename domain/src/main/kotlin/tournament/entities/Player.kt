package tournament.entities

class PlayerWithRanking( pseudo: String, points: Int, val ranking: Int):Player(pseudo, points)

open class Player(val pseudo: String, val points: Int = 0)