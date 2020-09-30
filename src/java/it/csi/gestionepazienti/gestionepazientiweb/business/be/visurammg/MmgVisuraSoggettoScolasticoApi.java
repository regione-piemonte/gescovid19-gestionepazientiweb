/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.GZIP;

import io.swagger.annotations.ResponseHeader;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TipoSoggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForPersonaleScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElenco;

@Path("/mmgvisura/soggetti-scolastici")

@Produces({ "application/json" })

public interface MmgVisuraSoggettoScolasticoApi {

	@GET
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco soggetti", response = SoggettoForPersonaleScolastico.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}),
	 })
	public Response getElencoSoggettiScolasticiByMedico(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest req);
	
	@GET
	@Path("/for-ente")
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco soggetti", response = SoggettoForPersonaleScolastico.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}),
	 })
	public Response getElencoSoggettiScolasticiForEnte(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest req);

	@GET
	@Path("/ruoli-soggetti-scolastici")
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco soggetti", response = TipoSoggetto.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}),
	 })
	public Response getDecodificaTipoSoggettoScolastico(@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Path("{idSoggetto}")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "get soggetto scolastico dettaglio", notes = "", response = SoggettoForPersonaleScolastico.class)
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Soggetto", response = SoggettoForPersonaleScolastico.class),
			@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getSoggettoScolasticoByIdSoggetto(@PathParam("idSoggetto") Long idSoggetto,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest req);
	
	
	@POST
	@Consumes({ "application/json" })
	@ApiImplicitParamsApp
	public Response insertSoggettoScolastico(SoggettoForPersonaleScolastico soggettoScolastico, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@PUT
	@Consumes({ "application/json" })
	@Path("{idSoggetto}")
	@ApiImplicitParamsApp
	public Response updateSoggettoScolastico(@PathParam("idSoggetto") Long idSoggetto, SoggettoForPersonaleScolastico soggettoScolastico, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@DELETE
	@Path("{idSoggetto}")
	@ApiImplicitParamsApp
	public Response deleteSoggettoScolastico(@PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

}
