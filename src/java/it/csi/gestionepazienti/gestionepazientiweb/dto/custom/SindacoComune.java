package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Sindaco;

public class SindacoComune extends Sindaco{

	private Comuni comune;

	public Comuni getComune() {
		return comune;
	}

	public void setComune(Comuni comune) {
		this.comune = comune;
	}
	
	
}
