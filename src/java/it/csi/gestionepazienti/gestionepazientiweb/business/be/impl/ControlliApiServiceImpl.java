package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toLike;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.ControlliApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.controllodati.ControlloDati;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.controllodati.generated.ControlloDatiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.IndicatoriMapper;

public class ControlliApiServiceImpl extends AuditableApiServiceImpl implements ControlliApi {

	
	@Autowired
	private ControlloDatiMapper controlloDatiMapper;
	
	@Autowired
	private IndicatoriMapper indicatoriMapper;
	
	
	@Override
	public Response getElencoSoggettiControlli(String codiceControllo, String filter, Long rowPerPage, Long pageNumber, String orderby,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		
		try {

			log.info("Inizio esecuzione getElencoSoggettiControlli");
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			if(codiceControllo == null || codiceControllo.isEmpty()) {
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message("Necessario indicare un codiceControllo")).build();
				throw new WebApplicationException(resp);
			}
			
			rowPerPage = rowPerPage == null || rowPerPage <= 0 ? 1 : rowPerPage;
			pageNumber = pageNumber == null || pageNumber <= 0 ? 1 : pageNumber;
			
			orderby = orderby == null || orderby.isEmpty() ? "ASC" : orderby;
			if(!orderby.equalsIgnoreCase("ASC") && !orderby.equalsIgnoreCase("DESC")) {
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message("Ordinamento deve essere ASC o DESC")).build();
				throw new WebApplicationException(resp);
			}
			
			String cfUtente = currentUser!=null ? currentUser.getCfUtente() : null;
			log.info("currentUser -> "+cfUtente);
			if(cfUtente == null || cfUtente.isEmpty()) {
				log.error("Errore. Codice fiscale utente loggato non valorizzato");
				throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
			}
			
			long offset = (pageNumber-1)*rowPerPage;
			
			List<SoggettoForElenco> responseList = indicatoriMapper.functionControlloDati(cfUtente, offset, rowPerPage, orderby, codiceControllo, toLike(filter));
			
			insertAudit("select", "fnc_controllo_dati", "codiceControllo", currentUser.getCfUtente(),
			null, req);
			
			// contatore di righe nell'header della risposta
			long rowsNumber = 0L;
			if(responseList!=null && responseList.size()>0) {
				rowsNumber = responseList.get(0).getTotalCount();
			}
			
			return Response.ok(responseList).header("Rows-Number", rowsNumber).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	@Override
	public Response getListaIndicatori(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			log.info("Inizio esecuzione getListaCodiciControlli");
			
			List<ControlloDati> responseList = controlloDatiMapper.selectAll();
			insertAudit("select", "controllo_dati", "getListaCodiciControlli()", null,
					null, req);
			
			return Response.ok(responseList).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

}
