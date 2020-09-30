
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.find;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per datiAnagraficiMsg complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datiAnagraficiMsg">
 *   &lt;complexContent>
 *     &lt;extension base="{http://AnagrafeFind.central.services.auraws.aura.csi.it}Ens_Response">
 *       &lt;sequence>
 *         &lt;element name="header" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}Header" minOccurs="0"/>
 *         &lt;element name="body" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}DatiAnagraficiBody" minOccurs="0"/>
 *         &lt;element name="footer" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}Footer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindProfiliAnagraficiResult", propOrder = {
    "header",
    "body",
    "footer"
})
@XmlRootElement(name= "FindProfiliAnagraficiResult",   namespace="http://AnagrafeFind.central.services.auraws.aura.csi.it")
public class FindProfiliAnagraficiResult
{

    protected Header header;
    protected DatiAnagraficiBody body;
    protected Footer footer;

    /**
     * Recupera il valore della proprieta header.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Imposta il valore della proprieta header.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Recupera il valore della proprieta body.
     * 
     * @return
     *     possible object is
     *     {@link DatiAnagraficiBody }
     *     
     */
    public DatiAnagraficiBody getBody() {
        return body;
    }

    /**
     * Imposta il valore della proprieta body.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAnagraficiBody }
     *     
     */
    public void setBody(DatiAnagraficiBody value) {
        this.body = value;
    }

    /**
     * Recupera il valore della proprieta footer.
     * 
     * @return
     *     possible object is
     *     {@link Footer }
     *     
     */
    public Footer getFooter() {
        return footer;
    }

    /**
     * Imposta il valore della proprieta footer.
     * 
     * @param value
     *     allowed object is
     *     {@link Footer }
     *     
     */
    public void setFooter(Footer value) {
        this.footer = value;
    }

}
