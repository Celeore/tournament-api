package tournament.api

import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
class PlayerResource(private val service: PlayerServicePort) {

    @GET
    fun `get all players sorted by points`():List<PlayerApi> = service.`retrieve all players sorted by points`().map { toPlayerApi(it) }.toMutableList()
    @POST
    fun `save a new player`(@QueryParam("pseudo") pseudo: String): PlayerApi = toPlayerApi(service.`add new player`(pseudo))

    @PATCH
    @Path("{pseudo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun `modify player points`(@PathParam("pseudo")pseudo: String, playerApi: PlayerApi):Boolean = service.`update points player`(pseudo, playerApi.points)

    private fun toPlayerApi(player: Player): PlayerApi{
        return PlayerApi(player.pseudo, player.points)
    }
}