
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DatiPrimari complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiPrimari">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDecesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siglaProvNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoCodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoProfiloAnagrafico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiPrimari", propOrder = {
    "codCittadinanza",
    "codComuneNascita",
    "codStatoNascita",
    "codiceFiscale",
    "cognome",
    "dataDecesso",
    "dataNascita",
    "descCittadinanza",
    "descComuneNascita",
    "descStatoNascita",
    "nome",
    "sesso",
    "siglaProvNascita",
    "statoCodiceFiscale",
    "statoProfiloAnagrafico"
})
public class DatiPrimari {

    protected String codCittadinanza;
    protected String codComuneNascita;
    protected String codStatoNascita;
    protected String codiceFiscale;
    protected String cognome;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDecesso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    protected String descCittadinanza;
    protected String descComuneNascita;
    protected String descStatoNascita;
    protected String nome;
    protected String sesso;
    protected String siglaProvNascita;
    protected String statoCodiceFiscale;
    protected String statoProfiloAnagrafico;

    /**
     * Recupera il valore della proprieta codCittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    /**
     * Imposta il valore della proprieta codCittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCittadinanza(String value) {
        this.codCittadinanza = value;
    }

    /**
     * Recupera il valore della proprieta codComuneNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComuneNascita() {
        return codComuneNascita;
    }

    /**
     * Imposta il valore della proprieta codComuneNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComuneNascita(String value) {
        this.codComuneNascita = value;
    }

    /**
     * Recupera il valore della proprieta codStatoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoNascita() {
        return codStatoNascita;
    }

    /**
     * Imposta il valore della proprieta codStatoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoNascita(String value) {
        this.codStatoNascita = value;
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
     * Recupera il valore della proprieta dataDecesso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDecesso() {
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
    public void setDataDecesso(XMLGregorianCalendar value) {
        this.dataDecesso = value;
    }

    /**
     * Recupera il valore della proprieta dataNascita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascita() {
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
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Recupera il valore della proprieta descCittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCittadinanza() {
        return descCittadinanza;
    }

    /**
     * Imposta il valore della proprieta descCittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCittadinanza(String value) {
        this.descCittadinanza = value;
    }

    /**
     * Recupera il valore della proprieta descComuneNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComuneNascita() {
        return descComuneNascita;
    }

    /**
     * Imposta il valore della proprieta descComuneNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComuneNascita(String value) {
        this.descComuneNascita = value;
    }

    /**
     * Recupera il valore della proprieta descStatoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescStatoNascita() {
        return descStatoNascita;
    }

    /**
     * Imposta il valore della proprieta descStatoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescStatoNascita(String value) {
        this.descStatoNascita = value;
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
     * Recupera il valore della proprieta siglaProvNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvNascita() {
        return siglaProvNascita;
    }

    /**
     * Imposta il valore della proprieta siglaProvNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvNascita(String value) {
        this.siglaProvNascita = value;
    }

    /**
     * Recupera il valore della proprieta statoCodiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoCodiceFiscale() {
        return statoCodiceFiscale;
    }

    /**
     * Imposta il valore della proprieta statoCodiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoCodiceFiscale(String value) {
        this.statoCodiceFiscale = value;
    }

    /**
     * Recupera il valore della proprieta statoProfiloAnagrafico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoProfiloAnagrafico() {
        return statoProfiloAnagrafico;
    }

    /**
     * Imposta il valore della proprieta statoProfiloAnagrafico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoProfiloAnagrafico(String value) {
        this.statoProfiloAnagrafico = value;
    }

}
