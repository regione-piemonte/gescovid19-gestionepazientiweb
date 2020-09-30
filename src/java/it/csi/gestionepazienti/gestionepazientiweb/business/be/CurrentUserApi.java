/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;

@Path("/currentUser")
@Produces({ "application/json" })
@Api(value = "Current User", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface CurrentUserApi {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "get current user", 
		notes = "Restituisce informazioni sull'utente correntemente loggato.", 
		response = Utenti.class)
	
	@ApiImplicitParamsApp
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "l'utente corrente", response = Utenti.class),
			})
	public Response getCurrentUser(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest req);

}
