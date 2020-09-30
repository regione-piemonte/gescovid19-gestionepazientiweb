package it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import it.csi.gestionepazienti.gestionepazientiweb.dto.AbstractDto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;

public class DisponibilitaForReportTransposed extends AbstractDto {

	
	private Ente ente;
	private Struttura struttura;
	private String dispoArray;
	private List<DisponibilitaArea> disponibilitaArea;
	
	public List<DisponibilitaArea> getDisponibilitaArea() {
		return disponibilitaArea;
	}
	public void setDisponibilitaArea(List<DisponibilitaArea> disponibilitaArea) {
		this.disponibilitaArea = disponibilitaArea;
	}
	public String getDispoArray() {
		return dispoArray;
	}
	public void setDispoArray(String dispoArray) {
		if (dispoArray != null) {
			//componentsString = componentsString.replaceAll("id_phenomenon", "idPhenomenon").replaceAll("id_measure_unit", "idMeasureUnit").replaceAll("id_data_type", "idDataType");
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
			try {
				DisponibilitaArea[] arr = mapper.readValue(dispoArray, DisponibilitaArea[].class);
				if (arr !=null)
					setDisponibilitaArea(Arrays.asList(arr));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public Ente getEnte() {
		return ente;
	}
	public void setEnte(Ente ente) {
		this.ente = ente;
	}
	public Struttura getStruttura() {
		return struttura;
	}
	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}

	
}
