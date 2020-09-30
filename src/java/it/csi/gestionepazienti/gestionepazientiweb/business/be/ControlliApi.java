/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.controllodati.ControlloDati;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;

@Path("/controlli")

@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Controlli", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface ControlliApi {

	
	@GET
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista soggetti", notes = "", response = SoggettoForElenco.class)
	@io.swagger.annotations.ApiResponses(value = { 
		@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco Soggetti", response = SoggettoForElenco.class, responseContainer = "List",
				responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)})
	})
	public Response getElencoSoggettiControlli(
			@QueryParam("codiceControllo") String codiceControllo,
			@QueryParam("filter") String filter,
			//Paginazione
			@QueryParam("rowPerPage") Long rowPerPage,
			@QueryParam("pageNumber") Long pageNumber,
			@QueryParam("orderby") String orderby, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	@GET
	@Path("/lista-indicatori")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista indicatori", notes = "", response = ControlloDati.class)
	@io.swagger.annotations.ApiResponses(value = { 
		@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco ControlloDati", response = ControlloDati.class, responseContainer = "List",
				responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)})
	})
	public Response getListaIndicatori(			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	
}
