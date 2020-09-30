package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.DecodeAutorizzazioneTampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;

@JsonPropertyOrder({"dataInserimentoRichiesta", "cognome", "nome", 
	"dataNascita", "comuneResidenza", "comuneDomicilio","descTipoSoggetto", "indirizzoDomicilio", "tamponeAutorizzato", "esitoTampone", "dataEsito", "laboratorio"})
public class TamponeForReportPlain extends AbstractDto {

	private String idTampone;
	private String dataRichiesta;
	private String idSoggetto;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String dataNascita;
	private String indirizzoDomicilio;
	private String comuneDomicilio;
	private String comuneResidenza; 
	private String descTipoSoggetto;
	private String tamponeAutorizzato;
	private String esitoTampone;
	private String dataEsito;
	private String criterioEpidemiologico;
	private String laboratorio;
	private String medicoRichiedente;
	private String contattoRichiedente;
	


	
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
	public String getCriterioEpidemiologico() {
		return criterioEpidemiologico;
	}
	public void setCriterioEpidemiologico(String criterioEpidemiologico) {
		this.criterioEpidemiologico = criterioEpidemiologico;
	}
	public TamponeForReportPlain() {

	}
	public TamponeForReportPlain(TamponeForReport tampone) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.codiceFiscale = tampone.getCodiceFiscale();
		this.cognome = tampone.getCognome();
		this.nome = tampone.getNome();
		if (tampone.getDataNascita()!=null)
			this.dataNascita = df.format(tampone.getDataNascita());
		if (tampone.getDataEsito()!=null)
			this.dataEsito = df.format(tampone.getDataEsito());
		if (tampone.getDataInserimentoRichiesta()!=null)
			this.dataRichiesta = df.format(tampone.getDataInserimentoRichiesta());
		else
			this.dataRichiesta="";
		this.laboratorio = tampone.getLaboratorio();
		this.esitoTampone = tampone.getEsitoTampone();
		this.tamponeAutorizzato = DecodeAutorizzazioneTampone.decodeAutorizzazioneTampone(
				tampone.getTamponeAutorizzato());
		this.criterioEpidemiologico = tampone.getCriterioEpidemiologicoCovid19();
		this.medicoRichiedente = tampone.getMedicoRichiedente();
		this.contattoRichiedente = tampone.getContattoRichiedente();
		this.idTampone = tampone.getIdTampone();
		this.idSoggetto = tampone.getIdSoggetto();
		this.indirizzoDomicilio = tampone.getIndirizzoDomicilio();
		this.comuneDomicilio = tampone.getComuneDomicilio();
		this.comuneResidenza = tampone.getComuneResidenza();
		this.descTipoSoggetto = tampone.getDescTipoSoggetto();

	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
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
	public String getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(String dataEsito) {
		this.dataEsito = dataEsito;
	}
	public String getTamponeAutorizzato() {
		return tamponeAutorizzato;
	}
	public void setTamponeAutorizzato(String tamponeAutorizzato) {
		this.tamponeAutorizzato = tamponeAutorizzato;
	}
	public String getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}
	public String getEsitoTampone() {
		return esitoTampone;
	}
	public void setEsitoTampone(String esitoTampone) {
		this.esitoTampone = esitoTampone;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

}
