package it.csi.gestionepazienti.gestionepazientiweb.dto.errore;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;



public class Dettaglio implements Serializable  {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -4114793308343985135L;
private String chiave = null;
  private String valore = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("chiave") 
 
  public String getChiave() {
    return chiave;
  }
  public void setChiave(String chiave) {
    this.chiave = chiave;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("valore") 
 
  public String getValore() {
    return valore;
  }
  public void setValore(String valore) {
    this.valore = valore;
  }
  
  
@Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dettaglio dettaglio = (Dettaglio) o;
    return Objects.equals(chiave, dettaglio.chiave) &&
        Objects.equals(valore, dettaglio.valore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chiave, valore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dettaglio {\n");
    
    sb.append("    chiave: ").append(toIndentedString(chiave)).append("\n");
    sb.append("    valore: ").append(toIndentedString(valore)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

