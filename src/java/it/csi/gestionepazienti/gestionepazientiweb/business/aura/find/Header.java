
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.find;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Header complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Header">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAuraRicondotto" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="numeroTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceRitorno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="eventi" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}ArrayOfeventoEvento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = {
    "idAuraRicondotto",
    "numeroTicket",
    "codiceRitorno",
    "requestDateTime",
    "eventi"
})
public class Header {

    protected Long idAuraRicondotto;
    protected String numeroTicket;
    protected String codiceRitorno;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestDateTime;
    protected ArrayOfeventoEvento eventi;

    /**
     * Recupera il valore della proprieta idAuraRicondotto.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdAuraRicondotto() {
        return idAuraRicondotto;
    }

    /**
     * Imposta il valore della proprieta idAuraRicondotto.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdAuraRicondotto(Long value) {
        this.idAuraRicondotto = value;
    }

    /**
     * Recupera il valore della proprieta numeroTicket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroTicket() {
        return numeroTicket;
    }

    /**
     * Imposta il valore della proprieta numeroTicket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroTicket(String value) {
        this.numeroTicket = value;
    }

    /**
     * Recupera il valore della proprieta codiceRitorno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRitorno() {
        return codiceRitorno;
    }

    /**
     * Imposta il valore della proprieta codiceRitorno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRitorno(String value) {
        this.codiceRitorno = value;
    }

    /**
     * Recupera il valore della proprieta requestDateTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * Imposta il valore della proprieta requestDateTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestDateTime(XMLGregorianCalendar value) {
        this.requestDateTime = value;
    }

    /**
     * Recupera il valore della proprieta eventi.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfeventoEvento }
     *     
     */
    public ArrayOfeventoEvento getEventi() {
        return eventi;
    }

    /**
     * Imposta il valore della proprieta eventi.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfeventoEvento }
     *     
     */
    public void setEventi(ArrayOfeventoEvento value) {
        this.eventi = value;
    }

}
