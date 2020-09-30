package it.csi.gestionepazienti.gestionepazientiweb.dto.referto;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
import java.util.Date;

public class RefertoElaborazione extends AbstractDto {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.elabdet_id
     *
     * @mbg.generated
     */
    private Long elabdetId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.elab_id
     *
     * @mbg.generated
     */
    private Long elabId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.intfrdet_id
     *
     * @mbg.generated
     */
    private Integer intfrdetId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.elabdet_scarto
     *
     * @mbg.generated
     */
    private Boolean elabdetScarto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.elabdet_scarto_motivo
     *
     * @mbg.generated
     */
    private String elabdetScartoMotivo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.query_eseguita
     *
     * @mbg.generated
     */
    private String queryEseguita;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.data_creazione
     *
     * @mbg.generated
     */
    private Date dataCreazione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.utente_operazione
     *
     * @mbg.generated
     */
    private String utenteOperazione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dmarefcovid_l_elaborazione_referto.scartomotivo_id
     *
     * @mbg.generated
     */
    private Integer scartomotivoId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.elabdet_id
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.elabdet_id
     *
     * @mbg.generated
     */
    public Long getElabdetId() {
        return elabdetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.elabdet_id
     *
     * @param elabdetId the value for dmarefcovid_l_elaborazione_referto.elabdet_id
     *
     * @mbg.generated
     */
    public void setElabdetId(Long elabdetId) {
        this.elabdetId = elabdetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.elab_id
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.elab_id
     *
     * @mbg.generated
     */
    public Long getElabId() {
        return elabId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.elab_id
     *
     * @param elabId the value for dmarefcovid_l_elaborazione_referto.elab_id
     *
     * @mbg.generated
     */
    public void setElabId(Long elabId) {
        this.elabId = elabId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.intfrdet_id
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.intfrdet_id
     *
     * @mbg.generated
     */
    public Integer getIntfrdetId() {
        return intfrdetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.intfrdet_id
     *
     * @param intfrdetId the value for dmarefcovid_l_elaborazione_referto.intfrdet_id
     *
     * @mbg.generated
     */
    public void setIntfrdetId(Integer intfrdetId) {
        this.intfrdetId = intfrdetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.elabdet_scarto
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.elabdet_scarto
     *
     * @mbg.generated
     */
    public Boolean getElabdetScarto() {
        return elabdetScarto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.elabdet_scarto
     *
     * @param elabdetScarto the value for dmarefcovid_l_elaborazione_referto.elabdet_scarto
     *
     * @mbg.generated
     */
    public void setElabdetScarto(Boolean elabdetScarto) {
        this.elabdetScarto = elabdetScarto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.elabdet_scarto_motivo
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.elabdet_scarto_motivo
     *
     * @mbg.generated
     */
    public String getElabdetScartoMotivo() {
        return elabdetScartoMotivo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.elabdet_scarto_motivo
     *
     * @param elabdetScartoMotivo the value for dmarefcovid_l_elaborazione_referto.elabdet_scarto_motivo
     *
     * @mbg.generated
     */
    public void setElabdetScartoMotivo(String elabdetScartoMotivo) {
        this.elabdetScartoMotivo = elabdetScartoMotivo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.query_eseguita
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.query_eseguita
     *
     * @mbg.generated
     */
    public String getQueryEseguita() {
        return queryEseguita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.query_eseguita
     *
     * @param queryEseguita the value for dmarefcovid_l_elaborazione_referto.query_eseguita
     *
     * @mbg.generated
     */
    public void setQueryEseguita(String queryEseguita) {
        this.queryEseguita = queryEseguita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.data_creazione
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.data_creazione
     *
     * @mbg.generated
     */
    public Date getDataCreazione() {
        return dataCreazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.data_creazione
     *
     * @param dataCreazione the value for dmarefcovid_l_elaborazione_referto.data_creazione
     *
     * @mbg.generated
     */
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.utente_operazione
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.utente_operazione
     *
     * @mbg.generated
     */
    public String getUtenteOperazione() {
        return utenteOperazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.utente_operazione
     *
     * @param utenteOperazione the value for dmarefcovid_l_elaborazione_referto.utente_operazione
     *
     * @mbg.generated
     */
    public void setUtenteOperazione(String utenteOperazione) {
        this.utenteOperazione = utenteOperazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dmarefcovid_l_elaborazione_referto.scartomotivo_id
     *
     * @return the value of dmarefcovid_l_elaborazione_referto.scartomotivo_id
     *
     * @mbg.generated
     */
    public Integer getScartomotivoId() {
        return scartomotivoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dmarefcovid_l_elaborazione_referto.scartomotivo_id
     *
     * @param scartomotivoId the value for dmarefcovid_l_elaborazione_referto.scartomotivo_id
     *
     * @mbg.generated
     */
    public void setScartomotivoId(Integer scartomotivoId) {
        this.scartomotivoId = scartomotivoId;
    }
}