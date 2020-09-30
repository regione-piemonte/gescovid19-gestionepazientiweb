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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.DecodeTipoEvento;
import it.csi.gestionepazienti.gestionepazientiweb.dto.EsitoDimissioni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Laboratorio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Nazione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Regione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RepartoRicovero;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RisTampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoContattoTipo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TamponeMotivo;

@Path("/decodifiche")

@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Decodifiche", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface DecodificheApi {

	
	@GET
	@Path("tiposoggetto")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista tipo soggetto", notes = "", response = Asr.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco asr", response = Asr.class),})
	public Response getElencoTipoSoggetto(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("asr")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista asr", notes = "", response = Asr.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco asr", response = Asr.class), })
	public Response getElencoAsr(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Path("asl")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista asr", notes = "", response = Asr.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco asr", response = Asr.class), })
	public Response getElencoAsl(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
	@Path("comuni")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista comuni", notes = "", response = Comuni.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco comuni", response = Comuni.class),})
	public Response getElencoComuni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("testcovid")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista test covid", notes = "", response = Comuni.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco testcovid", response = Comuni.class), })
	public Response getElencoTestCovid(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	
	@GET
	@Path("repartoricovero")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista reparto ricovero", notes = "", response = RepartoRicovero.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco reparto ricovero", response = RepartoRicovero.class), })
	public Response getElencoRepartoRicovero(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("ristampone")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista risultato tampone", notes = "", response = RisTampone.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco risultato tampone", response = RisTampone.class),})
	public Response getElencoRisTampone(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	@GET
	@Path("esitodimissioni")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista esito dimissioni", notes = "", response = EsitoDimissioni.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco esito dimissioni", response = EsitoDimissioni.class),})
	public Response getElencoEsitoDimissioni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);	
	
	
	
	@GET
	@Path("laboratorio")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista Laboratorio", notes = "", response = Laboratorio.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco Laboratorio", response = Laboratorio.class),})
	public Response getElencoLaboratorio(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);	

	@GET
	@Path("tipoevento")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "get lista tipo evento", notes = "", response = DecodeTipoEvento.class)
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco evento", response = DecodeTipoEvento.class), })
	public Response getElencoTipoEvento(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	
	@GET
	@Path("soggetto-contatto-tipi")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "Tipi contatto di un soggetto", notes = "", response = SoggettoContattoTipo.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(
			value = { 
					@io.swagger.annotations.ApiResponse(code = 200, message = "Soggetto", response = SoggettoContattoTipo.class, responseContainer = "List"),
			 })    
	public Response getTipiContattoSoggetto(
			
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest req);
	
	
	@GET
	@Path("tampone-motivi")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "Motivi di un tampone", notes = "", response = TamponeMotivo.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
	    		@ApiResponse(code = 200, message = "Success", response = TamponeMotivo.class, responseContainer = "List")
	 })
	public Response getTamponeMotivi(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	@GET
	@Path("nazioni")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "Elenco nazioni", notes = "", response = Nazione.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
	    		@ApiResponse(code = 200, message = "Success", response = Nazione.class, responseContainer = "List")
	 })
	public Response getElencoNazioni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("regioni")
	@Produces({ "application/json" })
	@ApiImplicitParamsApp
	@io.swagger.annotations.ApiOperation(value = "Elenco regioni", notes = "", response = Regione.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = { 
	    		@ApiResponse(code = 200, message = "Success", response = Regione.class, responseContainer = "List")
	 })
	public Response getElencoRegioni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
}
