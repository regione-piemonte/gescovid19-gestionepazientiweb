/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.GZIP;

import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForSegnalazione;

@Path("/mmgvisura/soggetti")

@Produces({ "application/json" })


public interface MmgVisuraSoggettoApi  {

	@GET
	@Path("/report/xlsx")
	@Produces({ "application/xlsx" })
	public Response getElencoSoggettiByMedicoXlsx(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	

	@GET
	@Produces({ "application/json" })
	@GZIP
	Response getElencoSoggettiByMedico(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Path("{idSoggetto}")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "get soggetto dettaglio", notes = "", response = SoggettoForElenco.class, tags = { "TOH", })
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Soggetto", response = SoggettoForElenco.class),
	@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getSoggettoByIdSoggetto(@PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@POST
	@Consumes({ "application/json" })
	public Response insertSoggettoSegnalazione(SoggettoForSegnalazione soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@GET
	@Path("/by-cf/{codiceFiscale}")
	@Produces({ "application/json" })
	Response searchSoggettoSegnalazione(@PathParam("codiceFiscale") String codiceFiscale,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
}
