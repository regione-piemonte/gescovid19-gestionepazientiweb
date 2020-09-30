/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;

@Path("/aura")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Aura", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface AuraApi  {


	@GET
	@Produces({ "application/json" })
	@Path("{codiceFiscale}")
	@ApiImplicitParamsApp
	public Response getSoggettiAuraByCF(@PathParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Produces({ "application/json" })
	@Path("{nome}/{cognome}/{dd}/{mm}/{yyyy}")
	@ApiImplicitParamsApp
	public Response getSoggettiAuraByNomeCognomeData(@PathParam("nome") String nome,@PathParam("cognome") String cognome,
			@PathParam("dd") String giorno, @PathParam("mm") String mese,@PathParam("yyyy") String anno,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Produces({ "application/json" })
	@Path("find/{id}")
	@ApiImplicitParamsApp
	public Response findAnagraficaById(@PathParam("id") String id,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

}
