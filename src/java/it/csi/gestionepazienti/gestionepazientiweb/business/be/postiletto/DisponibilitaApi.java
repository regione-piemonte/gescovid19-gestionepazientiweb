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
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReport;

@Path("/postiletto/disponibilita")

@Produces({ "application/json" })


public interface DisponibilitaApi  {


	@GET
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "get lista disponibilita", notes = "", response = DisponibilitaForReport.class, tags = { "TOH", })
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco disponibilita", response = DisponibilitaForReport.class),
	@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getElencoDisponibilitaReport(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Path("/transposed")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "get lista disponibilita", notes = "", response = DisponibilitaForReport.class, tags = { "TOH", })
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco disponibilita", response = DisponibilitaForReport.class),
	@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getElencoDisponibilitaReportTransposed(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

	@GET
	@Path("/report/csv")
	@Produces({ "application/csv" })
	@io.swagger.annotations.ApiOperation(value = "get lista disponibilita", notes = "", response = DisponibilitaForReport.class, tags = { "TOH", })
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco disponibilita", response = DisponibilitaForReport.class),
	@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getElencoDisponibilitaReportTransposedCSV(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);


	@GET
	@Path("/report/xlsx")
	@Produces({ "application/xlsx" })
	@io.swagger.annotations.ApiOperation(value = "get lista disponibilita", notes = "", response = DisponibilitaForReport.class, tags = { "TOH", })
	@io.swagger.annotations.ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Elenco disponibilita", response = DisponibilitaForReport.class),
	@io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
	public Response getElencoDisponibilitaReportTransposedXlsx(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req);

}
