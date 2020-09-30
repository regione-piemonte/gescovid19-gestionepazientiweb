/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.GZIP;

import io.swagger.annotations.Api;
import io.swagger.annotations.ResponseHeader;
import io.swagger.jaxrs.PATCH;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElencoNonAbbinati;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForReport;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TestExt;

@Path("/tampone")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Tampone", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface TamponeApi  {

	@GET
    @Path("/report/csv")
    @Produces({ "text/csv" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco tamponi", response = TamponeForReport.class, responseContainer = "List"),
	 })
	public Response getElencoTamponiCsv(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
    @Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco tamponi", response = TamponeForReport.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}),
	 })
	public Response getElencoTamponi(
			//Paginazione
			@QueryParam("orderby") String orderby, 
			@QueryParam("rowPerPage") Long rowPerPage,
			@QueryParam("pageNumber") Long pageNumber,
			@QueryParam("descending") String descendingStr,
			
			//Filtri
			@QueryParam("filter") String filter,
			@QueryParam("infoInsufficienti") String infoInsufficienti,
			@QueryParam("tamponeAutorizzato") String tamponeAutorizzato,
			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@POST
	@Consumes({ "application/json" })
	@ApiImplicitParamsApp
	public Response insertTampone(TamponeForElenco tampone, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@PUT
	@Consumes({ "application/json" })
	@ApiImplicitParamsApp
	public Response updateTampone(TamponeForElenco tampone, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@DELETE
	@Path("{idTampone}")
	@ApiImplicitParamsApp
	public Response deleteTampone(@PathParam("idTampone") Long idTampone, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	
	@PATCH
	@Consumes({ "application/json" })
	@ApiImplicitParamsApp
	public Response risultatoTampone(TamponeForElenco tampone, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);


	@GET
	@Path("/test-referti/{idSoggetto}")
    @Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco test", response = TestExt.class, responseContainer = "List"),
	 })
	public Response getElencoTest(@PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
	@Path("/non-abbinati/{idAsr}")
    @Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco test non abbinati", response = TamponeForElencoNonAbbinati.class, responseContainer = "List"),
	 })
	public Response getElencoTestNonAbbinati(@PathParam("idAsr") Long idAsr, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
}
