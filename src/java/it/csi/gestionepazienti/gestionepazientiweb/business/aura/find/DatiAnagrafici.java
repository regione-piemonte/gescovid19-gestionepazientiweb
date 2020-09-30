
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.find;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DatiAnagrafici complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiAnagrafici">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idProfiloAnagrafico" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[a-zA-Z]{6}\d{2}[a-zA-Z]\d{2}[a-zA-Z]\d{3}[a-zA-Z]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cognome" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sesso" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="M|F|I"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="codiceStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinciaNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDecesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAnagrafici", propOrder = {
    "idProfiloAnagrafico",
    "codiceFiscale",
    "cognome",
    "nome",
    "sesso",
    "dataNascita",
    "codiceStatoNascita",
    "statoNascita",
    "comuneNascita",
    "codiceComuneNascita",
    "provinciaNascita",
    "dataDecesso"
})
public class DatiAnagrafici {

    protected BigDecimal idProfiloAnagrafico;
    protected String codiceFiscale;
    protected String cognome;
    protected String nome;
    protected String sesso;
//    @XmlSchemaType(name = "dateTime")
    protected String dataNascita;
    protected String codiceStatoNascita;
    protected String statoNascita;
    protected String comuneNascita;
    protected String codiceComuneNascita;
    protected String provinciaNascita;
//    @XmlSchemaType(name = "dateTime")
    protected String dataDecesso;

    /**
     * Recupera il valore della proprieta idProfiloAnagrafico.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdProfiloAnagrafico() {
        return idProfiloAnagrafico;
    }

    /**
     * Imposta il valore della proprieta idProfiloAnagrafico.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdProfiloAnagrafico(BigDecimal value) {
        this.idProfiloAnagrafico = value;
    }

    /**
     * Recupera il valore della proprieta codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprieta codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprieta cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprieta cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprieta nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprieta nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprieta sesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprieta sesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Recupera il valore della proprieta dataNascita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta il valore della proprieta dataNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascita(String value) {
        this.dataNascita = value;
    }

    /**
     * Recupera il valore della proprieta codiceStatoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStatoNascita() {
        return codiceStatoNascita;
    }

    /**
     * Imposta il valore della proprieta codiceStatoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStatoNascita(String value) {
        this.codiceStatoNascita = value;
    }

    /**
     * Recupera il valore della proprieta statoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoNascita() {
        return statoNascita;
    }

    /**
     * Imposta il valore della proprieta statoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoNascita(String value) {
        this.statoNascita = value;
    }

    /**
     * Recupera il valore della proprieta comuneNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Imposta il valore della proprieta comuneNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneNascita(String value) {
        this.comuneNascita = value;
    }

    /**
     * Recupera il valore della proprieta codiceComuneNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComuneNascita() {
        return codiceComuneNascita;
    }

    /**
     * Imposta il valore della proprieta codiceComuneNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComuneNascita(String value) {
        this.codiceComuneNascita = value;
    }

    /**
     * Recupera il valore della proprieta provinciaNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaNascita() {
        return provinciaNascita;
    }

    /**
     * Imposta il valore della proprieta provinciaNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaNascita(String value) {
        this.provinciaNascita = value;
    }

    /**
     * Recupera il valore della proprieta dataDecesso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getDataDecesso() {
        return dataDecesso;
    }

    /**
     * Imposta il valore della proprieta dataDecesso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDecesso(String value) {
        this.dataDecesso = value;
    }

}
