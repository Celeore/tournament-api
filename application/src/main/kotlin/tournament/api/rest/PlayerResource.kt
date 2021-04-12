package tournament.api.rest

import tournament.entities.Player
import tournament.entities.PlayerWithRanking
import tournament.ports.api.PlayerServicePort
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
class PlayerResource(private val service: PlayerServicePort) {

    @GET
    @Path("{pseudo}")
    fun `get player informations`(@PathParam("pseudo")pseudo: String): PlayerWithRankingApi
            = toPlayerWithRankingApi(service.`get informations`(pseudo))

    @GET
    fun `get all players sorted by points`():List<PlayerWithRankingApi> = service.`retrieve all players sorted by points`().map { toPlayerWithRankingApi(it) }.toMutableList()

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun `save a new player`(@QueryParam("pseudo") pseudo: String): PlayerApi = toPlayerApi(service.`add new player`(pseudo))

    @PATCH
    @Path("{pseudo}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun `modify player points`(@PathParam("pseudo")pseudo: String, playerPutApi: PlayerPutApi):Boolean = service.`update points player`(pseudo, playerPutApi.points)

    private fun toPlayerApi(player: Player): PlayerApi {
        return PlayerApi(player.pseudo, player.points)
    }

    private fun toPlayerWithRankingApi(player: PlayerWithRanking): PlayerWithRankingApi {
        return PlayerWithRankingApi(player.pseudo, player.points, player.position)
    }
}
