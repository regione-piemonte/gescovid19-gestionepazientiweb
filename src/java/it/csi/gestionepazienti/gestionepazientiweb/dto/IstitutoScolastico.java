package it.csi.gestionepazienti.gestionepazientiweb.dto;

import java.util.Date;

public class IstitutoScolastico extends AbstractDto {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.id_istituto
	 * @mbg.generated
	 */
	private Integer idIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.ordine_istituto
	 * @mbg.generated
	 */
	private String ordineIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.codice_provincia
	 * @mbg.generated
	 */
	private String codiceProvincia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.provincia_istituto
	 * @mbg.generated
	 */
	private String provinciaIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.codice_comune
	 * @mbg.generated
	 */
	private String codiceComune;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.comune_istituto
	 * @mbg.generated
	 */
	private String comuneIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.codice_autonomia
	 * @mbg.generated
	 */
	private String codiceAutonomia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.denominazione_autonomia
	 * @mbg.generated
	 */
	private String denominazioneAutonomia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.indirizzo_autonomia
	 * @mbg.generated
	 */
	private String indirizzoAutonomia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.telefono_autonomia
	 * @mbg.generated
	 */
	private String telefonoAutonomia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.email_autonomia
	 * @mbg.generated
	 */
	private String emailAutonomia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.codice_istituto
	 * @mbg.generated
	 */
	private String codiceIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.denominazione_istituto
	 * @mbg.generated
	 */
	private String denominazioneIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.indirizzo_istituto
	 * @mbg.generated
	 */
	private String indirizzoIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.tel_istituto
	 * @mbg.generated
	 */
	private String telIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.email_istituto
	 * @mbg.generated
	 */
	private String emailIstituto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.validita_inizio
	 * @mbg.generated
	 */
	private Date validitaInizio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.validita_fine
	 * @mbg.generated
	 */
	private Date validitaFine;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.utente_operazione
	 * @mbg.generated
	 */
	private String utenteOperazione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.data_creazione
	 * @mbg.generated
	 */
	private Date dataCreazione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.data_modifca
	 * @mbg.generated
	 */
	private Date dataModifca;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column istituti_scolastici.data_cancellazione
	 * @mbg.generated
	 */
	private Date dataCancellazione;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.id_istituto
	 * @return  the value of istituti_scolastici.id_istituto
	 * @mbg.generated
	 */
	public Integer getIdIstituto() {
		return idIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.id_istituto
	 * @param idIstituto  the value for istituti_scolastici.id_istituto
	 * @mbg.generated
	 */
	public void setIdIstituto(Integer idIstituto) {
		this.idIstituto = idIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.ordine_istituto
	 * @return  the value of istituti_scolastici.ordine_istituto
	 * @mbg.generated
	 */
	public String getOrdineIstituto() {
		return ordineIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.ordine_istituto
	 * @param ordineIstituto  the value for istituti_scolastici.ordine_istituto
	 * @mbg.generated
	 */
	public void setOrdineIstituto(String ordineIstituto) {
		this.ordineIstituto = ordineIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.codice_provincia
	 * @return  the value of istituti_scolastici.codice_provincia
	 * @mbg.generated
	 */
	public String getCodiceProvincia() {
		return codiceProvincia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.codice_provincia
	 * @param codiceProvincia  the value for istituti_scolastici.codice_provincia
	 * @mbg.generated
	 */
	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.provincia_istituto
	 * @return  the value of istituti_scolastici.provincia_istituto
	 * @mbg.generated
	 */
	public String getProvinciaIstituto() {
		return provinciaIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.provincia_istituto
	 * @param provinciaIstituto  the value for istituti_scolastici.provincia_istituto
	 * @mbg.generated
	 */
	public void setProvinciaIstituto(String provinciaIstituto) {
		this.provinciaIstituto = provinciaIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.codice_comune
	 * @return  the value of istituti_scolastici.codice_comune
	 * @mbg.generated
	 */
	public String getCodiceComune() {
		return codiceComune;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.codice_comune
	 * @param codiceComune  the value for istituti_scolastici.codice_comune
	 * @mbg.generated
	 */
	public void setCodiceComune(String codiceComune) {
		this.codiceComune = codiceComune;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.comune_istituto
	 * @return  the value of istituti_scolastici.comune_istituto
	 * @mbg.generated
	 */
	public String getComuneIstituto() {
		return comuneIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.comune_istituto
	 * @param comuneIstituto  the value for istituti_scolastici.comune_istituto
	 * @mbg.generated
	 */
	public void setComuneIstituto(String comuneIstituto) {
		this.comuneIstituto = comuneIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.codice_autonomia
	 * @return  the value of istituti_scolastici.codice_autonomia
	 * @mbg.generated
	 */
	public String getCodiceAutonomia() {
		return codiceAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.codice_autonomia
	 * @param codiceAutonomia  the value for istituti_scolastici.codice_autonomia
	 * @mbg.generated
	 */
	public void setCodiceAutonomia(String codiceAutonomia) {
		this.codiceAutonomia = codiceAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.denominazione_autonomia
	 * @return  the value of istituti_scolastici.denominazione_autonomia
	 * @mbg.generated
	 */
	public String getDenominazioneAutonomia() {
		return denominazioneAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.denominazione_autonomia
	 * @param denominazioneAutonomia  the value for istituti_scolastici.denominazione_autonomia
	 * @mbg.generated
	 */
	public void setDenominazioneAutonomia(String denominazioneAutonomia) {
		this.denominazioneAutonomia = denominazioneAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.indirizzo_autonomia
	 * @return  the value of istituti_scolastici.indirizzo_autonomia
	 * @mbg.generated
	 */
	public String getIndirizzoAutonomia() {
		return indirizzoAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.indirizzo_autonomia
	 * @param indirizzoAutonomia  the value for istituti_scolastici.indirizzo_autonomia
	 * @mbg.generated
	 */
	public void setIndirizzoAutonomia(String indirizzoAutonomia) {
		this.indirizzoAutonomia = indirizzoAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.telefono_autonomia
	 * @return  the value of istituti_scolastici.telefono_autonomia
	 * @mbg.generated
	 */
	public String getTelefonoAutonomia() {
		return telefonoAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.telefono_autonomia
	 * @param telefonoAutonomia  the value for istituti_scolastici.telefono_autonomia
	 * @mbg.generated
	 */
	public void setTelefonoAutonomia(String telefonoAutonomia) {
		this.telefonoAutonomia = telefonoAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.email_autonomia
	 * @return  the value of istituti_scolastici.email_autonomia
	 * @mbg.generated
	 */
	public String getEmailAutonomia() {
		return emailAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.email_autonomia
	 * @param emailAutonomia  the value for istituti_scolastici.email_autonomia
	 * @mbg.generated
	 */
	public void setEmailAutonomia(String emailAutonomia) {
		this.emailAutonomia = emailAutonomia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.codice_istituto
	 * @return  the value of istituti_scolastici.codice_istituto
	 * @mbg.generated
	 */
	public String getCodiceIstituto() {
		return codiceIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.codice_istituto
	 * @param codiceIstituto  the value for istituti_scolastici.codice_istituto
	 * @mbg.generated
	 */
	public void setCodiceIstituto(String codiceIstituto) {
		this.codiceIstituto = codiceIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.denominazione_istituto
	 * @return  the value of istituti_scolastici.denominazione_istituto
	 * @mbg.generated
	 */
	public String getDenominazioneIstituto() {
		return denominazioneIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.denominazione_istituto
	 * @param denominazioneIstituto  the value for istituti_scolastici.denominazione_istituto
	 * @mbg.generated
	 */
	public void setDenominazioneIstituto(String denominazioneIstituto) {
		this.denominazioneIstituto = denominazioneIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.indirizzo_istituto
	 * @return  the value of istituti_scolastici.indirizzo_istituto
	 * @mbg.generated
	 */
	public String getIndirizzoIstituto() {
		return indirizzoIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.indirizzo_istituto
	 * @param indirizzoIstituto  the value for istituti_scolastici.indirizzo_istituto
	 * @mbg.generated
	 */
	public void setIndirizzoIstituto(String indirizzoIstituto) {
		this.indirizzoIstituto = indirizzoIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.tel_istituto
	 * @return  the value of istituti_scolastici.tel_istituto
	 * @mbg.generated
	 */
	public String getTelIstituto() {
		return telIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.tel_istituto
	 * @param telIstituto  the value for istituti_scolastici.tel_istituto
	 * @mbg.generated
	 */
	public void setTelIstituto(String telIstituto) {
		this.telIstituto = telIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.email_istituto
	 * @return  the value of istituti_scolastici.email_istituto
	 * @mbg.generated
	 */
	public String getEmailIstituto() {
		return emailIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.email_istituto
	 * @param emailIstituto  the value for istituti_scolastici.email_istituto
	 * @mbg.generated
	 */
	public void setEmailIstituto(String emailIstituto) {
		this.emailIstituto = emailIstituto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.validita_inizio
	 * @return  the value of istituti_scolastici.validita_inizio
	 * @mbg.generated
	 */
	public Date getValiditaInizio() {
		return validitaInizio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.validita_inizio
	 * @param validitaInizio  the value for istituti_scolastici.validita_inizio
	 * @mbg.generated
	 */
	public void setValiditaInizio(Date validitaInizio) {
		this.validitaInizio = validitaInizio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.validita_fine
	 * @return  the value of istituti_scolastici.validita_fine
	 * @mbg.generated
	 */
	public Date getValiditaFine() {
		return validitaFine;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.validita_fine
	 * @param validitaFine  the value for istituti_scolastici.validita_fine
	 * @mbg.generated
	 */
	public void setValiditaFine(Date validitaFine) {
		this.validitaFine = validitaFine;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.utente_operazione
	 * @return  the value of istituti_scolastici.utente_operazione
	 * @mbg.generated
	 */
	public String getUtenteOperazione() {
		return utenteOperazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.utente_operazione
	 * @param utenteOperazione  the value for istituti_scolastici.utente_operazione
	 * @mbg.generated
	 */
	public void setUtenteOperazione(String utenteOperazione) {
		this.utenteOperazione = utenteOperazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.data_creazione
	 * @return  the value of istituti_scolastici.data_creazione
	 * @mbg.generated
	 */
	public Date getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.data_creazione
	 * @param dataCreazione  the value for istituti_scolastici.data_creazione
	 * @mbg.generated
	 */
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.data_modifca
	 * @return  the value of istituti_scolastici.data_modifca
	 * @mbg.generated
	 */
	public Date getDataModifca() {
		return dataModifca;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.data_modifca
	 * @param dataModifca  the value for istituti_scolastici.data_modifca
	 * @mbg.generated
	 */
	public void setDataModifca(Date dataModifca) {
		this.dataModifca = dataModifca;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column istituti_scolastici.data_cancellazione
	 * @return  the value of istituti_scolastici.data_cancellazione
	 * @mbg.generated
	 */
	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column istituti_scolastici.data_cancellazione
	 * @param dataCancellazione  the value for istituti_scolastici.data_cancellazione
	 * @mbg.generated
	 */
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
}