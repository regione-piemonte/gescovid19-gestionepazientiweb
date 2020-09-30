package it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl;

import java.util.List;

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
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.EnteApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.EnteMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.StrutturaMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EnteApiServiceImpl  extends AuditableApiServiceImpl implements EnteApi {

	@Autowired
	EnteMapper enteMapper ;
	
	@Autowired
	StrutturaMapper strutturaMapper ;
	
	@Override
	public Response getElencoEnte(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<Ente> retList = enteMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco ente ", e);
			return Response.serverError().build();
		}
	}
	
	public Response getElencoStrutture(Integer idEnte, SecurityContext securityContext, 
			 HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			List<Struttura> retList = strutturaMapper.selectByIdEnte(idEnte);
			return Response.ok(retList).build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}
	

}
