package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.DecodeTipoEvento;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TipoSoggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;

public class SoggettoForTrasferimento extends Soggetto {

	private Asr asr;
	private Decorso decorso;
	private Area area;
	private Struttura struttura;
	private Ente ente;
	private DecodeTipoEvento tipoEvento;
	private Comuni comuneRicovero;
	private TipoSoggetto tipoSoggetto;
	
	private Long totalCount;


	public SoggettoForTrasferimento() {
		super();
	}

	public Asr getAsr() {
		return asr;
	}

	public void setAsr(Asr asr) {
		this.asr = asr;
	}

	public Decorso getDecorso() {
		return decorso;
	}

	public void setDecorso(Decorso decorso) {
		this.decorso = decorso;
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

	public DecodeTipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(DecodeTipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Comuni getComuneRicovero() {
		return comuneRicovero;
	}

	public void setComuneRicovero(Comuni comuneRicovero) {
		this.comuneRicovero = comuneRicovero;
	}

	public TipoSoggetto getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(TipoSoggetto tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
