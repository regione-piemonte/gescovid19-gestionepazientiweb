package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Sintomo;

public class DecorsoSintomo extends Decorso {
	
	private Sintomo sintomo;

	public Sintomo getSintomo() {
		return sintomo;
	}

	public void setSintomo(Sintomo sintomo) {
		this.sintomo = sintomo;
	}
	

	
}
