package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuraApi;

@Service("auraApiServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AuraApiServiceImpl extends AuditableApiServiceImpl implements AuraApi {

	@Autowired
	AuraComponent auraComponent;

	@Override
	public Response findAnagraficaById(String id, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		return auraComponent.findAnagraficaById(id, securityContext, httpHeaders, req);
	}

	@Override
	public Response getSoggettiAuraByCF(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {

		return auraComponent.getSoggettiAuraByCF(codiceFiscale, securityContext, httpHeaders, req);

	}

	@Override
	public Response getSoggettiAuraByNomeCognomeData(String nome, String cognome, String giorno, String mese,
			String anno, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest req) {
		return auraComponent.getSoggettiAuraByNomeCognomeData(nome, cognome, giorno, mese, anno, securityContext,
				httpHeaders, req);
	}

}
