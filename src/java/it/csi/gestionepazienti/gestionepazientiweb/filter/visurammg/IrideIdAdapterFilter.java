package it.csi.gestionepazienti.gestionepazientiweb.filter.visurammg;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.profilo.Profilo;
import it.csi.gestionepazienti.gestionepazientiweb.filter.AbstractIrideIdAdapterFilter;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseUtentiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.util.Constants;
import it.csi.gestionepazienti.gestionepazientiweb.util.GenericUtils;
import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;

/**
 * Inserisce in sessione:
 * <ul> 
 *  <li>l'identit&agrave; digitale relativa all'utente autenticato.
 *  <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter extends AbstractIrideIdAdapterFilter implements Filter {

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
	public static final String AUTH_MATRICOLA = "Shib-Identita-Matricola";
	public static final String AUTH_COMMUNITY = "Shib-community";
	
	
	public static final String APPLICATION_VISURA_PAZIENTI_MMG = "visura mmg";
	
	public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";
	private static final String ACCESS_DENIED_URL = "accessdenied.url";

	/**  */

	
	

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;
		if (!mustCheckPage(hreq.getRequestURI())) {
			fchn.doFilter(req, resp);
			return;
		}
		if (hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR) == null) {
			String marker = getToken(hreq);
			if (marker != null) {
				try {
					Identita identita = new Identita(normalizeToken(marker));
					String community = (String) hreq.getHeader(AUTH_COMMUNITY);
					
					LOG.info("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
					LOG.info("[IrideIdAdapterFilter::doFilter] cf:" + identita.getCodFiscale());
					LOG.info("[IrideIdAdapterFilter::doFilter] community:" + community);
					String userIdentifier = identita.getCodFiscale();
					UserLogged  user = loadUser(userIdentifier, identita.getNome(), identita.getCognome(),req,APPLICATION_VISURA_PAZIENTI_MMG); 
					
					user.setRunnningApplication(APPLICATION_VISURA_PAZIENTI_MMG);
					// verifica per l'applicazione visura
					user.setEnabled(
							GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_MMG)||
							GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_GESTORE_PAZIENTI)||
							GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_SUPERUSER_PAZIENTI)||
							GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_USCA) || // MED USCA 
							GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_EMERGENZA) || // MEDICO EMERGENZA 
							GenericUtils.currentUserHasProfilo(user, GenericUtils.ID_PROFILO_MEDICO_CONT_ASS)    // MEDICO CONT ASS
							)
							;
					user.setNome(identita.getNome());
					user.setCognome(identita.getCognome());
					//L'utente deve essere abilitato 
					if (!user.getEnabled()) {
						LOG.info("[IrideIdAdapterFilter::doFilter] mmgvisura user " + userIdentifier+" is NOT enabled");
						hresp.sendRedirect(accessdeniedUrl);
					}
					else {
						LOG.info("[IrideIdAdapterFilter::doFilter] mmgvisura user " + userIdentifier+" is enabled");
						hreq.getSession().setAttribute(IRIDE_ID_SESSIONATTR, identita);
						hreq.getSession().setAttribute(USERINFO_SESSIONATTR, user);
					}

				
				} catch (MalformedIdTokenException e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				}
			} else {
				// il marcatore deve sempre essere presente altrimenti e' una 
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(hreq.getRequestURI())) {
					LOG.error(
							"[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza ["
									+hreq.getRequestURI()+"]");
					hresp.sendRedirect(accessdeniedUrl);
					//throw new ServletException("Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
				}
			}
		}

		
		
		fchn.doFilter(req, resp);

	}

	private boolean mustCheckPage(String requestURI) {
		return requestURI==null || !requestURI.equals(accessdeniedUrl);
	}

	public void destroy() {
		// NOP
	}

	private static final String DEVMODE_INIT_PARAM = "devmode";
	private static final String LOGOUT_URL_INIT_PARAM = "logout.url";
	
	
	private boolean devmode = false;
	private String logoutUrl = "/";
	private String accessdeniedUrl = "/";

	public void init(FilterConfig fc) throws ServletException {
		String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
		if ("true".equals(sDevmode)) {
			devmode = true;
		} else {
			devmode = false;
		}
		
		logoutUrl = fc.getInitParameter(LOGOUT_URL_INIT_PARAM);
		accessdeniedUrl = fc.getInitParameter(ACCESS_DENIED_URL);
	}

	public String getToken(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getHeader(AUTH_ID_MARKER);
		if (marker == null && devmode) {
			return getTokenDevMode(httpreq);
		} else {
			try {
				// gestione dell'encoding
				String decodedMarker = new String(marker.getBytes("ISO-8859-1"), "UTF-8");
				return decodedMarker;
			} catch (java.io.UnsupportedEncodingException e) {
				// se la decodifica non funziona comunque sempre meglio restituire 
				// il marker originale non decodificato
				return marker;
			}
		}
	}

	private String getTokenDevMode(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getParameter(AUTH_ID_MARKER);
		return marker;
	}
	
	private String normalizeToken(String token) {
		return token;
	}
	
}
