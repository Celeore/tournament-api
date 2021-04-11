package tournament.api.app

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class CorsFilter : ContainerResponseFilter {
    override fun filter(
        request: ContainerRequestContext?,
        response: ContainerResponseContext,
    ) {
        response.headers.add("Access-Control-Allow-Origin", "*")
        response.headers.add("Access-Control-Allow-Headers",
            "CSRF-Token, X-Requested-By, Authorization, Content-Type")
        response.headers.add("Access-Control-Allow-Credentials", "true")
        response.headers.add("Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    }
}

