package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.RefertiApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.ErroreBuilder;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.WebMessages;
import it.csi.gestionepazienti.gestionepazientiweb.dto.profilo.Profilo;
import it.csi.gestionepazienti.gestionepazientiweb.util.GenericUtils;;

@Service
public class RefertiApiServiceImpl extends AuditableApiServiceImpl implements RefertiApi {
	
	@Autowired
	private ImportReferto importReferto;
	
	
	@Override
	public Response insertReferti(String payloadPlain, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		
		UserLogged currentUser = getBeService().getCurrentUser(req);
		if(!hasProfilo(currentUser, GenericUtils.ID_PROFILO_LAB_IMPORT)) {
			return ErroreBuilder.from(WebMessages.UTENTE_NON_AUTORIZZATO).dettaglio("E1", "Utente senza profilo corretto")
					.exception().getResponse(); 
		}
		
		if(StringUtils.isEmpty(payloadPlain)) {
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO)
					.title("CSV non valorizzato")
					.dettaglio("E2", "Payload null o vuoto")
			.exception().getResponse();
		}
		
		String[] split = payloadPlain.split("\\r\\n");
		
		List<String> lineeString = new ArrayList<>(Arrays.asList(split));
		if(!lineeString.isEmpty()) {
			//rimuovo la riga di intestazione
			lineeString.remove(0);
		}
		
		if(lineeString.isEmpty()) {
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO)
					.title("CSV non valorizzato")
					.dettaglio("E2", "Non sono presenti righe da importare. La prima riga viene considerata di intestazione.")
			.exception().getResponse();
		}
		
		insertAudit("import CSV Referti", "dmarefcovid_t_referto", "Importo CSV referti", currentUser.getCfUtente(), "", req);
		
		try {
			importReferto.importReferti(lineeString, currentUser.getCfUtente());
		} catch (Exception e) {
			log.error("insertReferti: impossibile importare referti CSV ", e);
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO)
					.title("Errore import CSV")
					.dettaglio("E3", e.getMessage()).throwable(e)
			.exception().getResponse();
		}
		
		return Response.ok().build();
	}

	private boolean hasProfilo(UserLogged currentUser, Integer idProfilo) {
		List<Profilo> elencoProfilo = currentUser.getElencoProfilo();
		if(elencoProfilo!=null) {
			for (Profilo p: elencoProfilo) {
				if(idProfilo.compareTo(p.getIdProfilo()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	

	
	
}
