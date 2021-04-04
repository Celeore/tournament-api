package tournament.api

import tournament.entities.Player
import tournament.ports.api.PlayerServicePort
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
class PlayerResource(private val service: PlayerServicePort) {

    @GET
    fun `get all players`():List<PlayerApi> = service.getAll().map { toPlayerApi(it) }.toMutableList()
    @POST
    fun `save a new player`(@QueryParam("pseudo") pseudo: String): PlayerApi = toPlayerApi(service.addPlayer(pseudo))

    private fun toPlayerApi(player: Player): PlayerApi{
        return PlayerApi(player.pseudo)
    }
}