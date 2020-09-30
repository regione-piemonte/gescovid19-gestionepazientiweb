package it.csi.gestionepazienti.gestionepazientiweb.business.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.csi.gestionepazienti.gestionepazientiweb.business.service.BackendService;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.filter.gestionepazienti.IrideIdAdapterFilter;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringSupportedResource;

@Component
@Qualifier("provaprovaprova")
public class BackendServiceImpl extends SpringSupportedResource implements BackendService {

	public String getMessage() {
		return "ping!!!!!";
	}

	@Value("${logout.url}")
	private String logoutUrl;

	public UserLogged getCurrentUser(HttpServletRequest req) {
		return (UserLogged) req.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);

	}

	public String localLogout(HttpServletRequest req) {
		// User res = getCurrentUser(req);
		req.getSession().invalidate();
		System.out.println("\n\n\n\nSessione invalidata !!! Redirect to "+ logoutUrl);
		return logoutUrl;
	}
}
