package it.csi.gestionepazienti.gestionepazientiweb.dto;

import java.sql.Date;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

public class Payload2 {

	private Integer idMedicoDelegato;
	private Integer idMedicoDelegante;
	
	private Date dataInizioValidita;
	private Date dataFineValidita;
	
		

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Payload2 payload = (Payload2) o;
		return Objects.equals(dataFineValidita, payload.dataFineValidita);
				
	}

	@JsonProperty("idMedicoDelegato")
	public Integer getIdMedicoDelegato() {
		return idMedicoDelegato;
	}

	public void setIdMedicoDelegato(Integer idMedicoDelegato) {
		this.idMedicoDelegato = idMedicoDelegato;
	}
	@JsonProperty("idMedicoDelegante")
	public Integer getIdMedicoDelegante() {
		return idMedicoDelegante;
	}

	public void setIdMedicoDelegante(Integer idMedicoDelegante) {
		this.idMedicoDelegante = idMedicoDelegante;
	}

	@JsonProperty("dataInizioValidita")
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}


	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	@JsonProperty("dataFineValidita")
	public Date getDataFineValidita() {
		return dataFineValidita;
	}


	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}


	@Override
	public int hashCode() {
		return Objects.hash(dataFineValidita);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Payload {\n");
		sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
		sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
