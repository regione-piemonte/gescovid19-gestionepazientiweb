package it.csi.gestionepazienti.gestionepazientiweb.business.delegate;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import it.csi.gestionepazienti.gestionepazientiweb.business.aura.find.FindProfiliAnagraficiResult;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.GetProfiloSanitarioResponse;

public class AuraWsDelegate {
	static Logger log = Logger.getLogger(AuraWsDelegate.class);
	
	public static  FindProfiliAnagraficiResult findProfiliAnagrafici(String codiceFiscale,String nome,String cognome,
			String dataNascita, String auraUser, String auraPwd, String auraprefixUrl, int auraTimeout  )throws Exception {
//		log.info("| inizio findProfiliAnagrafici: ");
		String xmlInput = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
				"   <S:Header>\n" + 
				"      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" + 
				"         <wsse:UsernameToken xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + 
				"            <wsse:Username>"+auraUser+"</wsse:Username>\n" + 
				"            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+auraPwd+"</wsse:Password>\n" + 
				"         </wsse:UsernameToken>\n" + 
				"      </wsse:Security>\n" + 
				"   </S:Header>\n" + 
				"   <S:Body>\n" + 
				"      <ns2:FindProfiliAnagrafici xmlns:ns2=\"http://AnagrafeFind.central.services.auraws.aura.csi.it\">\n" + 
				"         <ns2:profiliRequest>\n" ;
			if (codiceFiscale!=null)
				xmlInput += "            <codiceFiscale>"+codiceFiscale+"</codiceFiscale> ";
			if (nome!=null)
				xmlInput += "             <nome>"+nome+"</nome> ";
			if (cognome!=null)
				xmlInput += "            <cognome>"+cognome+"</cognome>\n";
			if (dataNascita!=null)
				 xmlInput += "    <dataNascita>"+dataNascita+"</dataNascita>\n";
			xmlInput +=
				"            <flagDecesso>1</flagDecesso>\n" + 
				"         </ns2:profiliRequest>\n" + 
				"      </ns2:FindProfiliAnagrafici>\n" + 
				"   </S:Body>\n" + 
				"</S:Envelope>";

		String SOAPAction = "FindProfiliAnagrafici";

//		log.info("| stringa profili anagrafici: "+xmlInput);
		// test "JabooCh0"
		try {
//			log.info("| Chiamata al ws " );
			String webServiceResponse = WebServiceDelegate.callWebService(auraprefixUrl + ".AnagrafeFind.cls", null, null, xmlInput, SOAPAction, "text/xml",auraTimeout);
			log.info(webServiceResponse);

			SOAPMessage message = MessageFactory.newInstance().createMessage(null,
				    new ByteArrayInputStream(webServiceResponse.getBytes()));
			Unmarshaller unmarshaller = JAXBContext.newInstance(FindProfiliAnagraficiResult.class).createUnmarshaller();
			FindProfiliAnagraficiResult datiAnagraficiMsg = (FindProfiliAnagraficiResult)unmarshaller.unmarshal(
					message.getSOAPBody().extractContentAsDocument().getFirstChild().getFirstChild());

			
			
			return datiAnagraficiMsg;
		} catch (Exception e) {
			log.error("Eccezione durante la chiamata a Aura", e);
			throw e;
		}
	}
	
	public static  GetProfiloSanitarioResponse getProfiloSanitario(String auraId, String auraUser, String auraPwd, String auraprefixUrl, int auraTimeout) throws Exception{
		String xmlInput = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:anag=\"http://AnagrafeSanitaria.central.services.auraws.aura.csi.it\">\n" + 
				"   <S:Header>\n" + 
				"      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" + 
				"         <wsse:UsernameToken xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + 
				"            <wsse:Username>"+auraUser+"</wsse:Username>\n" + 
				"            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+auraPwd+"</wsse:Password>\n" + 
				"         </wsse:UsernameToken>\n" + 
				"      </wsse:Security>\n" + 
				"   </S:Header>\n" + 
				"   <S:Body>\n" + 
				"      <anag:GetProfiloSanitario>\n" + 
				"         <!--Optional:-->\n" + 
				"         <anag:AURAid>"+auraId+"</anag:AURAid>\n" + 
				"      </anag:GetProfiloSanitario>\n" + 
				"   </S:Body>\n" + 
				"</S:Envelope>";
		
		String SOAPAction = "GetProfiloSanitario";

		// test "JabooCh0"
		try {
			log.trace(xmlInput);
			String webServiceResponse = WebServiceDelegate.callWebService(auraprefixUrl + ".AnagrafeSanitaria.cls", null, null, xmlInput, SOAPAction, "text/xml", auraTimeout);
			log.info(webServiceResponse);

			SOAPMessage message = MessageFactory.newInstance().createMessage(null,
				    new ByteArrayInputStream(webServiceResponse.getBytes()));
			Unmarshaller unmarshaller = JAXBContext.newInstance(GetProfiloSanitarioResponse.class).createUnmarshaller();
			GetProfiloSanitarioResponse profiloSanitario = (GetProfiloSanitarioResponse)unmarshaller.unmarshal(
					message.getSOAPBody().extractContentAsDocument().getFirstChild());

			return profiloSanitario;
		} catch (Exception e) {
			log.error("Eccezione durante la chiamata a Aura", e);
			throw e;
		}
	}
}
