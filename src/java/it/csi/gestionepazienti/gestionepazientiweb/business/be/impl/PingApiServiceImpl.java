package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.PingApi;
import it.csi.gestionepazienti.gestionepazientiweb.business.service.BackendService;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringSupportedResource;

public class PingApiServiceImpl extends SpringSupportedResource implements PingApi {
	public Response ping(SecurityContext securityContext, HttpHeaders httpHeaders) {
		// do some magic!

		return Response.ok(getBeService().getMessage()).build();
	}

	@Autowired
	public BackendService backendServiceImpl;

	public BackendService getBeService() {
//    	  if (backendServiceImpl == null) {
//    		  backendServiceImpl = (BackendService)SpringApplicationContextHelper.getBean("backendServiceImpl");
//    	  }
		return backendServiceImpl;
	}

//      public void setBeService(BackendService beService) {
//      	this.beService = beService;
//      }
}
