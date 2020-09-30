package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.math.BigDecimal;

public class SoggettoFromElencoAura {

	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String dataNascitaStr;
	private String comuneNascita;
	private String provinciaNascita;
	private String sesso;
	private String dataDecessoStr;
	private String statoNascita;
	private BigDecimal profiloAnagrafico;
	
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getDataDecessoStr() {
		return dataDecessoStr;
	}
	public void setDataDecessoStr(String dataDecessoStr) {
		this.dataDecessoStr = dataDecessoStr;
	}
	public String getProvinciaNascita() {
		return provinciaNascita;
	}
	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}
	public String getStatoNascita() {
		return statoNascita;
	}
	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}
	
	public BigDecimal getProfiloAnagrafico() {
		return profiloAnagrafico;
	}
	public void setProfiloAnagrafico(BigDecimal profiloAnagrafico) {
		this.profiloAnagrafico = profiloAnagrafico;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascitaStr() {
		return dataNascitaStr;
	}
	public void setDataNascitaStr(String dataNascitaStr) {
		this.dataNascitaStr = dataNascitaStr;
	}

}
