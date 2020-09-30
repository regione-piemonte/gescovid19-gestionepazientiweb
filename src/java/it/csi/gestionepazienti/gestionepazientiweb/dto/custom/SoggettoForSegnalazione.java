package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.util.ArrayList;
import java.util.List;

import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;

public class SoggettoForSegnalazione extends Soggetto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idAura;
	private DecorsoSintomo decorso;
	
	private List<IstitutoScolastico> elencoIstitutiScolastico = new ArrayList<IstitutoScolastico>();
		
	public List<IstitutoScolastico> getElencoIstitutiScolastico() {
		return elencoIstitutiScolastico;
	}

	public void setElencoIstitutiScolastico(List<IstitutoScolastico> elencoIstitutiScolastico) {
		this.elencoIstitutiScolastico = elencoIstitutiScolastico;
	}

	
	public Long getIdAura() {
		return idAura;
	}

	public void setIdAura(Long idAura) {
		this.idAura = idAura;
	}

	public DecorsoSintomo getDecorso() {
		return decorso;
	}

	public void setDecorso(DecorsoSintomo decorso) {
		this.decorso = decorso;
	}

	
}
