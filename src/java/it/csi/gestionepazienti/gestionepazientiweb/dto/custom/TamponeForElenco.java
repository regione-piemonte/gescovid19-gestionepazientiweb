package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Laboratorio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RisTampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Tampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TamponeMotivo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TestCovid;

public class TamponeForElenco extends Tampone {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Laboratorio laboratorio;
	private TestCovid	testCovid;
	private RisTampone  risTampone;
	private TamponeMotivo tamponeMotivo;
	
	public TamponeMotivo getTamponeMotivo() {
		return tamponeMotivo;
	}
	public void setTamponeMotivo(TamponeMotivo tamponeMotivo) {
		this.tamponeMotivo = tamponeMotivo;
	}
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	public TestCovid getTestCovid() {
		return testCovid;
	}
	public void setTestCovid(TestCovid testCovid) {
		this.testCovid = testCovid;
	}
	public RisTampone getRisTampone() {
		return risTampone;
	}
	public void setRisTampone(RisTampone risTampone) {
		this.risTampone = risTampone;
	}
	
	
	
}
