package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.util.Date;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;

public class TamponeForReport extends AbstractDto{
	private static final long serialVersionUID = -7789567918506752348L;
	
	private Date dataInserimentoRichiesta;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private String tamponeAutorizzato;
	private String esitoTampone;
	private Date dataEsito;
	private String laboratorio;
	private String idTampone;
	private String idSoggetto;
	private String criterioEpidemiologicoCovid19;
	private String medicoRichiedente;
	private String contattoRichiedente;
	private String codiceFiscale;
	private String indirizzoDomicilio;
	private String comuneDomicilio;
	private String comuneResidenza; 
	private String descTipoSoggetto;
	private Long totalCount;

	public Date getDataInserimentoRichiesta() {
		return dataInserimentoRichiesta;
	}
	public void setDataInserimentoRichiesta(Date dataInserimentoRichiesta) {
		this.dataInserimentoRichiesta = dataInserimentoRichiesta;
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
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getTamponeAutorizzato() {
		return tamponeAutorizzato;
	}
	public void setTamponeAutorizzato(String tamponeAutorizzato) {
		this.tamponeAutorizzato = tamponeAutorizzato;
	}
	public String getEsitoTampone() {
		return esitoTampone;
	}
	public void setEsitoTampone(String esitoTampone) {
		this.esitoTampone = esitoTampone;
	}
	public Date getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}
	public TamponeForReport() {
	}
	

	
	public String getCriterioEpidemiologicoCovid19() {
		return criterioEpidemiologicoCovid19;
	}
	public void setCriterioEpidemiologicoCovid19(String criterioEpidemiologicoCovid19) {
		this.criterioEpidemiologicoCovid19 = criterioEpidemiologicoCovid19;
	}
	public String getIdTampone() {
		return idTampone;
	}
	public void setIdTampone(String idTampone) {
		this.idTampone = idTampone;
	}
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getMedicoRichiedente() {
		return medicoRichiedente;
	}
	public void setMedicoRichiedente(String medicoRichiedente) {
		this.medicoRichiedente = medicoRichiedente;
	}
	public String getContattoRichiedente() {
		return contattoRichiedente;
	}
	public void setContattoRichiedente(String contattoRichiedente) {
		this.contattoRichiedente = contattoRichiedente;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}
	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
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
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
}
