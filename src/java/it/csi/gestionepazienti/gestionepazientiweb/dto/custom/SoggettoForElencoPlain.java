package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
/*
 * L'ordine delle property qui sotto coincide con l'export excel
 */
public class SoggettoForElencoPlain extends AbstractDto {

	private String codiceFiscale="";
	private String nome="";
	private String cognome="";
	private String dataNascita="";
	private String aslResidenza="";
	private String aslDomicilio="";
	private String indirizzoDomicilio="";
	private String telefono="";
	private String asr="";
	private String comuneDomicilio="";
	private String comuneResidenza="";
	private int numeroTamponiRichiesti=0;
	private int numeroDecorsi=0;
	private String operatoreSanitario="";
	
	public SoggettoForElencoPlain(SoggettoForElenco sl) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (sl.getAsr()!=null)
			this.asr = sl.getAsr().getDescrizione();
		this.codiceFiscale = sl.getCodiceFiscale();
		this.cognome = sl.getCognome();
		if (sl.getComuneDomicilio()!=null)
			this.comuneDomicilio = sl.getComuneDomicilio().getNomeComune();
		if (sl.getComuneResidenza()!=null)
			this.comuneResidenza = sl.getComuneResidenza().getNomeComune();
		if (sl.getDataNascita()!=null)
			this.dataNascita = df.format(sl.getDataNascita());
		this.indirizzoDomicilio = sl.getIndirizzoDomicilio();
		this.nome = sl.getNome();
		this.numeroDecorsi = sl.getNumeroDecorsi();
		this.numeroTamponiRichiesti = sl.getNumeroCampioni();
		this.telefono = sl.getTelefonoRecapito();
		this.aslDomicilio = sl.getAslDomicilio();
		this.aslResidenza = sl.getAslResidenza();
		if (sl.getTipoSoggetto()!=null)
			this.operatoreSanitario = sl.getTipoSoggetto().getDescTipoSoggetto();
	}
	
	public String getAslDomicilio() {
		return aslDomicilio;
	}

	public void setAslDomicilio(String aslDomicilio) {
		this.aslDomicilio = aslDomicilio;
	}

	public String getAslResidenza() {
		return aslResidenza;
	}

	public void setAslResidenza(String aslResidenza) {
		this.aslResidenza = aslResidenza;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}
	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getAsr() {
		return asr;
	}
	public void setAsr(String asr) {
		this.asr = asr;
	}
	public String getComuneDomicilio() {
		return comuneDomicilio;
	}
	public void setComuneDomicilio(String comuneDomicilio) {
		this.comuneDomicilio = comuneDomicilio;
	}
	public String getComuneResidenza() {
		return comuneResidenza;
	}
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public int getNumeroTamponiRichiesti() {
		return numeroTamponiRichiesti;
	}
	public void setNumeroTamponiRichiesti(int numeroTamponiRichiesti) {
		this.numeroTamponiRichiesti = numeroTamponiRichiesti;
	}
	public int getNumeroDecorsi() {
		return numeroDecorsi;
	}
	public void setNumeroDecorsi(int numeroDecorsi) {
		this.numeroDecorsi = numeroDecorsi;
	}

	public String getOperatoreSanitario() {
		return operatoreSanitario;
	}

	public void setOperatoreSanitario(String operatoreSanitario) {
		this.operatoreSanitario = operatoreSanitario;
	}
	
		
}
