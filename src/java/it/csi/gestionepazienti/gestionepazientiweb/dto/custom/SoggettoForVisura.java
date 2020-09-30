package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Tampone;

public class SoggettoForVisura extends SoggettoForTrasferimento {

	private Tampone tampone;
	private Comuni comuneDomicilio;
	private Comuni comuneResidenza;
	private Medico medico; 
	
	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Tampone getTampone() {
		return tampone;
	}

	public void setTampone(Tampone tampone) {
		this.tampone = tampone;
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


}