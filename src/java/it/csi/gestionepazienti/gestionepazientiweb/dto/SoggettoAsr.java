package it.csi.gestionepazienti.gestionepazientiweb.dto;

public class SoggettoAsr extends AbstractDto {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column r_soggetto_asr.id_soggetto_asr
     *
     * @mbg.generated
     */
    private Integer idSoggettoAsr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column r_soggetto_asr.id_soggetto
     *
     * @mbg.generated
     */
    private Long idSoggetto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column r_soggetto_asr.id_asr
     *
     * @mbg.generated
     */
    private Long idAsr;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column r_soggetto_asr.id_soggetto_asr
     *
     * @return the value of r_soggetto_asr.id_soggetto_asr
     *
     * @mbg.generated
     */
    public Integer getIdSoggettoAsr() {
        return idSoggettoAsr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column r_soggetto_asr.id_soggetto_asr
     *
     * @param idSoggettoAsr the value for r_soggetto_asr.id_soggetto_asr
     *
     * @mbg.generated
     */
    public void setIdSoggettoAsr(Integer idSoggettoAsr) {
        this.idSoggettoAsr = idSoggettoAsr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column r_soggetto_asr.id_soggetto
     *
     * @return the value of r_soggetto_asr.id_soggetto
     *
     * @mbg.generated
     */
    public Long getIdSoggetto() {
        return idSoggetto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column r_soggetto_asr.id_soggetto
     *
     * @param idSoggetto the value for r_soggetto_asr.id_soggetto
     *
     * @mbg.generated
     */
    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column r_soggetto_asr.id_asr
     *
     * @return the value of r_soggetto_asr.id_asr
     *
     * @mbg.generated
     */
    public Long getIdAsr() {
        return idAsr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column r_soggetto_asr.id_asr
     *
     * @param idAsr the value for r_soggetto_asr.id_asr
     *
     * @mbg.generated
     */
    public void setIdAsr(Long idAsr) {
        this.idAsr = idAsr;
    }
}