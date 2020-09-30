/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoContatto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForTrasferimento;

@Path("/soggetto")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Soggetto", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface SoggettoApi  {

	@GET
    @Path("/report/csv")
    @Produces({ "text/csv" })
	@ApiImplicitParamsApp
	public Response getElencoSoggettiCsv(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("/report/xlsx")
	@Produces({ "application/xlsx" })
	@ApiImplicitParamsApp
	public Response getElencoSoggettiXlsx(
			
			@QueryParam("codiceFiscale") String codiceFiscale,
			@QueryParam("cognome") String cognome,
			@QueryParam("nome") String nome,
			@QueryParam("idTipoEvento") String idTipoEventoStr,
			@QueryParam("idTipoSoggettoStr") String idTipoSoggettoStr,
			@QueryParam("comuneRicovero") String comuneRicovero,
			@QueryParam("comuneDomicilio") String comuneDomicilio,
			@QueryParam("comuneResidenza") String comuneResidenza,
			@QueryParam("filter") String filter,
			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
	@Produces({ "application/json" })
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get soggetti pazienti", notes = "", response = SoggettoForElenco.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco Soggetti", response = SoggettoForElenco.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}),
			
			
			
	 })
	public Response getElencoSoggetti(
			//Paginazione
			@QueryParam("orderby") String orderby, 
			@QueryParam("rowPerPage") Long rowPerPage,
			@QueryParam("pageNumber") Long pageNumber,
			@QueryParam("descending") String descendingStr,
			
			//Filtri
			@QueryParam("idTipoEvento") String idTipoEventoStr,
			@QueryParam("idTipoSoggettoStr") String idTipoSoggettoStr,
			@QueryParam("filter") String filter,
			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	@GET
	@Produces({ "application/json" })
	@Path("/extra/")
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get soggetti pazienti", notes = "", response = SoggettoForElenco.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco Soggetti", response = SoggettoForElenco.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)})
			})
	public Response getElencoSoggettiExtraAsr(
			//Paginazione
			@QueryParam("orderby") String orderby, 
			@QueryParam("rowPerPage") Long rowPerPage,
			@QueryParam("pageNumber") Long pageNumber,
			@QueryParam("descending") String descendingStr,
			
			//Filtri
			@QueryParam("codiceFiscale") String codiceFiscale, 
			@QueryParam("cognome") String cognome, 
			@QueryParam("nome") String nome, 
			@QueryParam("cognomeexact") Boolean cognomeexact,
			@QueryParam("idTipoEvento") String idTipoEventoStr,
			@QueryParam("idTipoSoggettoStr") String idTipoSoggettoStr,
			@QueryParam("filter") String filter,
			
			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
	@Path("{idSoggetto}")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get soggetto dettaglio", notes = "", response = SoggettoForDettaglio.class)
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 200, message = "Soggetto", response = SoggettoForDettaglio.class),
			 })    
	public Response getSoggettoByIdSoggetto(@PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@POST
	@Path("contatti")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "inserisce il contatto (contagio) di un soggetto", notes = "", response = SoggettoContatto.class)
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 201, message = "Creato", response = SoggettoContatto.class),
			 })    
	public Response inserisciContatto(
			SoggettoContatto soggettoContatto,
			
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest req);
	


	
	
	@POST
	@Consumes({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "inserimento di un soggetto", notes = "", response = SoggettoForDettaglio.class)
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 200, message = "Soggetto", response = SoggettoForDettaglio.class),
			 })    
	public Response insertSoggetto(SoggettoForDettaglio soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	@PUT
	@Consumes({ "application/json" })
	@ApiImplicitParamsApp
	public Response updateSoggetto(SoggettoForDettaglio soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
	@Produces({ "application/json" })
    @Path("/trasferimenti")
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get soggetti trasferiti", notes = "", response = SoggettoForTrasferimento.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco Soggetti trasferiti", response = SoggettoForTrasferimento.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)})
			})
	Response getElencoSoggettiTrasferiti(//Paginazione
			@QueryParam("orderby") String orderby, 
			@QueryParam("rowPerPage") Long rowPerPage,
			@QueryParam("pageNumber") Long pageNumber,
			@QueryParam("descending") String descendingStr,
			
			//Filtri
			@QueryParam("filter") String filter,
			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Produces({ "application/json" })
    @Path("/quarantena")
	@GZIP
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get soggetti quarantena", notes = "", response = SoggettoForTrasferimento.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
			@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco Soggetti quarantena", response = SoggettoForTrasferimento.class, responseContainer = "List",
					responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)})
			})
	Response getElencoSoggettiQuarantena(
			//Paginazione
			@QueryParam("orderby") String orderby, 
			@QueryParam("rowPerPage") Long rowPerPage,
			@QueryParam("pageNumber") Long pageNumber,
			@QueryParam("descending") String descendingStr,
			
			//Filtri
			@QueryParam("filter") String filter,
			
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Produces({ "application/json" })
    @Path("/segnalati")
	@GZIP
	@ApiImplicitParamsApp
	Response getElencoSoggettiSegnalati(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
    @Path("/quarantena/report/csv")
    @Produces({ "text/csv" })
	@ApiImplicitParamsApp
	public Response getElencoSoggettiQuarantenaCsv(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
}
