
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import it.csi.gestionepazienti.gestionepazientiweb.business.aura.EnsMessagebody;


/**
 * <p>Classe Java per Ens_Response complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Ens_Response">
 *   &lt;complexContent>
 *     &lt;extension base="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}Ens_Messagebody">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Response")
@XmlSeeAlso({
    SoggettoAuraMsg.class
})
public class EnsResponse
    extends EnsMessagebody
{


}
