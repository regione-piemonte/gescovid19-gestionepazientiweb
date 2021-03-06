package it.csi.gestionepazienti.gestionepazientiweb.dto.personalescolastico;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
import java.math.BigDecimal;
import java.util.Date;

public class SoggettoPersonaleScolastico extends AbstractDto {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.id_soggetto
     *
     * @mbg.generated
     */
    private Long idSoggetto;

    
    private Long idSoggettoFk;
    
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.codice_fiscale
     *
     * @mbg.generated
     */
    private String codiceFiscale;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.id_asr
     *
     * @mbg.generated
     */
    private Long idAsr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.cognome
     *
     * @mbg.generated
     */
    private String cognome;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.nome
     *
     * @mbg.generated
     */
    private String nome;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.data_nascita_str
     *
     * @mbg.generated
     */
    private String dataNascitaStr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.comune_residenza_istat
     *
     * @mbg.generated
     */
    private String comuneResidenzaIstat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.comune_domicilio_istat
     *
     * @mbg.generated
     */
    private String comuneDomicilioIstat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.indirizzo_domicilio
     *
     * @mbg.generated
     */
    private String indirizzoDomicilio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.telefono_recapito
     *
     * @mbg.generated
     */
    private String telefonoRecapito;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.data_nascita
     *
     * @mbg.generated
     */
    private Date dataNascita;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.asl_residenza
     *
     * @mbg.generated
     */
    private String aslResidenza;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.asl_domicilio
     *
     * @mbg.generated
     */
    private String aslDomicilio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.lat
     *
     * @mbg.generated
     */
    private BigDecimal lat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.lng
     *
     * @mbg.generated
     */
    private BigDecimal lng;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.id_tipo_soggetto
     *
     * @mbg.generated
     */
    private Integer idTipoSoggetto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.utente_operazione
     *
     * @mbg.generated
     */
    private String utenteOperazione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.data_creazione
     *
     * @mbg.generated
     */
    private Date dataCreazione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.data_modifica
     *
     * @mbg.generated
     */
    private Date dataModifica;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.data_cancellazione
     *
     * @mbg.generated
     */
    private Date dataCancellazione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column soggetto_personale_scolastico.id_medico
     *
     * @mbg.generated
     */
    private Long idMedico;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.id_soggetto
     *
     * @return the value of soggetto_personale_scolastico.id_soggetto
     *
     * @mbg.generated
     */
    public Long getIdSoggetto() {
        return idSoggetto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.id_soggetto
     *
     * @param idSoggetto the value for soggetto_personale_scolastico.id_soggetto
     *
     * @mbg.generated
     */
    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.codice_fiscale
     *
     * @return the value of soggetto_personale_scolastico.codice_fiscale
     *
     * @mbg.generated
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.codice_fiscale
     *
     * @param codiceFiscale the value for soggetto_personale_scolastico.codice_fiscale
     *
     * @mbg.generated
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.id_asr
     *
     * @return the value of soggetto_personale_scolastico.id_asr
     *
     * @mbg.generated
     */
    public Long getIdAsr() {
        return idAsr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.id_asr
     *
     * @param idAsr the value for soggetto_personale_scolastico.id_asr
     *
     * @mbg.generated
     */
    public void setIdAsr(Long idAsr) {
        this.idAsr = idAsr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.cognome
     *
     * @return the value of soggetto_personale_scolastico.cognome
     *
     * @mbg.generated
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.cognome
     *
     * @param cognome the value for soggetto_personale_scolastico.cognome
     *
     * @mbg.generated
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.nome
     *
     * @return the value of soggetto_personale_scolastico.nome
     *
     * @mbg.generated
     */
    public String getNome() {
        return nome;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.nome
     *
     * @param nome the value for soggetto_personale_scolastico.nome
     *
     * @mbg.generated
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.data_nascita_str
     *
     * @return the value of soggetto_personale_scolastico.data_nascita_str
     *
     * @mbg.generated
     */
    public String getDataNascitaStr() {
        return dataNascitaStr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.data_nascita_str
     *
     * @param dataNascitaStr the value for soggetto_personale_scolastico.data_nascita_str
     *
     * @mbg.generated
     */
    public void setDataNascitaStr(String dataNascitaStr) {
        this.dataNascitaStr = dataNascitaStr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.comune_residenza_istat
     *
     * @return the value of soggetto_personale_scolastico.comune_residenza_istat
     *
     * @mbg.generated
     */
    public String getComuneResidenzaIstat() {
        return comuneResidenzaIstat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.comune_residenza_istat
     *
     * @param comuneResidenzaIstat the value for soggetto_personale_scolastico.comune_residenza_istat
     *
     * @mbg.generated
     */
    public void setComuneResidenzaIstat(String comuneResidenzaIstat) {
        this.comuneResidenzaIstat = comuneResidenzaIstat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.comune_domicilio_istat
     *
     * @return the value of soggetto_personale_scolastico.comune_domicilio_istat
     *
     * @mbg.generated
     */
    public String getComuneDomicilioIstat() {
        return comuneDomicilioIstat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.comune_domicilio_istat
     *
     * @param comuneDomicilioIstat the value for soggetto_personale_scolastico.comune_domicilio_istat
     *
     * @mbg.generated
     */
    public void setComuneDomicilioIstat(String comuneDomicilioIstat) {
        this.comuneDomicilioIstat = comuneDomicilioIstat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.indirizzo_domicilio
     *
     * @return the value of soggetto_personale_scolastico.indirizzo_domicilio
     *
     * @mbg.generated
     */
    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.indirizzo_domicilio
     *
     * @param indirizzoDomicilio the value for soggetto_personale_scolastico.indirizzo_domicilio
     *
     * @mbg.generated
     */
    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.telefono_recapito
     *
     * @return the value of soggetto_personale_scolastico.telefono_recapito
     *
     * @mbg.generated
     */
    public String getTelefonoRecapito() {
        return telefonoRecapito;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.telefono_recapito
     *
     * @param telefonoRecapito the value for soggetto_personale_scolastico.telefono_recapito
     *
     * @mbg.generated
     */
    public void setTelefonoRecapito(String telefonoRecapito) {
        this.telefonoRecapito = telefonoRecapito;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.data_nascita
     *
     * @return the value of soggetto_personale_scolastico.data_nascita
     *
     * @mbg.generated
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.data_nascita
     *
     * @param dataNascita the value for soggetto_personale_scolastico.data_nascita
     *
     * @mbg.generated
     */
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.asl_residenza
     *
     * @return the value of soggetto_personale_scolastico.asl_residenza
     *
     * @mbg.generated
     */
    public String getAslResidenza() {
        return aslResidenza;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.asl_residenza
     *
     * @param aslResidenza the value for soggetto_personale_scolastico.asl_residenza
     *
     * @mbg.generated
     */
    public void setAslResidenza(String aslResidenza) {
        this.aslResidenza = aslResidenza;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.asl_domicilio
     *
     * @return the value of soggetto_personale_scolastico.asl_domicilio
     *
     * @mbg.generated
     */
    public String getAslDomicilio() {
        return aslDomicilio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.asl_domicilio
     *
     * @param aslDomicilio the value for soggetto_personale_scolastico.asl_domicilio
     *
     * @mbg.generated
     */
    public void setAslDomicilio(String aslDomicilio) {
        this.aslDomicilio = aslDomicilio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.email
     *
     * @return the value of soggetto_personale_scolastico.email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.email
     *
     * @param email the value for soggetto_personale_scolastico.email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.lat
     *
     * @return the value of soggetto_personale_scolastico.lat
     *
     * @mbg.generated
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.lat
     *
     * @param lat the value for soggetto_personale_scolastico.lat
     *
     * @mbg.generated
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.lng
     *
     * @return the value of soggetto_personale_scolastico.lng
     *
     * @mbg.generated
     */
    public BigDecimal getLng() {
        return lng;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.lng
     *
     * @param lng the value for soggetto_personale_scolastico.lng
     *
     * @mbg.generated
     */
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.id_tipo_soggetto
     *
     * @return the value of soggetto_personale_scolastico.id_tipo_soggetto
     *
     * @mbg.generated
     */
    public Integer getIdTipoSoggetto() {
        return idTipoSoggetto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.id_tipo_soggetto
     *
     * @param idTipoSoggetto the value for soggetto_personale_scolastico.id_tipo_soggetto
     *
     * @mbg.generated
     */
    public void setIdTipoSoggetto(Integer idTipoSoggetto) {
        this.idTipoSoggetto = idTipoSoggetto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.utente_operazione
     *
     * @return the value of soggetto_personale_scolastico.utente_operazione
     *
     * @mbg.generated
     */
    public String getUtenteOperazione() {
        return utenteOperazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.utente_operazione
     *
     * @param utenteOperazione the value for soggetto_personale_scolastico.utente_operazione
     *
     * @mbg.generated
     */
    public void setUtenteOperazione(String utenteOperazione) {
        this.utenteOperazione = utenteOperazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.data_creazione
     *
     * @return the value of soggetto_personale_scolastico.data_creazione
     *
     * @mbg.generated
     */
    public Date getDataCreazione() {
        return dataCreazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.data_creazione
     *
     * @param dataCreazione the value for soggetto_personale_scolastico.data_creazione
     *
     * @mbg.generated
     */
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.data_modifica
     *
     * @return the value of soggetto_personale_scolastico.data_modifica
     *
     * @mbg.generated
     */
    public Date getDataModifica() {
        return dataModifica;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.data_modifica
     *
     * @param dataModifica the value for soggetto_personale_scolastico.data_modifica
     *
     * @mbg.generated
     */
    public void setDataModifica(Date dataModifica) {
        this.dataModifica = dataModifica;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.data_cancellazione
     *
     * @return the value of soggetto_personale_scolastico.data_cancellazione
     *
     * @mbg.generated
     */
    public Date getDataCancellazione() {
        return dataCancellazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.data_cancellazione
     *
     * @param dataCancellazione the value for soggetto_personale_scolastico.data_cancellazione
     *
     * @mbg.generated
     */
    public void setDataCancellazione(Date dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column soggetto_personale_scolastico.id_medico
     *
     * @return the value of soggetto_personale_scolastico.id_medico
     *
     * @mbg.generated
     */
    public Long getIdMedico() {
        return idMedico;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column soggetto_personale_scolastico.id_medico
     *
     * @param idMedico the value for soggetto_personale_scolastico.id_medico
     *
     * @mbg.generated
     */
    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

	public Long getIdSoggettoFk() {
		return idSoggettoFk;
	}

	public void setIdSoggettoFk(Long idSoggettoFk) {
		this.idSoggettoFk = idSoggettoFk;
	}
}