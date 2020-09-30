/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiImplicitParamsApp;
import it.csi.gestionepazienti.gestionepazientiweb.annotation.ApiResponsesDefault;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;

@Path("/localLogout")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Locallogout", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface LogoutApi {


	@GET
	@ApiImplicitParamsApp
	public Message localLogout(@Context HttpServletRequest req);
}
