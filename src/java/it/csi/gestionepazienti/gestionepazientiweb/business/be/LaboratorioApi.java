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
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.LaboratorioCompleto;

@Path("/laboratorio")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Laboratorio", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface LaboratorioApi  {


	@GET
    @Produces({ "application/json" })
	@ApiImplicitParamsApp
	
	
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 200, message = "Success",response = LaboratorioCompleto.class, responseContainer = "List"),
			})
	public Response getElencoLaboratori(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

}
