package tournament.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

abstract class Player(open val pseudo: String, open val points: Int)

data class PlayerWithRankingApi(@JsonProperty("pseudo")override val pseudo: String, @JsonProperty("points") override val points: Int, @JsonProperty("ranking") val ranking: Int) :
    Player(pseudo, points)

data class PlayerApi(
    @JsonProperty("pseudo") override val pseudo: String,
    @JsonProperty("points") override var points: Int = 0
) : Player(pseudo, points)

class PlayerPutApi(
    @JsonProperty("points") var points: Int
)
