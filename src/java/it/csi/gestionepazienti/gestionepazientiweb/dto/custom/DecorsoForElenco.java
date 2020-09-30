package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.DecodeTipoEvento;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.EsitoDimissioni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RepartoRicovero;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Sintomo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;

public class DecorsoForElenco extends Decorso {
	
	private Comuni comuneRicovero;
	private EsitoDimissioni esitoDimissioni;
	private RepartoRicovero repartoRicovero;
	private DecodeTipoEvento decodeTipoEvento;
	private Area area;
	private Struttura struttura;
	private Ente ente;
	private Sintomo sintomo;
	
	public Sintomo getSintomo() {
		return sintomo;
	}
	public void setSintomo(Sintomo sintomo) {
		this.sintomo = sintomo;
	}
	public DecodeTipoEvento getDecodeTipoEvento() {
		return decodeTipoEvento;
	}
	public void setDecodeTipoEvento(DecodeTipoEvento decodeTipoEvento) {
		this.decodeTipoEvento = decodeTipoEvento;
	}
	public Comuni getComuneRicovero() {
		return comuneRicovero;
	}
	public void setComuneRicovero(Comuni comuneRicovero) {
		this.comuneRicovero = comuneRicovero;
	}
	public EsitoDimissioni getEsitoDimissioni() {
		return esitoDimissioni;
	}
	public void setEsitoDimissioni(EsitoDimissioni esitoDimissioni) {
		this.esitoDimissioni = esitoDimissioni;
	}
	public RepartoRicovero getRepartoRicovero() {
		return repartoRicovero;
	}
	public void setRepartoRicovero(RepartoRicovero repartoRicovero) {
		this.repartoRicovero = repartoRicovero;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Struttura getStruttura() {
		return struttura;
	}
	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}
	public Ente getEnte() {
		return ente;
	}
	public void setEnte(Ente ente) {
		this.ente = ente;
	}
	

	
}
