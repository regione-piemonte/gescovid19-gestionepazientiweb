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
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Payload;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Payload2;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RMedicoMedico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.MedicoExt;



@Path("/mmg-deleganti")
@Produces({ "application/json" })
@Api(value = "MMG Deleganti", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface MmgVisuraDeleganteApi  {

	@GET
	@GZIP
	@Path("/by-cf/{codiceFiscale}")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get medici deleganti", notes = "", response = MedicoExt.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = {
    		@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco medici Deleganti", response = MedicoExt.class, responseContainer = "List",
    				responseHeaders = {@ResponseHeader(name = "Rows-Number", description = "Numero totale elementi", response = long.class)}
    		)
    })
	public Response searchMediciDeleganti(
			@PathParam("codiceFiscale") String codiceFiscale,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest req);
	
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "inserimento di una delega", notes = "", response = RMedicoMedico.class)
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 200, message = "Delega medico", response = RMedicoMedico.class),
			 })    
	public Response insertDelega(Payload2 paylod, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@PUT
	@Path("/aggiorna-validita-delega/{idMedicoDelegato}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiImplicitParamsApp
	
	public Response updateDelega(@PathParam("idMedicoDelegato") Long idMedicoDelegato, Payload paylod, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@DELETE
	@Path("/cancella-delega/{idMedicoDelegato}")
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "delete di una delega", notes = "")
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 200, message = "Delete delega medico OK"),
			 })
	public Response deleteDelega(@PathParam("idMedicoDelegato") Long idMedicoDelegato, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	


	@GET
	@GZIP
	@Path("/medici-delegabili")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get medici delegabili", notes = "", response = Medico.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = {
    		@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco medici delegabili", response = Medico.class, responseContainer = "List")}
    		)
    public Response getMediciDelegabili(
			@QueryParam("codiceFiscale") String codiceFiscale, 
			@QueryParam("cognome") String cognome, 
			@QueryParam("nome") String nome, 
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest req);
	
	
	@GET
	@GZIP
	@Path("/{codiceFiscale}/medici-delegati")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get medici delegati", notes = "", response = MedicoExt.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = {
    		@io.swagger.annotations.ApiResponse(code = 200, message = "Elenco medici delegati", response = MedicoExt.class, responseContainer = "List")}
    		)
    public Response getMediciDelegati(
    		@PathParam("codiceFiscale") String codiceFiscale,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest req);


}
