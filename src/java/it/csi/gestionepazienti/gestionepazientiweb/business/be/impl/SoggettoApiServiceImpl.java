package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toBoolean;
import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toInteger;
import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toLike;
import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toListLong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AbstractSoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.SoggettoApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RSoggettoIstituto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoContatto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoContattoExt;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElencoPlain;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForTrasferimento;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForTrasferimentoPlain;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoFromElencoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.ErroreBuilder;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.WebMessages;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.filter.gestionepazienti.IrideIdAdapterFilter;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AsrMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.IstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoIstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseRSoggettoIstitutoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.util.Paginazione;
import it.csi.gestionepazienti.gestionepazientiweb.util.GenericUtils;

@Service("soggettoApiServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class SoggettoApiServiceImpl extends AbstractSoggettoApiServiceImpl implements SoggettoApi {


	@Autowired
	private AsrMapper asrMapper;


	@Autowired
	IstitutiScolasticiMapper istitutiScolasticiMapper;
	
	@Autowired
	SoggettoIstitutiScolasticiMapper soggettoIstitutiScolasticiMapper;

	
	@Override
	public Response getElencoSoggettiCsv(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			UserLogged currentUser = getBeService().getCurrentUser(req);
			List<SoggettoForElenco> soggettoList = soggettoMapper.selectForElencoByIdAsr(currentUser.getIdAsr());
			insertAudit("select", "soggetto", "getElencoSoggettiCsv("+currentUser.getIdAsr()+")", currentUser.getCfUtente(), null, req);

			List<SoggettoForElencoPlain> list = new ArrayList<SoggettoForElencoPlain>();
			if (soggettoList!=null)
			{
				for (SoggettoForElenco soggettoForElenco : soggettoList) {
					SoggettoForElencoPlain sp = new SoggettoForElencoPlain(soggettoForElenco);
					list.add(sp);
				}
			}
			
			String csv = convertObjectsToCsv(list);
			String fileName = "pazienti_nome_asr.csv";
			return Response.ok(csv).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();


		} catch (Exception e) {
			log.error("Errore durante la conversione csv", e);
			return Response.serverError().entity(new Message("Errore durante la conversione csv")).build();
		}

	}
	
	@Override
	public Response getElencoSoggettiXlsx(String codiceFiscale, String cognome, String nome, String idTipoEventoStr,
			String idTipoSoggettoStr, String comuneRicovero, String comuneDomicilio, String comuneResidenza, String filter,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {

		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			List<SoggettoForVisura> soggettoList = soggettoMapper
					.selectForElencoVisuraByIdAsr(currentUser.getIdAsr(), 
							toLike(codiceFiscale), 
							toLike(cognome),
							toLike(nome), 
							toListLong(idTipoEventoStr, "-", null), 
							toInteger(idTipoSoggettoStr), 
							toLike(comuneRicovero),
							toLike(comuneDomicilio), 
							toLike(comuneResidenza),
							toLike(filter));

			insertAudit("select", "soggetto", "selectForElencoVisuraByIdAsr()", currentUser.getCfUtente(),
					null, req);


			StreamingOutput os = convertObjectsToXlsx(soggettoList);
			String fileName = "soggetti.xlsx";
			return Response.ok(os).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();

		} catch (Exception e) {
			log.error("Errore durante la conversione xlsx", e);
			return Response.serverError().entity(new Message("Errore durante la conversione xlsx")).build();
		}

	}


	@Override
	public Response getElencoSoggetti(
			// Paginazione:
			String orderby, Long rowPerPage,Long pageNumber, String descendingStr,
			
			// Filtri:
			String idTipoEventoStr,
			String idTipoSoggettoStr,
			String filter,
			
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		Date start = new Date();
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			log.info("[[SoggettoApiServiceImpl::getElencoSoggetti]] getCurrentUser "+(new Date().getTime()-start.getTime()));
			start = new Date();

			Paginazione paginazione = new Paginazione(orderby, rowPerPage, pageNumber, toBoolean(descendingStr));
			
			List<SoggettoForElenco> soggettoList = soggettoMapper.selectForElencoByIdAsrPaged(
					//Filtri
					currentUser.getIdAsr(),
					null,null,null,null,null,
					toListLong(idTipoEventoStr, "-", null), 
					toInteger(idTipoSoggettoStr),
					toLike(filter),
					
					//Paginazione
					paginazione.getOrderBy(),
					paginazione.orderAscDesc(),
					paginazione.getOffset(),
					paginazione.getLimit());
			
			log.info("[[SoggettoApiServiceImpl::getElencoSoggetti]] selectForElencoByIdAsr "+(new Date().getTime()-start.getTime()) + "ms");
			start = new Date();
			insertAudit("select", "soggetto", "selectForElencoByIdAsr("+currentUser.getIdAsr()+")", currentUser.getCfUtente(), null, req);
			log.info("[[SoggettoApiServiceImpl::getElencoSoggetti]] insertAudit "+(new Date().getTime()-start.getTime()));
			
			Long rowsNumber;
			if(paginazione.isActive()) {
				rowsNumber = 0L;
				if(soggettoList!=null && !soggettoList.isEmpty()) {
					rowsNumber = soggettoList.get(0).getTotalCount();
					if(rowsNumber==null) {
						rowsNumber = 0L;
					}
				}
				
			} else {
				rowsNumber = soggettoList==null?0L: soggettoList.size();
			}
			
			return Response.ok(soggettoList).header("Rows-Number", rowsNumber).build();
		} catch (Exception e) {
			String msg = "Errore reperimento lista soggetti";
			log.error("[[SoggettoApiServiceImpl::getElencoSoggetti]] "+msg, e);
			return ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).dettaglio("E1", msg)
					.exception().getResponse();
			//return Response.serverError().build();
		} 

	}
	
	
	@Override
	public Response getElencoSoggettiExtraAsr(
			// Paginazione:
			String orderby, Long rowPerPage,Long pageNumber, String descendingStr,
			
			// Filtri:
			String codiceFiscale,
			String cognome,
			String nome,
			Boolean cognomeexact,
			String idTipoEventoStr,
			String idTipoSoggettoStr,
			String filter,
			
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		Date start = new Date();
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			if (
				(codiceFiscale==null || codiceFiscale.isEmpty())
				&& 
				(cognome==null || cognome.isEmpty())
				)
			{
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message("Necessario indicare o cognome o codiceFiscale")).build();
				throw new WebApplicationException(resp);
			}
			if (cognomeexact==null)
				cognomeexact = false;
			if (cognome!=null && cognome.length()<=3)
				cognomeexact = true;

			if (cognome!=null  && !cognomeexact) cognome='%'+cognome+'%';
			if (nome != null) nome='%'+nome+'%';
			
			Paginazione paginazione = new Paginazione(orderby, rowPerPage, pageNumber, toBoolean(descendingStr));
			
			
			
			log.info("[[SoggettoApiServiceImpl::getElencoSoggettiExtraAsr]] STOCA CODICE FISCALE  --> " + codiceFiscale + " cognome  --> " + cognome + " nome  --> " + nome + " cognomeexact  --> " + cognomeexact);
			List<SoggettoForElenco> soggettoList = soggettoMapper.selectForElencoByIdAsrPaged(
					//Filtri
					null, //id_asr
					codiceFiscale,cognome,nome,cognomeexact,null,
					toListLong(idTipoEventoStr, "-", null), 
					toInteger(idTipoSoggettoStr),
					toLike(filter),
					
					//Paginazione
					paginazione.getOrderBy(),
					paginazione.orderAscDesc(),
					paginazione.getOffset(),
					paginazione.getLimit()
					
					);
			insertAudit("select", "soggetto", "selectForElencoByCFCognomeNome("+codiceFiscale+","+ 
					cognome+", "+
					nome+ ", "+
					cognomeexact+" )", currentUser.getCfUtente(), null, req);
			
			Long rowsNumber;
			if(paginazione.isActive()) {
				rowsNumber = 0L;
				if(soggettoList!=null && !soggettoList.isEmpty()) {
					rowsNumber = soggettoList.get(0).getTotalCount();
					if(rowsNumber==null) {
						rowsNumber = 0L;
					}
				}
				
			} else {
				rowsNumber = soggettoList==null?0L:soggettoList.size();
			}
			
			
			return Response.ok(soggettoList).header("Rows-Number", rowsNumber).build();
		} catch (Exception e) {
			String msg = "Errore reperimento lista soggetti extra asr";
			log.error("[[SoggettoApiServiceImpl::getElencoSoggettiExtraAsr]] "+msg, e);
			return ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).dettaglio("E1", msg)
					.exception().getResponse();
			//return Response.serverError().build();
		} 

	}
	
	@Override
	public Response getElencoSoggettiQuarantenaCsv(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			UserLogged currentUser = (UserLogged) req.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			
			Long idAsr = currentUser.getIdAsr();
			
			

			List<SoggettoForTrasferimento> soggettoList = soggettoMapper.
					selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEvento(
							//Filtri
							idAsr, null, tipiEventoQuarantena, null,
							
							//Paginazione 
							null, null, null, -1L);
			
			insertAudit("select", "soggetto", "getElencoSoggettiQuarantenaCsv("+idAsr+")", currentUser.getCfUtente(), null, req);

			List<SoggettoForTrasferimentoPlain> list = new ArrayList<SoggettoForTrasferimentoPlain>();
			if (soggettoList!=null)
			{
				for (SoggettoForTrasferimento soggettoForElenco : soggettoList) {
					SoggettoForTrasferimentoPlain sp = new SoggettoForTrasferimentoPlain(soggettoForElenco);
					list.add(sp);
				}
			}
			
			String csv = convertObjectsToCsv(list);
			String fileName = "pazienti_quarantena.csv";
			return Response.ok(csv).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();


		} catch (Exception e) {
			log.error("Errore durante la conversione csv", e);
			return Response.serverError().entity(new Message("Errore durante la conversione csv")).build();
		}

	}
	
	

	private static final Integer[] tipiEventoQuarantena = new Integer[] {
			DecorsoApiServiceImpl.ID_TIPO_EVENTO_QUARANTENA_DOMIC, 
			DecorsoApiServiceImpl.ID_TIPO_EVENTO_QUARANTENA_EXTRA_DOMIC,
			DecorsoApiServiceImpl.ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_DOMIC,
			DecorsoApiServiceImpl.ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_EXTRA_DOMIC,
	};
	
	private static final Integer[] tipiEventoTrasferiti = new Integer[] {
			DecorsoApiServiceImpl.ID_TIPO_EVENTO_TRASFERITO_INTERNO_STRUTTURA, 
			DecorsoApiServiceImpl.ID_TIPO_EVENTO_TRASFERITO_ESTERNO_STRUTTURA,
	};
	
	
	@Override
	public Response getElencoSoggettiQuarantena(
			// Paginazione:
			String orderby, Long rowPerPage,Long pageNumber, String descendingStr,
			
			// Filtri: 
			String filter,
			
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			UserLogged currentUser = (UserLogged) req.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			
			Long idAsr = currentUser.getIdAsr();
			
			Paginazione paginazione = new Paginazione(orderby, rowPerPage, pageNumber, toBoolean(descendingStr));

			List<SoggettoForTrasferimento> soggettoList = soggettoMapper.
					selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEvento(
							//Filtri
							idAsr, null, tipiEventoQuarantena, toLike(filter),
							
							//Paginazione
							paginazione.getOrderBy(),
							paginazione.orderAscDesc(),
							paginazione.getOffset(),
							paginazione.getLimit()
							);
			
			
			insertAudit("select", "soggetto", "getElencoSoggettiQuarantena("+idAsr+")", currentUser.getCfUtente(), null, req);
			
			
			Long rowsNumber;
			if(paginazione.isActive()) {
//				rowsNumber = soggettoMapper.selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEventoCount(
//						idAsr, null, tipiEventoQuarantena, toLike(filter));
				
				rowsNumber = 0L;
				if(soggettoList!=null && !soggettoList.isEmpty()) {
					rowsNumber = soggettoList.get(0).getTotalCount();
					if(rowsNumber==null) {
						rowsNumber = 0L;
					}
				}
				
				
			} else {
				rowsNumber = soggettoList==null?0L: soggettoList.size();
			}
			
			return Response.ok(soggettoList).header("Rows-Number", rowsNumber).build();
		} catch (Exception e) {
			String msg = "Errore reperimento lista soggetti quarantena";
			log.error("[[SoggettoApiServiceImpl::getElencoSoggettiQuarantena]] "+msg, e);
			return ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).dettaglio("E1", msg)
					.exception().getResponse();
		}

	}
	
	@Override
	public Response getElencoSoggettiSegnalati(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			UserLogged currentUser = (UserLogged) req.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			
			Long idAsr = currentUser.getIdAsr();
			
			

			List<SoggettoForVisura> soggettoList = soggettoMapper.
					selectForElencoVisuraByIdTipoEventoByIdAsr(idAsr, new Integer[] {DecorsoApiServiceImpl.ID_TIPO_SEGNALAZIONE});
			
			insertAudit("select", "soggetto", "getElencoSoggettiQuarantena("+idAsr+")", currentUser.getCfUtente(), null, req);
			
			return Response.ok(soggettoList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}
	
	@Override
	public Response getElencoSoggettiTrasferiti(
			// Paginazione:
			String orderby, Long rowPerPage,Long pageNumber, String descendingStr,
						
			// Filtri: 
			String filter,
			
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			UserLogged currentUser = (UserLogged) req.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			
			Long idAsr = currentUser.getIdAsr();
			
			if (GenericUtils.currentUserHasProfilo(currentUser, GenericUtils.ID_PROFILO_SUPERUSER_PAZIENTI)) {
				idAsr=-1L;
			}
			
			Paginazione paginazione = new Paginazione(orderby, rowPerPage, pageNumber, toBoolean(descendingStr));
			

			List<SoggettoForTrasferimento> soggettoList = soggettoMapper.selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEvento(
					
					//Filtri
					idAsr, null, 
					tipiEventoTrasferiti, 
					toLike(filter),
					
					//Paginazione
					paginazione.getOrderBy(),
					paginazione.orderAscDesc(),
					paginazione.getOffset(),
					paginazione.getLimit()
					
					);
			
			insertAudit("select", "soggetto", "selectForElencoTrasferitiByIdAsr("+idAsr+")", currentUser.getCfUtente(), null, req);
			
			
			Long rowsNumber;
			if(paginazione.isActive()) {
//				rowsNumber = soggettoMapper.selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEventoCount(
//						idAsr, null, 
//						tipiEventoTrasferiti, 
//						toLike(filter)
//						);
				
				rowsNumber = 0L;
				if(soggettoList!=null && !soggettoList.isEmpty()) {
					rowsNumber = soggettoList.get(0).getTotalCount();
					if(rowsNumber==null) {
						rowsNumber = 0L;
					}
				}
				
			} else {
				rowsNumber = soggettoList==null?0L: soggettoList.size();
			}
			
			
			
			
			return Response.ok(soggettoList).header("Rows-Number", rowsNumber).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}
	
	@Override
	public Response getSoggettoByIdSoggetto(Long idSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);

			Long idAsr = currentUser.getIdAsr();
			
			// richiesta visibilita' completa per i dettagli
			//if (GenericUtils.currentUserHasProfilo(currentUser, 6))
			idAsr=-1L;

			
			SoggettoForDettaglio dettaglio = getDettaglioSoggettoByIdSoggettoIdAsr(idSoggetto, idAsr);
			
			
			insertAudit("select", "soggetto tampone decorso", "selectForElencoByIdSoggetto("+idSoggetto+")", currentUser.getCfUtente(), null, req);

			
			return Response.ok(dettaglio).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	private SoggettoForDettaglio getDettaglioSoggettoByIdSoggettoIdAsr(Long idSoggetto, Long idAsr) {
		SoggettoForElenco soggettoForElenco = soggettoMapper.selectForElencoByIdSoggetto(idSoggetto, idAsr);
		if (soggettoForElenco==null) {
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
		List<TamponeForElenco> elencoTampone = tamponeMapper.selectForElencoByIdSoggetto(idSoggetto);
		List<DecorsoForElenco> elencoDecorso = decorsoMapper.selectForElencoByIdSoggetto(idSoggetto);
		List<SoggettoAura> elencoSoggettoAura = soggettoAuraMapper.selectByIdSoggetto(idSoggetto);
		List<SoggettoContattoExt> elencoContattiDa = soggettoContattoMapper.selectContattiDaA(idSoggetto, null);
		List<SoggettoContattoExt> elencoContattiA = soggettoContattoMapper.selectContattiDaA(null, idSoggetto);
			
		List<IstitutoScolastico> elencoIstitutoScolastico = istitutiScolasticiMapper.selectBySoggettoId(idSoggetto);	
		
		
		Long idAura = null;
		Medico medico = null;
		//Dovrebbe esserci al piu un solo soggettoAura ma il db ne permette piu di uno. scelgo il primo e loggo
		if (elencoSoggettoAura!=null && !elencoSoggettoAura.isEmpty())
		{
			if (elencoSoggettoAura.size()>1)
				log.warn("Trovato soggetto con "+elencoSoggettoAura.size()+"  id_aura associati!! "+idSoggetto);
			idAura = elencoSoggettoAura.get(0).getIdAura();
			List<Medico> elencoMedico = medicoMapper.selectByIdAuraSogg(idAura);
			if (elencoMedico!=null && !elencoMedico.isEmpty())
			{
				if (elencoMedico.size()>1)
					log.warn("Trovato soggetto con "+elencoMedico.size()+"  medici associati!! "+idSoggetto);
				medico = elencoMedico.get(0);
			}
		}
		
		SoggettoForDettaglio dettaglio = new SoggettoForDettaglio(soggettoForElenco, elencoTampone, elencoDecorso, medico);
		dettaglio.setIdAura(idAura);
		
		dettaglio.setElencoIstitutiScolastico(elencoIstitutoScolastico);
		dettaglio.setElencoContattiDa(elencoContattiDa);
		dettaglio.setElencoContattiA(elencoContattiA);
		
		return dettaglio;
	}

	
	@Override
	public Response insertSoggetto(SoggettoForDettaglio soggetto, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			if (soggetto.getIdAsr()==null) { 
				soggetto.setIdAsr(currentUser.getIdAsr());
			}
			
			//controllo se si tratta di caso importato
			soggetto = calcolaCasoImportato(soggetto);
			
			/**
			 * Raffa: gestito il nuovo caso di nuovo soggetto senza asr e utente loggato senza asr
			 * quindi in questo caso prendo la prima struttura della lista 
			 */
			//boolean insertSoggettoDaUsca =  soggetto.getCodiceApplicazione()!=null && soggetto.getCodiceApplicazione().equalsIgnoreCase("USCA");
			
			//if(!insertSoggettoDaUsca) {
				
				if (currentUser.getIdAsr()!=null && soggetto.getIdAsr()!=null &&
						currentUser.getIdAsr()!=-1 && !currentUser.getIdAsr().equals(soggetto.getIdAsr())) {
					Response resp = Response.status(Status.FORBIDDEN).build();
					throw new WebApplicationException(resp);
				}
			//}
			
			// not duplicate CF for same idASR
			if	 (soggetto.getCodiceFiscale()!=null && !soggetto.getCodiceFiscale().isEmpty())
			{
				List<Soggetto> soggetti = soggettoMapper.selectForElencoByCodiceFiscaleIdAsr(soggetto.getCodiceFiscale(), -1L /*soggetto.getIdAsr()*/);
				if (soggetti!=null && !soggetti.isEmpty())
				{
					log.info("Identificato soggetto duplicato "+soggetto.getCodiceFiscale()+",idAsr="+soggetto.getIdAsr());
					throw new WebApplicationException( 
							Response.status(Status.CONFLICT).entity(soggetti).build()
							);
				}
			}
			else
				log.info("Inserimento soggetto senza codice fiscale");
			
			/**
			 * Domenico: generalizzo i casi senza currentUser.idAsr
			 */
			if(currentUser.getIdAsr()==null) {
				// Inserimento SENZA IdAsr
				
				soggettoMapper.insertSenzaAsr(soggetto) ;
				
				/**
				 * inserisco l'asr
				 */
				Integer idEnte = soggettoMapper.selectEnteByIdSoggetto(soggetto.getIdSoggetto());
				Long idAsr = asrMapper.selectByIdEnte(idEnte.longValue() /*, soggetto.getAslDomicilio()*/);
				
				soggetto.setIdAsr(idAsr);
				soggettoMapper.updateAsrByPrimaryKey(soggetto);

			} else {
				// Inserimento CON IdAsr
				
				soggettoMapper.insert(soggetto);
			}
			
			
			// RM: Gestione personale scolastico e studenti
			Date now = new Date();
			if(soggetto.getElencoIstitutiScolastico()!=null && !soggetto.getElencoIstitutiScolastico().isEmpty()) {
				
				for (IstitutoScolastico istitutoScolastico : soggetto.getElencoIstitutiScolastico()) {
					
					Integer idIstituto = istitutoScolastico.getIdIstituto();
					
					log.info("Inserimento associazione soggetto - istituto :" +soggetto.getIdSoggetto() + " - " + idIstituto);
					
					RSoggettoIstituto rsoggettoIstituto = new RSoggettoIstituto();
					rsoggettoIstituto.setIdSoggetto(soggetto.getIdSoggetto().intValue());
					rsoggettoIstituto.setIdIstituto(idIstituto);
					rsoggettoIstituto.setValiditaInizio(now);
					rsoggettoIstituto.setDataCreazione(now);
					rsoggettoIstituto.setDataModifca(now);
					rsoggettoIstituto.setUtenteOperazione(currentUser.getCfUtente());
										
					soggettoIstitutiScolasticiMapper.insert(rsoggettoIstituto);
				
				}				
				
			}
			
			// aggiungo sul soggetto le info aggiornate post inserimento soggetto
			List<IstitutoScolastico> elencoIstitutoScolastico = istitutiScolasticiMapper.selectIstitutiValidiBySoggettoId(soggetto.getIdSoggetto(), now);
			soggetto.setElencoIstitutiScolastico(elencoIstitutoScolastico);
				
			if (soggetto.getIdAura()!=null)
			{
				SoggettoAura sa = new SoggettoAura();
				sa.setIdAura(soggetto.getIdAura());
				sa.setIdSoggetto(soggetto.getIdSoggetto());
				log.info("Inserimento soggetto con associazione aura "+soggetto.getIdSoggetto());
				soggettoAuraMapper.insert(sa);
				try {
					
					// Aggiornamento del medico associato al soggetto, si legge da Aura
					aggiornamentoMediciPerSoggetto(soggetto,soggetto.getIdAura(), req, currentUser);
				} catch (Exception e) {
					log.error("Errore durante il censimento del medico per idSoggetto "+soggetto.getIdSoggetto(), e);
				}
			
			}
			
			
			
			
			insertAudit("insert", "soggetto", "insert("+soggetto.getIdSoggetto()+")", currentUser.getCfUtente(), (Soggetto)soggetto, req);

			/**
			 * RM modifica per permettere di avere il soggetto post inserimento (vedi ins. soggetto da Usca)
			 * 
			 */
			SoggettoForDettaglio dettaglio = getDettaglioSoggettoByIdSoggettoIdAsr(soggetto.getIdSoggetto(), soggetto.getIdAsr());
			
			return Response.ok(dettaglio).build();
		} catch (Exception e) {
			log.error("insertSoggetto generic error:", e);
			if (e instanceof WebApplicationException) {
				throw (WebApplicationException)e;
			}
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}
	
	@Override
	public Response updateSoggetto(SoggettoForDettaglio soggetto, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			if (soggetto.getIdAsr()==null)
				soggetto.setIdAsr(currentUser.getIdAsr());
			
//			log.info("| Inizio servizio");
			
			
			//controllo se si tratta di caso importato
			soggetto = calcolaCasoImportato(soggetto);
			
			
//			if (currentUser.getIdAsr()!=-1 && soggetto.getIdAsr() != currentUser.getIdAsr())
//			{
//				Response resp = Response.status(Status.FORBIDDEN).build();
//				throw new WebApplicationException(resp);
//			}
			// check format data  NON SERVE PIU DATE GESTITE COME TALI
//			String date = soggetto.getDataNascitaStr();
//			try {
//				if (date!=null)
//				{
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//					Date data = sdf.parse(date);
//					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
//					soggetto.setDataNascitaStr(sdf2.format(data));
//				}
//			} catch (ParseException e) {
//			}
//

			
			if (soggetto.getCodiceFiscale() != null && !soggetto.getCodiceFiscale().isEmpty()) {

				soggetto.setCodiceFiscale(soggetto.getCodiceFiscale().toUpperCase());
				List<Soggetto> listSoggettoForElenco = soggettoMapper
						.selectForElencoByCodiceFiscaleIdAsr(soggetto.getCodiceFiscale(), -1L /*soggetto.getIdAsr()*/);
				if (listSoggettoForElenco != null && !listSoggettoForElenco.isEmpty()) {

					Soggetto soggettoForElenco = listSoggettoForElenco.get(0);
					if (soggettoForElenco != null
							&& soggettoForElenco.getCodiceFiscale().equals(soggetto.getCodiceFiscale())
							&& !soggettoForElenco.getIdSoggetto().equals(soggetto.getIdSoggetto())) {
						log.error("Il soggetto che si intende aggiornare e gia presente. ");
						throw new WebApplicationException(Response.status(410).entity(soggetto).build());
					}
				}
			}
//			log.info("| Cod fisca ok");
			if (soggetto.getCognome()!=null) {
				soggetto.setCognome(soggetto.getCognome().toUpperCase());
			}
			if (soggetto.getNome()!=null) {
				soggetto.setNome(soggetto.getNome().toUpperCase());
			}
			if (soggetto.getIndirizzoDomicilio()!=null) {
				soggetto.setIndirizzoDomicilio(soggetto.getIndirizzoDomicilio().toUpperCase());
			}
			if (soggetto.getTelefonoRecapito()!=null) {
				soggetto.setTelefonoRecapito(soggetto.getTelefonoRecapito().toUpperCase());				
			}
			
//			log.info("| Inizio Update del soggetto");
			int ret = soggettoMapper.updateByPrimaryKey(soggetto);
//			log.info("| Update soggetto ok");
			
			// RM: gestione relazione soggetto e istituto scolastico, faccio come per soggettoAUra
			Date now = new Date();
			log.info("Inzio update soggetto - istituto ");
			List<IstitutoScolastico> elencoIstitutiScolasticiReq = soggetto.getElencoIstitutiScolastico(); 
			List<IstitutoScolastico> elencoIstitutiScolastici = istitutiScolasticiMapper.selectBySoggettoId(soggetto.getIdSoggetto());
			//1- Cancello tutte le relazioni
			if(elencoIstitutiScolastici != null && !elencoIstitutiScolastici.isEmpty()) {
				
				RSoggettoIstituto rSoggettoIstituto = new RSoggettoIstituto();
				rSoggettoIstituto.setDataCancellazione(now);
				rSoggettoIstituto.setDataModifca(now);
				rSoggettoIstituto.setValiditaFine(now);
				rSoggettoIstituto.setUtenteOperazione(currentUser.getCfUtente());
				rSoggettoIstituto.setIdSoggetto(soggetto.getIdSoggetto().intValue());
				soggettoIstitutiScolasticiMapper.updateBySoggettoId(rSoggettoIstituto);
			}
			
			log.info("Step 1 update soggetto - istituto fine ");
			//2- reinserisco le relazioni
			log.info("Step 2 update soggetto - istituto inizio ");
			if(elencoIstitutiScolasticiReq != null && !elencoIstitutiScolasticiReq.isEmpty()) {
				
				for (IstitutoScolastico istitutoScolastico : elencoIstitutiScolasticiReq) {
					
					Integer idIstituto = istitutoScolastico.getIdIstituto();
					
					log.info("Inserimento associazione soggetto - istituto :" +soggetto.getIdSoggetto() + " - " + idIstituto);
					
					RSoggettoIstituto rsoggettoIstituto = new RSoggettoIstituto();
					rsoggettoIstituto.setIdSoggetto(soggetto.getIdSoggetto().intValue());
					rsoggettoIstituto.setIdIstituto(idIstituto);
					rsoggettoIstituto.setValiditaInizio(now);
					rsoggettoIstituto.setDataCreazione(now);
					rsoggettoIstituto.setDataModifca(now);
					rsoggettoIstituto.setUtenteOperazione(currentUser.getCfUtente());
										
					soggettoIstitutiScolasticiMapper.insert(rsoggettoIstituto);
				
				}				
				
			}
			log.info("Step 2 update soggetto - istituto fine ");
			
			
			// per aggiornare idAura lo cancello e lo reinserisco ogni volta che viene fatto update
//			log.info("| Inizio servizio delete");
			soggettoAuraMapper.deleteByIdSoggetto(soggetto.getIdSoggetto());
//			log.info("| Delete soggetto ok");
			
			Long idAura = new Long(0);
			if(soggetto.getIdAura() != null) {
				log.info("| Soggetto.idAura not null");
				idAura = soggetto.getIdAura();
			}else {
				log.info("| Soggetto.idAura null, rileggo da aura");
				
				List<SoggettoFromElencoAura> listaSoggettoAura = null;
				if(soggetto.getCodiceFiscale()!=null && !soggetto.getCodiceFiscale().isEmpty()) {
					
					listaSoggettoAura = findProfiliAnagrafici(soggetto.getCodiceFiscale().toUpperCase());
				} else if(soggetto.getNome()!=null && soggetto.getCognome()!=null && soggetto.getDataNascita()!=null){
					listaSoggettoAura = findProfiliAnagraficiByNomeECognome(soggetto.getNome(), soggetto.getCognome(),
							soggetto.getDataNascita().toString());
				} else {
					log.error("Il soggetto che si intende aggiornare non possiede sufficienti dati per l'aggiornamento. (CF, nome, cognome, data_nascita) ");
					throw new WebApplicationException(Response.status(410).entity(soggetto).build());
				}
				
				
				
//				log.info("| listaSoggettoAura size: "+ (listaSoggettoAura!=null ? listaSoggettoAura.size() : null) );
				
				if(listaSoggettoAura!=null && !listaSoggettoAura.isEmpty()) {
//					log.info("| listaSoggettoAura not null ");
					SoggettoFromElencoAura soggAura = listaSoggettoAura.get(0);
					if(soggAura!=null && soggAura.getProfiloAnagrafico()!=null) {
						idAura = soggAura.getProfiloAnagrafico().longValue();
						log.info("Rileggo da AURA --> il soggetto: " + soggetto.getIdSoggetto() + " ha idAura: " + idAura);
					}
					
				}else {
					log.info(" Per il cf " + soggetto.getCodiceFiscale() + " non sono state trovate occorrenze su Aura ");
				}
			}
//			log.info("| Dopo soggetto");
			

			if(idAura!=null && idAura!=0)
			{
				
				SoggettoAura sa = new SoggettoAura();
				sa.setIdAura(idAura);
				sa.setIdSoggetto(soggetto.getIdSoggetto());
				log.info("Inserimento soggetto con associazione aura durante update");
				soggettoAuraMapper.insert(sa);
				try {
					aggiornamentoMediciPerSoggetto(soggetto,soggetto.getIdAura(), req, currentUser);
				} catch (Exception e) {
					log.error("Errore durante il censimento del medico per idSoggetto "+soggetto.getIdSoggetto(), e);
				}
			
			}

			log.info("GESTIONE VISIBILITA sog_idsog: "+soggetto.getIdSoggetto());
			log.info("GESTIONE VISIBILITA sog_idasr: "+soggetto.getIdAsr());
			log.info("GESTIONE VISIBILITA use_idasr: "+currentUser.getIdAsr());
			int aggret=aggiornavisibilita(soggetto, currentUser);
			log.info("GESTIONE VISIBILITA aggret: "+aggret);
			
			insertAudit("update", "soggetto", "updateByPrimaryKey("+soggetto.getIdSoggetto()+")", currentUser.getCfUtente(), (Soggetto)soggetto, req);

			return Response.ok().build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	@Override
	public Response inserisciContatto(
			SoggettoContatto soggettoContatto,
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		
		
		
		if(soggettoContatto==null) {
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO).dettaglio("E1", "soggettoContatto null")
			.exception().getResponse();
		}
		
		
		if(soggettoContatto.getContattoTipoId()==null) {
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO).title("Tipo contatto non valorizzato").dettaglio("E2", "soggettoContatto.contattoTipoId null")
			.exception().getResponse();
		}
		
		if(soggettoContatto.getIdSoggettoA()==null) {
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO).title("Paziente A non valorizzato").dettaglio("E2", "soggettoContatto.idSoggettoA null")
			.exception().getResponse();
		}
		
		if(soggettoContatto.getIdSoggettoDa()==null) {
			return ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO).title("Paziente Da non valorizzato").dettaglio("E2", "soggettoContatto.idSoggettoDa null")
			.exception().getResponse();
		}
		
		UserLogged currentUser = getBeService().getCurrentUser(req);
		
		Date now = new Date();
		soggettoContatto.setValiditaInizio(now);
		soggettoContatto.setDataCreazione(now);
		soggettoContatto.setDataModifca(now);
		soggettoContatto.setUtenteOperazione(currentUser.getCfUtente());
		soggettoContatto.setIdSoggettoContatti(null);
		
		
		try {
			int insert = soggettoContattoMapper.insert(soggettoContatto);
			log.info("inserisciContatto "+ insert);
		} catch (RuntimeException re) {
			log.error("Errore inserimento relazione soggetto", re);
			return ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).dettaglio("E1", "Errore inserimento relazione soggetto").throwable(re)
					.exception().getResponse();
		}
		
		return Response.status(Status.CREATED).entity(soggettoContatto).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	/**
	 * Imposta il flag del caso importato (si/no/non noto) in base alla combinazione di stato e regione di contagio
	 * @param <R>
	 * @param soggetto
	 * @return
	 */
	private <R extends Soggetto> R calcolaCasoImportato(R soggetto) {
		
		if(soggetto!=null && soggetto.getStatoContagio()!=null && !soggetto.getStatoContagio().equals("IT")) {
			soggetto.setCasoImportato("SI");
		}
		
		if(soggetto!=null && soggetto.getStatoContagio()!=null && soggetto.getStatoContagio().equals("IT")) {
			
			if(soggetto.getRegioneContagio()!=null && soggetto.getRegioneContagio().equalsIgnoreCase("Piemonte")) {
				soggetto.setCasoImportato("NO");
			} else {
				soggetto.setCasoImportato("SI");
			}
		}
		
		return soggetto;
	}
}
