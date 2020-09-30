package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.util.List;
import java.util.Map;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Laboratorio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Sindaco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.UtenteContatto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.profilo.Profilo;

public class UserLogged extends Utenti {

	private boolean enabled = false;
	private Asr asr;
	private List<Profilo> elencoProfilo;
	private List<Ente> elencoEnte;
	private List<Struttura> elencoStruttura;
	private List<Area> elencoArea;
	private String runnningApplication;
	private SindacoComune sindaco;
	private Medico medico;
	private UtenteContatto contatto;
	
	public List<Profilo> getElencoProfilo() {
		return elencoProfilo;
	}

	public void setElencoProfilo(List<Profilo> elencoProfilo) {
		this.elencoProfilo = elencoProfilo;
	}

	public List<Ente> getElencoEnte() {
		return elencoEnte;
	}

	public void setElencoEnte(List<Ente> elencoEnte) {
		this.elencoEnte = elencoEnte;
	}

	public List<Struttura> getElencoStruttura() {
		return elencoStruttura;
	}

	public void setElencoStruttura(List<Struttura> elencoStruttura) {
		this.elencoStruttura = elencoStruttura;
	}

	public List<Area> getElencoArea() {
		return elencoArea;
	}

	public void setElencoArea(List<Area> elencoArea) {
		this.elencoArea = elencoArea;
	}

	private String cognome;
	private String nome;
	
	public Asr getAsr() {
		return asr;
	}

	public void setAsr(Asr asr) {
		this.asr = asr;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	private Laboratorio laboratorio;
	
	
	public boolean getEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRunnningApplication() {
		return runnningApplication;
	}

	public void setRunnningApplication(String runnningApplication) {
		this.runnningApplication = runnningApplication;
	}

	public SindacoComune getSindaco() {
		return sindaco;
	}

	public void setSindaco(SindacoComune sindaco) {
		this.sindaco = sindaco;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public UtenteContatto getContatto() {
		return contatto;
	}

	public void setContatto(UtenteContatto contatto) {
		this.contatto = contatto;
	}
}
