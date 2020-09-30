
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.find;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per findProfiliAnagraficiRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="findProfiliAnagraficiRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://AnagrafeFind.central.services.auraws.aura.csi.it}Ens_Request">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagDecesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findProfiliAnagraficiRequest", propOrder = {
    "codiceFiscale",
    "cognome",
    "nome",
    "dataNascita",
    "dataDa",
    "dataA",
    "flagDecesso",
    "idEnte"
})
public class FindProfiliAnagraficiRequest
    extends EnsRequest
{

    protected String codiceFiscale;
    protected String cognome;
    protected String nome;
    protected String dataNascita;
    protected String dataDa;
    protected String dataA;
    protected String flagDecesso;
    protected String idEnte;

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
     * Recupera il valore della proprieta dataNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
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
     *     {@link String }
     *     
     */
    public void setDataNascita(String value) {
        this.dataNascita = value;
    }

    /**
     * Recupera il valore della proprieta dataDa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataDa() {
        return dataDa;
    }

    /**
     * Imposta il valore della proprieta dataDa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataDa(String value) {
        this.dataDa = value;
    }

    /**
     * Recupera il valore della proprieta dataA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataA() {
        return dataA;
    }

    /**
     * Imposta il valore della proprieta dataA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataA(String value) {
        this.dataA = value;
    }

    /**
     * Recupera il valore della proprieta flagDecesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagDecesso() {
        return flagDecesso;
    }

    /**
     * Imposta il valore della proprieta flagDecesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagDecesso(String value) {
        this.flagDecesso = value;
    }

    /**
     * Recupera il valore della proprieta idEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdEnte() {
        return idEnte;
    }

    /**
     * Imposta il valore della proprieta idEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdEnte(String value) {
        this.idEnte = value;
    }

}
