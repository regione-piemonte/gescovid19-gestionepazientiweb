/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be.visurapazienti;

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

@Path("/visura/soggetti")

@Produces({ "application/json" })


public interface VisuraSoggettoApi  {

	@GET
	@Path("/report/xlsx")
	@Produces({ "application/xlsx" })
	public Response getElencoSoggettiByDomicilioXlsx(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	

	@GET
	@Produces({ "application/json" })
	@GZIP
	Response getElencoSoggettiByDomicilio(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

}
