/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.DecodeArea;

@Path("/postiletto/area/decode")

@Produces({ "application/json" })


public interface DecodeAreaApi  {


	@GET
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "get lista decode area", notes = "", response = DecodeArea.class, tags = { "TOH", })
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco decode area", response = DecodeArea.class),
	@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getElencoDecodeArea(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

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
