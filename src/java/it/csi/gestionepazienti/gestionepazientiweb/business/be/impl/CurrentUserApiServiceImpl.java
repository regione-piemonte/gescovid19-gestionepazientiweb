package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.CurrentUserApi;
import it.csi.gestionepazienti.gestionepazientiweb.business.service.BackendService;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;

//public class CurrentUserApiServiceImpl extends SpringSupportedResource implements CurrentUserApi {
public class CurrentUserApiServiceImpl extends AuditableApiServiceImpl implements CurrentUserApi {
	public Response getCurrentUser(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		// do some magic!
		UserLogged currentUser = getBeService().getCurrentUser(req);
		insertAudit("Login", "utenti", "loaduser", currentUser.getCfUtente(), currentUser, req);
		
		return Response.ok(currentUser).build();
	}

	@Autowired
	public BackendService beService;

	public BackendService getBeService() {
//	if (beService==null) {
//  		beService = (BackendService)SpringApplicationContextHelper.getBean("backendService");
//  	}
		return beService;
	}

	public void setBeService(BackendService beService) {
		this.beService = beService;
	}


}
