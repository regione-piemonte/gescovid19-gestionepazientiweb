package it.csi.gestionepazienti.gestionepazientiweb.dto.errore;

import javax.ws.rs.core.MediaType;

public enum WebMessages {

	UTENTE_NON_AUTORIZZATO(401, "Utente non Autorizzato"), 
	CODICE_FISCALE_NON_VALIDO(421, "Codice Fiscale %s non valido"),
	ENTITA_NON_TROVATA(404, "Entita non trovata."),
	UTENTE_NON_LOGGATO(500, "Utente non loggato. Sessione scaduta."), 
	ERRORE_DB(500, "Errore DB. %s"),
	INTERNAL_SERVER_ERROR(500, "Si è verificato un problema tecnico"), 
	PARAMETRO_ERRATO(400, "Parametro errato o mancante"),
	
	;

	private int statusCode;
	private String descTemplate;

	WebMessages(int statusCode, String desc) {
		this.statusCode = statusCode;
		this.descTemplate = desc;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public RESTException getRestException(Object... args) {
		String desc = getMessage(args);
		return new RESTException(this.statusCode, MediaType.TEXT_PLAIN_TYPE, desc, desc);
	}

	public RESTException getRestException(MediaType mediaType, Object entity, String message) {
		return new RESTException(this.statusCode, mediaType, entity, message);
	}

	public String getMessage(Object... args) {
		return String.format(descTemplate, args);
	}
}
