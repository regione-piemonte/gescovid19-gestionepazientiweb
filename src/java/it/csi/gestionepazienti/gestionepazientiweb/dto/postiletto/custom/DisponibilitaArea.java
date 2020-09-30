package it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Disponibilita;

public class DisponibilitaArea extends AbstractDto{

	private Area area;
	private Disponibilita disponibilita;
	
	public Disponibilita getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(Disponibilita disponibilita) {
		this.disponibilita = disponibilita;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}
