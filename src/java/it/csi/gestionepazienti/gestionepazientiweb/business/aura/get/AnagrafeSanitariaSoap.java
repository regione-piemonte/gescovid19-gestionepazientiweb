
package it.csi.gestionepazienti.gestionepazientiweb.business.aura.get;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import it.csi.gestionepazienti.gestionepazientiweb.business.aura.ObjectFactory;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AnagrafeSanitariaSoap", targetNamespace = "http://AnagrafeSanitaria.central.services.auraws.aura.csi.it")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AnagrafeSanitariaSoap {


    /**
     * 
     * @param aurAid
     * @return
     *     returns it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.SoggettoAuraMsg
     */
    @WebMethod(operationName = "GetProfiloSanitario", action = "http://AnagrafeSanitaria.central.services.auraws.aura.csi.it/AURA.WS.AnagrafeSanitaria.GetProfiloSanitario")
    @WebResult(name = "GetProfiloSanitarioResult", targetNamespace = "http://AnagrafeSanitaria.central.services.auraws.aura.csi.it")
    @RequestWrapper(localName = "GetProfiloSanitario", targetNamespace = "http://AnagrafeSanitaria.central.services.auraws.aura.csi.it", className = "it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.GetProfiloSanitario")
    @ResponseWrapper(localName = "GetProfiloSanitarioResponse", targetNamespace = "http://AnagrafeSanitaria.central.services.auraws.aura.csi.it", className = "it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.GetProfiloSanitarioResponse")
    public SoggettoAuraMsg getProfiloSanitario(
        @WebParam(name = "AURAid", targetNamespace = "http://AnagrafeSanitaria.central.services.auraws.aura.csi.it")
        String aurAid);

}