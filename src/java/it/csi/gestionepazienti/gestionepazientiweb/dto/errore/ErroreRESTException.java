package it.csi.gestionepazienti.gestionepazientiweb.dto.errore;

import javax.ws.rs.core.MediaType;

public class ErroreRESTException extends RESTException {

	private static final long serialVersionUID = -6454021553480738359L;

	public ErroreRESTException(Errore errore, String message) {
		super(errore.getStatus(), MediaType.APPLICATION_JSON_TYPE, errore, message);
	}
	
	public ErroreRESTException(Errore errore) {
		super(errore.getStatus(), MediaType.APPLICATION_JSON_TYPE, errore, errore.toString());
	}
	
	
}
