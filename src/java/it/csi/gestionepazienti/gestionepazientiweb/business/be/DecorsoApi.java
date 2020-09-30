/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;

@Path("/decorso")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "Decorso", produces = MediaType.APPLICATION_JSON)
@ApiResponsesDefault
public interface DecorsoApi  {



	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiImplicitParamsApp
	public Response insertDecorso(DecorsoForElenco decorso, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@PUT
	@Path("{idDecorso}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiImplicitParamsApp
	public Response updateDecorso(@PathParam("idDecorso") Long idDecorso, DecorsoForElenco decorso, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	@DELETE
	@Path("{idDecorso}")
	@ApiImplicitParamsApp
	public Response deleteDecorso(@PathParam("idDecorso") Long idDecorso, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);
	
	
	
	//    @GET
//    @Path("/report/csv")
//    @Produces({ "text/csv" })
//    public Response getTableCSV( @QueryParam("table") String table, @QueryParam("lang") String lang, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );
//
//    @GET
//    @Path("/report/table")
//    @Produces({ "application/json" })
//    public Response getTable( @QueryParam("table") String table,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );
}