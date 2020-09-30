package it.csi.gestionepazienti.gestionepazientiweb.business;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.gestionepazienti.gestionepazientiweb.business.service.BackendService;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringSupportedResource;

@Path("/be")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class GestionepazientiwclBE extends SpringSupportedResource {

	@GET
	@Path("/ping")
	public String ping() {
		return getImpl().getMessage();
	}

	@GET
	@Path("/currentUser")
	public UserLogged getCurrentUser(@Context HttpServletRequest req) {
		return getImpl().getCurrentUser(req);
	}

	@GET
	@Path("/localLogout")
	public Message localLogout(@Context HttpServletRequest req) {
		Message message = new Message(getImpl().localLogout(req));
		System.out.println("message " + message.getMessage());
		
		return message;
	}

	private BackendService getImpl() {
		// return
		// (GestionepazientiwebImpl)SpringApplicationContextHelper.getBean("gestionepazientiwebImpl");
		// return gestionepazientiwebImpl;
		return beService;
		// return (BEService)SpringApplicationContextHelper.getBean("gestionepazientiwebImpl");
	}

	@Autowired
	private BackendService beService = null;
	
}
