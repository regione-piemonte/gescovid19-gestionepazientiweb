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

import org.jboss.resteasy.annotations.GZIP;

import io.swagger.annotations.Api;
import io.swagger.annotations.ResponseHeader;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;

@Path("/istituto-scolastico")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Istituto Scolastico", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface IstitutoScolasticoApi  {

	
	@GET
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco istituti scolastici", response = IstitutoScolastico.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}),
	 })
	public Response getIstituti(
			@QueryParam("denominazione") String denominazione, 
			@QueryParam("ordine") String ordine,
			@QueryParam("provincia") String provincia,
			@QueryParam("comune") String comune,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
    @Path("/lista-ordini")
    @Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco ordini", response = String.class, responseContainer = "List"),
	 })
	public Response getElencoOrdini(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("/lista-province")
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco province", response = String.class, responseContainer = "List"),
	})
	public Response getElencoProvince(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("/lista-comuni")
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco comuni", response = String.class, responseContainer = "List"),
	})
	public Response getElencoComuni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

}
