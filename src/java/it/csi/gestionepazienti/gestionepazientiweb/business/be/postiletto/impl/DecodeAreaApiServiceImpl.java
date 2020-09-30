package it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.DecodeAreaApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.EsitoDimissioni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.DecodeArea;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.DecodeAreaMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DecodeAreaApiServiceImpl  extends AuditableApiServiceImpl implements DecodeAreaApi {

	@Autowired
	DecodeAreaMapper decodeAreaMapper;
	
	@Override
	public Response getElencoDecodeArea(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<DecodeArea> retList = decodeAreaMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}
	}

}
