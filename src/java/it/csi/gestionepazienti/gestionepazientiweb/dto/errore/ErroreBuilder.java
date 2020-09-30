package it.csi.gestionepazienti.gestionepazientiweb.dto.errore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.Messaggio.MessaggioTipo;

/**
 * Build dell'oggetto {@link Errore}
 * 
 */
public class ErroreBuilder {
	private Errore errore;
	
	public static ErroreBuilder from(WebMessages aas, Object... args) {
		return new ErroreBuilder()
				.status(aas.getStatusCode())
				.code(aas.name())
				.title(aas.getMessage(args));
	}
	
	
	public static ErroreBuilder from(Status s) { 
		return new ErroreBuilder() 
				.status(s.getStatusCode()) 
				.code(s.name()); 
	} 

	public static ErroreBuilder from(StatusType s) {
		return new ErroreBuilder()
				.status(s.getStatusCode())
				.code(s.getReasonPhrase());
	}
	
	public static ErroreBuilder from(int status, String code) {
		return new ErroreBuilder()
				.status(status)
				.code(code);
	}
	
	public static ErroreBuilder from(MessaggiException e) {
		 ErroreBuilder eb = ErroreBuilder.from(WebMessages.ERRORE_DB, e.getMessage());
		 for(Messaggio m : e.getMessaggi()) {
			 eb.dettaglio(m.getCodice(), m.getParametri());
		 }
		 return eb;
	}
	
	
	
	private ErroreBuilder() {
		this.errore = new Errore();
	}
	
	public ErroreBuilder status(int status) {
		errore.setStatus(status);
		return this;
	}
	
	public ErroreBuilder code(String code) {
		errore.setCode(code);
		return this;
	}
	
	public ErroreBuilder title(String title) {
		errore.setTitle(title);
		return this;
	}
	
	public ErroreBuilder dettaglio(String chiave, String valore) {
		Dettaglio dettaglio = new Dettaglio();
		dettaglio.setChiave(chiave);
		dettaglio.setValore(valore);
		return dettaglio(dettaglio);
	}
	
	

	private ErroreBuilder dettaglio(Dettaglio dettaglio) {
		if(errore.getDetail()==null) {
			errore.setDetail(new ArrayList<>());
		}
		errore.getDetail().add(dettaglio);
		return this;
	}
	
	public <T> ErroreBuilder dettagli(List<T> list, Function<T, Dettaglio> mapper) {
		if(list!=null) {
			list.stream().map(mapper).forEach(d -> dettaglio(d));
		}
		return this;
	}
	
	public ErroreBuilder messaggio(String codice, String parametri, MessaggioTipo tipo) {
		Messaggio dettaglio = new Messaggio();
		dettaglio.setCodice(codice);
		dettaglio.setParametri(parametri);
		dettaglio.setTipo(tipo);
		return messaggio(dettaglio);
	}
	
	

	private ErroreBuilder messaggio(Messaggio messaggio) {
		if(errore.getMessages()==null) {
			errore.setMessages(new ArrayList<>());
		}
		errore.getMessages().add(messaggio);
		return this;
	}
	
	public <T> ErroreBuilder messaggi(List<T> list, Function<T, Dettaglio> mapper) {
		if(list!=null) {
			list.stream().map(mapper).forEach(this::dettaglio);
		}
		return this;
	}
	
	public ErroreBuilder messaggi(List<Messaggio> messaggi) {
		if(messaggi == null) {
			messaggi = new ArrayList<>();
		}
		errore.setMessages(messaggi);
		return this;
	}
	
	
	public ErroreBuilder link(String link) {
		if(errore.getLinks()==null) {
			errore.setLinks(new ArrayList<>());
		}
		errore.getLinks().add(link);
		return this;
	}
	
	public ErroreBuilder throwable(Throwable t) {
		if(t==null) {
			return this;
		}
		this.dettaglio("throwable", t.getMessage());
		addCause(t, 1);
		return this;
	}
	
	private void addCause(Throwable t, int i) {
		if(t.getCause()!=null) {
			this.dettaglio("cause"+i, t.getCause().getMessage());
			i++;
			addCause(t.getCause(), i);
		}
	}
	
	public Errore build() {
		return this.errore;
	}
	
	public ErroreRESTException exception() {
		return new ErroreRESTException(this.errore);
	}
	
	public ErroreRESTException exception(String message) {
		return new ErroreRESTException(this.errore, message);
	}

	
	
	
}
