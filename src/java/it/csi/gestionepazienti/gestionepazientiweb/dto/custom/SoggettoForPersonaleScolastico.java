package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.util.Date;
import java.util.List;

import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TipoSoggetto;

public class SoggettoForPersonaleScolastico extends SoggettoForElenco{
	private static final long serialVersionUID = 4905520445557471244L;
	
	private String esitoSierologico;
	private Date dataTestSierologico;
	private TipoSoggetto tipoSoggetto;
	private Long idAura;
	private Medico medico;
	private Long idSoggettoFk;
	private List<IstitutoScolastico> elencoIstitutiScolastico;
	
	public Date getDataTestSierologico() {
		return dataTestSierologico;
	}
	public void setDataTestSierologico(Date dataTestSierologico) {
		this.dataTestSierologico = dataTestSierologico;
	}
	public String getEsitoSierologico() {
		return esitoSierologico;
	}
	public void setEsitoSierologico(String esitoSierologico) {
		this.esitoSierologico = esitoSierologico;
	}
	public TipoSoggetto getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(TipoSoggetto tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public Long getIdAura() {
		return idAura;
	}
	public void setIdAura(Long idAura) {
		this.idAura = idAura;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public Long getIdSoggettoFk() {
		return idSoggettoFk;
	}
	public void setIdSoggettoFk(Long idSoggettoFk) {
		this.idSoggettoFk = idSoggettoFk;
	}
	public List<IstitutoScolastico> getElencoIstitutiScolastico() {
		return elencoIstitutiScolastico;
	}
	public void setElencoIstitutiScolastico(List<IstitutoScolastico> elencoIstitutiScolastico) {
		this.elencoIstitutiScolastico = elencoIstitutiScolastico;
	}
	
}
