package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;


import it.csi.gestionepazienti.gestionepazientiweb.dto.UtenteContatto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.test.Test;
import it.csi.gestionepazienti.gestionepazientiweb.dto.test.TestEsito;
import it.csi.gestionepazienti.gestionepazientiweb.dto.test.TestRichiestaTipo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.test.TestTipo;

public class TestExt extends Test {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TestEsito testEsito;
	private TestTipo  testTipo;
	private TestRichiestaTipo testRichiestaTipo;
	
	private UtenteContatto utente;
	
	
	public UtenteContatto getUtente() {
		return utente;
	}
	public void setUtente(UtenteContatto utente) {
		this.utente = utente;
	}
	public TestEsito getTestEsito() {
		return testEsito;
	}
	public void setTestEsito(TestEsito testEsito) {
		this.testEsito = testEsito;
	}
	public TestTipo getTestTipo() {
		return testTipo;
	}
	public void setTestTipo(TestTipo testTipo) {
		this.testTipo = testTipo;
	}
	public TestRichiestaTipo getTestRichiestaTipo() {
		return testRichiestaTipo;
	}
	public void setTestRichiestaTipo(TestRichiestaTipo testRichiestaTipo) {
		this.testRichiestaTipo = testRichiestaTipo;
	}
	
	
}
