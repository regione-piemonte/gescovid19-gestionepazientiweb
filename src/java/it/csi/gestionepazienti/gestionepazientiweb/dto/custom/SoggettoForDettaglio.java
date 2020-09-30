package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoContatto;

public class SoggettoForDettaglio extends SoggettoForElenco{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4645658485510354022L;
	private List<TamponeForElenco> elencoTampone;
	private List<DecorsoForElenco> elencoDecorso;
	private List<SoggettoContattoExt> elencoContattiDa;
	private List<SoggettoContattoExt> elencoContattiA;
	
	private List<IstitutoScolastico> elencoIstitutiScolastico;
	
	
	
	public List<IstitutoScolastico> getElencoIstitutiScolastico() {
		return elencoIstitutiScolastico;
	}

	public void setElencoIstitutiScolastico(List<IstitutoScolastico> elencoIstitutiScolastico) {
		this.elencoIstitutiScolastico = elencoIstitutiScolastico;
	}

	private String codiceApplicazione;
		
	
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	private Long idAura;
	private Medico medico;

	public SoggettoForDettaglio() {
	}
	
	public SoggettoForDettaglio(SoggettoForElenco soggettoForElenco,
			List<TamponeForElenco> elencoTampone,
			List<DecorsoForElenco> elencoDecorso, 
			Medico medico) {
		try {
			BeanUtils.copyProperties(this, soggettoForElenco);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.elencoDecorso = elencoDecorso;
		this.elencoTampone = elencoTampone;
		this.medico = medico;
	}

	public List<TamponeForElenco> getElencoTampone() {
		return elencoTampone;
	}

	public void setElencoTampone(List<TamponeForElenco> elencoTampone) {
		this.elencoTampone = elencoTampone;
	}

	public List<DecorsoForElenco> getElencoDecorso() {
		return elencoDecorso;
	}

	public void setElencoDecorso(List<DecorsoForElenco> elencoDecorso) {
		this.elencoDecorso = elencoDecorso;
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

	public List<SoggettoContattoExt> getElencoContattiDa() {
		return elencoContattiDa;
	}

	public void setElencoContattiDa(List<SoggettoContattoExt> elencoContattiDa) {
		this.elencoContattiDa = elencoContattiDa;
	}

	public List<SoggettoContattoExt> getElencoContattiA() {
		return elencoContattiA;
	}

	public void setElencoContattiA(List<SoggettoContattoExt> elencoContattiA) {
		this.elencoContattiA = elencoContattiA;
	}

	

	
}
