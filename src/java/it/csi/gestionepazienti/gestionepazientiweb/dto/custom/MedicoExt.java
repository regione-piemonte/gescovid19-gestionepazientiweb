package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;


import java.util.Date;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;

public class MedicoExt extends Medico {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date dataInizioValidita;
	private Date  dataFineValidita;
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}



}
