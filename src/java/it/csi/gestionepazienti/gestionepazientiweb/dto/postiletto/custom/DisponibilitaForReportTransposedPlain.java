package it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;


// deprecato solo per CSV
public class DisponibilitaForReportTransposedPlain extends AbstractDto {

	
	private String nomeEnte;
	private String nomeStruttura;
	private String naturaStruttura;
	private String numPLAttivatiMediaIntensita;
	private String numPLOccupatiMediaIntensita;
	private String numPLAttivatiTerapiaIntensiva;
	private String numPLOccupatiTerapiaIntensiva;
	private String numPLAttivatiTerapiaSemiIntensiva;
	private String numPLOccupatiTerapiaSemiIntensiva;
	private String numPLAttivatiPneumologia;
	private String numPLOccupatiPneumologia;
	private String numPLAttivatiMalInfettive;
	private String numPLOccupatiMalInfettive;
	private String numPLAttivatiAltro;
	private String numPLOccupatiAltro;
	private String numPLAttivatiTotale;
	private String numPLOccupatiTotale;
	public String getNomeEnte() {
		return nomeEnte;
	}
	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	public String getNomeStruttura() {
		return nomeStruttura;
	}
	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}
	public String getNaturaStruttura() {
		return naturaStruttura;
	}
	public void setNaturaStruttura(String naturaStruttura) {
		this.naturaStruttura = naturaStruttura;
	}
	public String getNumPLAttivatiMediaIntensita() {
		return numPLAttivatiMediaIntensita;
	}
	public void setNumPLAttivatiMediaIntensita(String numPLAttivatiMediaIntensita) {
		this.numPLAttivatiMediaIntensita = numPLAttivatiMediaIntensita;
	}
	public String getNumPLOccupatiMediaIntensita() {
		return numPLOccupatiMediaIntensita;
	}
	public void setNumPLOccupatiMediaIntensita(String numPLOccupatiMediaIntensita) {
		this.numPLOccupatiMediaIntensita = numPLOccupatiMediaIntensita;
	}
	public String getNumPLAttivatiTerapiaIntensiva() {
		return numPLAttivatiTerapiaIntensiva;
	}
	public void setNumPLAttivatiTerapiaIntensiva(String numPLAttivatiTerapiaIntensiva) {
		this.numPLAttivatiTerapiaIntensiva = numPLAttivatiTerapiaIntensiva;
	}
	public String getNumPLOccupatiTerapiaIntensiva() {
		return numPLOccupatiTerapiaIntensiva;
	}
	public void setNumPLOccupatiTerapiaIntensiva(String numPLOccupatiTerapiaIntensiva) {
		this.numPLOccupatiTerapiaIntensiva = numPLOccupatiTerapiaIntensiva;
	}
	public String getNumPLAttivatiTerapiaSemiIntensiva() {
		return numPLAttivatiTerapiaSemiIntensiva;
	}
	public void setNumPLAttivatiTerapiaSemiIntensiva(String numPLAttivatiTerapiaSemiIntensiva) {
		this.numPLAttivatiTerapiaSemiIntensiva = numPLAttivatiTerapiaSemiIntensiva;
	}
	public String getNumPLOccupatiTerapiaSemiIntensiva() {
		return numPLOccupatiTerapiaSemiIntensiva;
	}
	public void setNumPLOccupatiTerapiaSemiIntensiva(String numPLOccupatiTerapiaSemiIntensiva) {
		this.numPLOccupatiTerapiaSemiIntensiva = numPLOccupatiTerapiaSemiIntensiva;
	}
	public String getNumPLAttivatiPneumologia() {
		return numPLAttivatiPneumologia;
	}
	public void setNumPLAttivatiPneumologia(String numPLAttivatiPneumologia) {
		this.numPLAttivatiPneumologia = numPLAttivatiPneumologia;
	}
	public String getNumPLOccupatiPneumologia() {
		return numPLOccupatiPneumologia;
	}
	public void setNumPLOccupatiPneumologia(String numPLOccupatiPneumologia) {
		this.numPLOccupatiPneumologia = numPLOccupatiPneumologia;
	}
	public String getNumPLAttivatiMalInfettive() {
		return numPLAttivatiMalInfettive;
	}
	public void setNumPLAttivatiMalInfettive(String numPLAttivatiMalInfettive) {
		this.numPLAttivatiMalInfettive = numPLAttivatiMalInfettive;
	}
	public String getNumPLOccupatiMalInfettive() {
		return numPLOccupatiMalInfettive;
	}
	public void setNumPLOccupatiMalInfettive(String numPLOccupatiMalInfettive) {
		this.numPLOccupatiMalInfettive = numPLOccupatiMalInfettive;
	}
	public String getNumPLAttivatiAltro() {
		return numPLAttivatiAltro;
	}
	public void setNumPLAttivatiAltro(String numPLAttivatiAltro) {
		this.numPLAttivatiAltro = numPLAttivatiAltro;
	}
	public String getNumPLOccupatiAltro() {
		return numPLOccupatiAltro;
	}
	public void setNumPLOccupatiAltro(String numPLOccupatiAltro) {
		this.numPLOccupatiAltro = numPLOccupatiAltro;
	}
	public String getNumPLAttivatiTotale() {
		return numPLAttivatiTotale;
	}
	public void setNumPLAttivatiTotale(String numPLAttivatiTotale) {
		this.numPLAttivatiTotale = numPLAttivatiTotale;
	}
	public String getNumPLOccupatiTotale() {
		return numPLOccupatiTotale;
	}
	public void setNumPLOccupatiTotale(String numPLOccupatiTotale) {
		this.numPLOccupatiTotale = numPLOccupatiTotale;
	}
	
	
}
