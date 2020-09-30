package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Laboratorio;

public class LaboratorioCompleto extends Laboratorio {

	private Integer tamponiDaLavorare;

	public Integer getTamponiDaLavorare() {
		return tamponiDaLavorare;
	}

	public void setTamponiDaLavorare(Integer tamponiDaLavorare) {
		this.tamponiDaLavorare = tamponiDaLavorare;
	}


}
