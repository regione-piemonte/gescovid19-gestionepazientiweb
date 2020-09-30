package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.sql.Date;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;

public class TamponeForElencoNonAbbinati extends AbstractDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codFisc;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private Long asr;
	private String descrStruttura;
	private Date effectiveTimeValue;
	private String valueCode;
	private String valueDisplayName;
	private Date dataCreazione;

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
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

	public Long getAsr() {
		return asr;
	}

	public void setAsr(Long asr) {
		this.asr = asr;
	}

	public String getDescrStruttura() {
		return descrStruttura;
	}

	public void setDescrStruttura(String descrStruttura) {
		this.descrStruttura = descrStruttura;
	}

	public Date getEffectiveTimeValue() {
		return effectiveTimeValue;
	}

	public void setEffectiveTimeValue(Date effectiveTimeValue) {
		this.effectiveTimeValue = effectiveTimeValue;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getValueDisplayName() {
		return valueDisplayName;
	}

	public void setValueDisplayName(String valueDisplayName) {
		this.valueDisplayName = valueDisplayName;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

}
