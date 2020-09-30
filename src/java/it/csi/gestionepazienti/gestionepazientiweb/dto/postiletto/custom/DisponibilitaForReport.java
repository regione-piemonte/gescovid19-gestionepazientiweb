package it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Disponibilita;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;

public class DisponibilitaForReport extends Disponibilita {

	
	private Ente ente;
	private Struttura struttura;
	private Area area;
	
	public Ente getEnte() {
		return ente;
	}
	public void setEnte(Ente ente) {
		this.ente = ente;
	}
	public Struttura getStruttura() {
		return struttura;
	}
	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
}
