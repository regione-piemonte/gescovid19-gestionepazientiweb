package it.csi.gestionepazienti.gestionepazientiweb.dto.custom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
/*
 * L'ordine delle property qui sotto coincide con l'export excel
 */
public class SoggettoForTrasferimentoPlain extends AbstractDto {

	private String codiceFiscale="";
	private String cognome="";
	private String nome="";
	private String dataNascita="";
	private String telefonoMobile="";
	
	private String dataEvento="";
	private String dataFineQuarantena="";
	
	private String isolamentoPresso="";
	private String struttura="";
	private String area="";
	private String evento="";
	private String operatoreSanitario="";

	/*
	 * 						<div class="div-table-cell" role="cell" >{{p.comuneRicovero.nomeComune}} {{p.decorso.indirizzoDecorso }} {{p.decorso.decorsoPresso }} </div>		
						<div class="div-table-cell" role="cell" >{{p.struttura.nome}}</div>		
						<div class="div-table-cell" role="cell" >{{p.area.nome}}</div>		
						<div class="div-table-cell" role="cell" >{{p.tipoEvento.descTipoEvento}}</div>	
	 */
	public SoggettoForTrasferimentoPlain(SoggettoForTrasferimento sl) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.codiceFiscale = sl.getCodiceFiscale();
		this.cognome = sl.getCognome();
		if (sl.getDataNascita()!=null)
			this.dataNascita = df.format(sl.getDataNascita());
		this.nome = sl.getNome();
		this.telefonoMobile = sl.getTelefonoRecapito();

		if (sl.getDecorso()!=null && sl.getDecorso().getDataDimissioni()!=null)
			this.dataEvento = df.format(sl.getDecorso().getDataDimissioni());
		if (sl.getDecorso()!=null && sl.getDecorso().getDataPrevFineEvento()!=null)
			this.dataFineQuarantena = df.format(sl.getDecorso().getDataPrevFineEvento());
		
		String comuneRic = "";
		String indirizzoRic = "";
		String pressoRic = "";
		if (sl.getComuneRicovero()!=null && sl.getComuneRicovero().getNomeComune()!=null)
			comuneRic = sl.getComuneRicovero().getNomeComune();
		if (sl.getDecorso()!=null && sl.getDecorso().getIndirizzoDecorso()!=null)
			indirizzoRic = sl.getDecorso().getIndirizzoDecorso();
		if (sl.getDecorso()!=null && sl.getDecorso().getDecorsoPresso()!=null)
			pressoRic = sl.getDecorso().getDecorsoPresso();
		
		this.isolamentoPresso = comuneRic + " " + indirizzoRic +" "+ pressoRic;
		
		if (sl.getStruttura()!=null && sl.getStruttura().getNome()!=null)
			this.struttura = sl.getStruttura().getNome();
		if (sl.getArea()!=null && sl.getArea().getNome()!=null)
			this.area = sl.getArea().getNome();
		if (sl.getTipoEvento()!=null && sl.getTipoEvento().getDescTipoEvento()!=null)
			this.evento = sl.getTipoEvento().getDescTipoEvento();
		if (sl.getTipoSoggetto()!=null && sl.getTipoSoggetto().getDescTipoSoggetto()!=null)
			this.operatoreSanitario = sl.getTipoSoggetto().getDescTipoSoggetto();
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}


	public String getTelefonoMobile() {
		return telefonoMobile;
	}


	public void setTelefonoMobile(String telefonoMobile) {
		this.telefonoMobile = telefonoMobile;
	}



	public String getDataFineQuarantena() {
		return dataFineQuarantena;
	}


	public void setDataFineQuarantena(String dataFineQuarantena) {
		this.dataFineQuarantena = dataFineQuarantena;
	}


	public String getIsolamentoPresso() {
		return isolamentoPresso;
	}


	public void setIsolamentoPresso(String isolamentoPresso) {
		this.isolamentoPresso = isolamentoPresso;
	}


	public String getStruttura() {
		return struttura;
	}


	public void setStruttura(String struttura) {
		this.struttura = struttura;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getDataEvento() {
		return dataEvento;
	}


	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}


	public String getEvento() {
		return evento;
	}


	public void setEvento(String evento) {
		this.evento = evento;
	}


	public String getOperatoreSanitario() {
		return operatoreSanitario;
	}


	public void setOperatoreSanitario(String operatoreSanitario) {
		this.operatoreSanitario = operatoreSanitario;
	}



}
