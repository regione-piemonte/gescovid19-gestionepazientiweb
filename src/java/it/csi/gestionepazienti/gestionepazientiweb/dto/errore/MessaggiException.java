package it.csi.gestionepazienti.gestionepazientiweb.dto.errore;

import java.util.ArrayList;
import java.util.List;

public class MessaggiException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260205000944373832L;
	private List<Messaggio> messaggi;

	
	public MessaggiException(String message, List<Messaggio> messaggi) {
		super(message);
		this.messaggi = messaggi;
	}


	public List<Messaggio> getMessaggi() {
		if(this.messaggi==null) {
			this.messaggi = new ArrayList<>();
		}
		return messaggi;
	}


	public void setMessaggi(List<Messaggio> messaggi) {
		this.messaggi = messaggi;
	}
	
	
	
	

}
