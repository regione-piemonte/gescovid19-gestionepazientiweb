package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import javax.servlet.http.HttpServletRequest;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.LogoutApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;

public class LocalLogoutApiServiceImpl extends AuditableApiServiceImpl implements LogoutApi {


	@Override
	public Message localLogout(HttpServletRequest req) {
		UserLogged currentUser = getBeService().getCurrentUser(req);

		insertAudit("logout", "utenti", "Logout su gestione pazienti", currentUser.getCfUtente(), null, req);
		return new Message(beService.localLogout(req));
	}

}
