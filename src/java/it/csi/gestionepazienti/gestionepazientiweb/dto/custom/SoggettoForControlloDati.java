package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.controllodati.ControlloDati;

public class SoggettoForControlloDati extends Soggetto {

	private static final long serialVersionUID = 490552044510574821L;

	private ControlloDati controlloDati;

	public ControlloDati getControlloDati() {
		return controlloDati;
	}

	public void setControlloDati(ControlloDati controlloDati) {
		this.controlloDati = controlloDati;
	}

}
