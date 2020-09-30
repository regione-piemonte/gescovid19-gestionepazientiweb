package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TipoSoggetto;

public class SoggettoForElenco extends Soggetto{
	private static final long serialVersionUID = 4905520445194671263L;
	
	private Long totalCount;
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	private Asr asr;
	private Comuni comuneDomicilio;
	private Comuni comuneResidenza;
	private int numeroCampioni;
	private int numeroDecorsi;
	private int ultimoIdTipoEvento;
	private TipoSoggetto tipoSoggetto;
	
	private IstitutoScolastico istitutiScolastici;
	
		
	public IstitutoScolastico getIstitutiScolastici() {
		return istitutiScolastici;
	}
	public void setIstitutiScolastici(IstitutoScolastico istitutiScolastici) {
		this.istitutiScolastici = istitutiScolastici;
	}
	public int getNumeroDecorsi() {
		return numeroDecorsi;
	}
	public void setNumeroDecorsi(int numeroDecorsi) {
		this.numeroDecorsi = numeroDecorsi;
	}
	public Asr getAsr() {
		return asr;
	}
	public void setAsr(Asr asr) {
		this.asr = asr;
	}
	public Comuni getComuneDomicilio() {
		return comuneDomicilio;
	}
	public void setComuneDomicilio(Comuni comuneDomicilio) {
		this.comuneDomicilio = comuneDomicilio;
	}
	public Comuni getComuneResidenza() {
		return comuneResidenza;
	}
	public void setComuneResidenza(Comuni comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public int getNumeroCampioni() {
		return numeroCampioni;
	}
	public void setNumeroCampioni(int numeroCampioni) {
		this.numeroCampioni = numeroCampioni;
	}
	public int getUltimoIdTipoEvento() {
		return ultimoIdTipoEvento;
	}
	public void setUltimoIdTipoEvento(int ultimoIdTipoEvento) {
		this.ultimoIdTipoEvento = ultimoIdTipoEvento;
	}
	public TipoSoggetto getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(TipoSoggetto tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	
	
	
}
