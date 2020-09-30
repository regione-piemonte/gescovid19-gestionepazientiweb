package it.csi.gestionepazienti.gestionepazientiweb.dto.errore;

import java.io.Serializable;

public class Messaggio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4704082611343104570L;
	
	
	private String codice;
	private MessaggioTipo tipo;
	private String parametri;
	
	
	public Messaggio() {
		// empty constructor
	}
	
	public Messaggio(String codice, MessaggioTipo tipo, String parametri) {
		super();
		this.codice = codice;
		this.tipo = tipo;
		this.parametri = parametri;
	}

	public static enum MessaggioTipo {
		ERR, WARN, INFO
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public MessaggioTipo getTipo() {
		return tipo;
	}

	public void setTipo(MessaggioTipo tipo) {
		this.tipo = tipo;
	}

	public String getParametri() {
		return parametri;
	}

	public void setParametri(String parametri) {
		this.parametri = parametri;
	}

	@Override
	public String toString() {
		return "Messaggio [codice=" + codice + ", tipo=" + tipo + ", parametri=" + parametri + "]";
	}
	
	
	
	
	

}
