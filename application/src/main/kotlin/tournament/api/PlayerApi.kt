package tournament.api

import com.fasterxml.jackson.annotation.JsonProperty

data class PlayerApi(@JsonProperty("pseudo") val pseudo:String)