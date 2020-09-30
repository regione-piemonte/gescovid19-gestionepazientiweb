/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;

@Path("/referti")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Referti", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface RefertiApi  {

	@POST
	@Path("/csv")
	@Consumes({ MediaType.TEXT_PLAIN })
	//@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiImplicitParamsApp
	public Response insertReferti(
			//@FormDataParam("file") InputStream uploadedInputStream,
			String payloadPlain,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest req);
	
}
