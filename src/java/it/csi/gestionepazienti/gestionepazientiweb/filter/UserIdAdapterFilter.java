package it.csi.gestionepazienti.gestionepazientiweb.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.csi.gestionepazienti.gestionepazientiweb.dto.UtenteContatto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.Errore;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.ErroreBuilder;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.WebMessages;
import it.csi.gestionepazienti.gestionepazientiweb.util.GenericUtils;

/**
 * Inserisce in sessione:
 * <ul> 
 *  <li>l'oggetto <code>currentUser</code> {@link UserLogged} relativo all'utente autenticato.
 * </ul>
 * 
 *
 * @author CSIPiemonte
 */
public class UserIdAdapterFilter extends AbstractIrideIdAdapterFilter implements Filter {

	public static final String SHIB_IDENTITA_CODICE_FISCALE = "Shib-Identita-CodiceFiscale";
	public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";
	
	private boolean devmode = false;
	private String runningApplication = "gestione pazienti";
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;

		
		if(devmode && hreq.getRequestURI().equals(getContextRoot()+"/api/swagger.json")) { 
			fchn.doFilter(req, resp);
			return;
		}
		
		if(hreq.getRequestURI().startsWith(getContextRoot()+"/referti/")) {
			//https://rupcovid.sdp.csi.it/gestionepazienti-api/gestionepazientiapiwebsrv/referti/upload.html
			fchn.doFilter(req, resp);
			return;
		}
		
		// se l'user è già in sessione proseguo con la catena di filtri.
		if (hreq.getSession(false) != null 
				&& hreq.getSession(false).getAttribute(USERINFO_SESSIONATTR) != null) {
			fchn.doFilter(req, resp);
			return;
		}

		String codiceFiscale = hreq.getHeader(SHIB_IDENTITA_CODICE_FISCALE);
		if(devmode && StringUtils.isBlank(codiceFiscale)) {
			codiceFiscale = hreq.getParameter(SHIB_IDENTITA_CODICE_FISCALE);
		}
		
		if(StringUtils.isBlank(codiceFiscale)) {
			LOG.error("[UserIdAdapterFilter::doFilter] Tentativo di accesso senza Shib-Identita-CodiceFiscale");
			writeErrore(hresp, ErroreBuilder.from(WebMessages.UTENTE_NON_AUTORIZZATO).dettaglio("E1", "Tentativo di accesso senza Shib-Identita-CodiceFiscale").build());
			return;
		}
		UserLogged  user = loadUser(codiceFiscale, req, runningApplication); 
		
		user.setRunnningApplication(runningApplication);
		// verifica per l'applicazione  covid
		user.setEnabled(
				GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_GESTORE_PAZIENTI)
				||
				GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_SUPERUSER_PAZIENTI)
				||
				GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_USCA) 
				|| // MED USCA 
				GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_EMERGENZA) 
				|| // MEDICO EMERGENZA 
				GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_CONT_ASS) 
				||  // USCA 
				GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_MMG)  // USCA  ID_PROFILO_MEDICO_MMG
				
				);
		UtenteContatto contatto = user.getContatto();
		if(contatto!=null) {
			user.setNome(contatto.getNome());
			user.setCognome(contatto.getCognome());
		}
		
		
		//L'utente deve essere abilitato a gestionepazienti
		if (!user.getEnabled()) {
			
			if(GenericUtils.currentUserHasProfilo(user,GenericUtils.ID_PROFILO_LAB_IMPORT)){
				// Utente con profilo lab Import
				if(hreq.getRequestURI().startsWith(getContextRoot()+"/api/referti") 
						|| hreq.getRequestURI().startsWith(getContextRoot()+"/api/currentUser")) {
					LOG.info("[UserIdAdapterFilter::doFilter] gestionepazientiapi user " + codiceFiscale+" is enabled for ID_PROFILO_LAB_IMPORT");
					//Creao la sessione e metto lo user in sessione.
					hreq.getSession().setAttribute(USERINFO_SESSIONATTR, user);
					fchn.doFilter(req, resp);
					return;
				}
			}
			
			LOG.info("[UserIdAdapterFilter::doFilter] gestionepazientiapi user " + codiceFiscale+" is not enabled");
			writeErrore(hresp, ErroreBuilder.from(WebMessages.UTENTE_NON_AUTORIZZATO).dettaglio("E2", "gestionepazientiapi user " + codiceFiscale+" is not enabled").build());
			return;
		}
		
		LOG.info("[UserIdAdapterFilter::doFilter] gestionepazientiapi user " + codiceFiscale+" is enabled");

		//Creao la sessione e metto lo user in sessione.
		hreq.getSession().setAttribute(USERINFO_SESSIONATTR, user);
		fchn.doFilter(req, resp);

	}


	@Override
	public void destroy() {
		// NOP
	}


	@Override
	public void init(FilterConfig fc) throws ServletException {
		// devmode
		String sDevmode = fc.getInitParameter("devmode");
		devmode = "true".equalsIgnoreCase(sDevmode);
		
		// runningApplication
		runningApplication = fc.getInitParameter("runningApplication");
		if(StringUtils.isBlank(runningApplication)) {
			runningApplication = getDefaultRunningApplication(); //default solo se NON configurato
		}
	}


	protected String getDefaultRunningApplication() {
		return "gestione pazienti";
	}
	
	protected String getContextRoot() {
		return "/gestionepazientiapiwebsrv";
	}

	
	private void writeErrore(HttpServletResponse hresp, Errore errore) throws IOException {
		hresp.setStatus(errore.getStatus());
		hresp.setContentType(MediaType.APPLICATION_JSON);
		PrintWriter writer = hresp.getWriter();
		ObjectMapper om = new ObjectMapper();
		ObjectWriter objWriter = om.writerFor(Errore.class);
		objWriter.writeValue(writer, errore);
		hresp.flushBuffer();
	}


	
}
