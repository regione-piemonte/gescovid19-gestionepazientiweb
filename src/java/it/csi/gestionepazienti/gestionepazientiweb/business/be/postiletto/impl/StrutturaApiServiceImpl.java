package it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl;

import java.util.ArrayList;
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
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.StrutturaApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.AreaMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.EnteMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.StrutturaMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StrutturaApiServiceImpl  extends AuditableApiServiceImpl implements StrutturaApi {


	
	@Autowired
	AreaMapper areaMapper ;
	
	@Autowired
	StrutturaMapper strutturaMapper;

	
	public Response getElencoAreeByIdStruttura(String idStruttura, SecurityContext securityContext, 
			 HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			List<Area> retList = areaMapper.selectByIdStrutturaValid(idStruttura);
			return Response.ok(retList).build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}
	

	@Override
	public Response getElencoStrutturaRicettiva(SecurityContext securityContext, 
			 HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			List<Struttura> list = strutturaMapper.selectElencoStrutturaWithAreaValid();
			// 'Ricettiva' && allStrutture[i].natura!='RSA' && allStrutture[i].natura!='Caserma'
			List<Struttura> retList = new ArrayList<Struttura>();
			if (list!=null)
			{
				for (Struttura struttura : list) {
					if (struttura.getNatura().equalsIgnoreCase("Ricettiva")
							||
							struttura.getNatura().equalsIgnoreCase("RSA")
							||
							struttura.getNatura().equalsIgnoreCase("Caserma")	
						)
					{
						retList.add(struttura);
					}
				}
			}
			
			
			return Response.ok(retList).build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}
}
