package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toLike;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.IstitutoScolasticoApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.IstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringSupportedResource;

public class IstitutoScolasticoApiServiceImpl extends SpringSupportedResource implements IstitutoScolasticoApi {

	@Autowired
	private IstitutiScolasticiMapper istitutiScolasticiMapper;
	
	@Override
	public Response getIstituti(String denominazione, String ordine, String provincia, String comune,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		
		List<IstitutoScolastico> result = istitutiScolasticiMapper.selectListaIstituti(toLike(denominazione),
				toLike(ordine), toLike(provincia), toLike(comune));
		
		return Response.ok(result).build();
	}

	@Override
	public Response getElencoOrdini(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		List<IstitutoScolastico> result = istitutiScolasticiMapper.selectDistinctOrdine();
		
		List<String> responseList = new ArrayList<String>();
		for(IstitutoScolastico is : result) {
			responseList.add(is.getOrdineIstituto());
		}
		
		return Response.ok(responseList).build();
	}

	@Override
	public Response getElencoProvince(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		List<IstitutoScolastico> result = istitutiScolasticiMapper.selectDistinctProvincia();
		
		List<String> responseList = new ArrayList<String>();
		for(IstitutoScolastico is : result) {
			responseList.add(is.getProvinciaIstituto());
		}
		
		return Response.ok(responseList).build();
	}

	@Override
	public Response getElencoComuni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		
		List<IstitutoScolastico> result = istitutiScolasticiMapper.selectDistinctComune();
		
		List<String> responseList = new ArrayList<String>();
		for(IstitutoScolastico is : result) {
			responseList.add(is.getComuneIstituto());
		}
		
		return Response.ok(responseList).build();
	}
	
}
