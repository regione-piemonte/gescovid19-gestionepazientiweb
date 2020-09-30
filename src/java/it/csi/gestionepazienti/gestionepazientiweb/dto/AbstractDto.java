package it.csi.gestionepazienti.gestionepazientiweb.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class  AbstractDto implements Serializable {

}
