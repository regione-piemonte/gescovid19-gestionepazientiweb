package it.csi.gestionepazienti.gestionepazientiweb.dto;

public class Nazione extends AbstractDto {

	private static final long serialVersionUID = -4332578753980403648L;
	private Long id;
	private String codice;
	private String descrizione;
	private boolean attiva;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public boolean isAttiva() {
		return attiva;
	}

	public void setAttiva(boolean attiva) {
		this.attiva = attiva;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}