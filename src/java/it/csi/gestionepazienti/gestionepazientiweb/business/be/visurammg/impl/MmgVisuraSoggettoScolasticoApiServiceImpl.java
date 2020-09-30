package it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AbstractSoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg.MmgVisuraSoggettoScolasticoApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RSoggettoPersonaleScolasticoIstituto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TipoSoggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForPersonaleScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.ErroreBuilder;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.WebMessages;
import it.csi.gestionepazienti.gestionepazientiweb.dto.personalescolastico.SoggettoPersonaleScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.Referto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.RefertoDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.IstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.RSoggettoPersonaleScolasticoIstitutoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TipoSoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.personalescolastico.generated.SoggettoPersonaleScolasticoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoDettaglioMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoMapper;;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MmgVisuraSoggettoScolasticoApiServiceImpl extends AbstractSoggettoApiServiceImpl implements MmgVisuraSoggettoScolasticoApi {

	private static final String COD_TIPO_RICHIESTA_05 = "05";


	@Autowired
	TipoSoggettoMapper tipoSoggettoMapper;
	
	@Autowired
	RefertoMapper refertoMapper;
	
	@Autowired
	IstitutiScolasticiMapper istitutiScolasticiMapper;
	
	@Autowired
	RSoggettoPersonaleScolasticoIstitutoMapper soggScolasticoIstitutoMapper;
	
	@Autowired
	RefertoDettaglioMapper refertoDettaglioMapper;
	
	@Autowired
	SoggettoPersonaleScolasticoMapper soggettoPersonaleScolasticoMapper;
	
	@Override
	public Response getElencoSoggettiScolasticiByMedico(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);

			Long idMedico = null;
			
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}
			
			List<SoggettoForPersonaleScolastico> soggettoList = soggettoPersonaleScolasticoMapper.selectSoggettiPersonaleScolasticoForVisuraMmgByMedico(idMedico, currentUser.getCfUtente());
			insertAudit("select", "soggetto_personale_scolastico", "selectSoggettiPersonaleScolasticoForVisuraMmgByMedico()", currentUser.getCfUtente(),
					null, req);
			
			return Response.ok(soggettoList).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}

	}
	
	@Override
	public Response getElencoSoggettiScolasticiForEnte(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			log.info("Inizio esecuzione getElencoSoggettiScolasticiForEnte");
			UserLogged currentUser = getBeService().getCurrentUser(req);

			log.info("currentUser -> "+(currentUser!=null ? currentUser.getCfUtente() : "NULL" ));
			
			List<SoggettoForPersonaleScolastico> soggettoList = soggettoPersonaleScolasticoMapper.selectSoggettiPersonaleScolasticoForVisuraMmgForEnte(currentUser.getCfUtente());
			insertAudit("select", "soggetto_personale_scolastico", "selectSoggettiPersonaleScolasticoForVisuraMmgForEnte()", currentUser.getCfUtente(),
					null, req);
			
			return Response.ok(soggettoList).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}

	}

	@Override
	public Response getSoggettoScolasticoByIdSoggetto(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			if(id == null || id == 0) {
				throw new Exception("id soggetto obbligatorio");
			}
			UserLogged currentUser = getBeService().getCurrentUser(req);			

			Long idMedico = null;
			
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}
			
			SoggettoForPersonaleScolastico soggetto = soggettoPersonaleScolasticoMapper.selectSoggettoPersonaleScolasticoForVisuraMmgByIdSoggetto(id, idMedico, currentUser.getCfUtente());
			if (soggetto==null)
			{
				log.warn("Soggetto scolastico:"+id+" non presente");
				throw new WebApplicationException(ErroreBuilder.from(WebMessages.ENTITA_NON_TROVATA)
						.title("Soggetto personale scolastico non trovato.")
						.dettaglio("E1", "Soggetto personale scolastico non trovato con id " + id).exception()
						.getResponse());
			}

			insertAudit("select", "SoggettoForPersonaleScolastico ID", "select("+soggetto.getIdSoggetto()+")", currentUser.getCfUtente(), null, req);
			
			//completo l'informazione del soggetto con i dati dell'istituto scolastico
			Date now = new Date();
			List<IstitutoScolastico> elencoIstitutoScolastico = istitutiScolasticiMapper.selectIstitutiValidiBySoggettoScolasticoId(soggetto.getIdSoggetto(), now);
			soggetto.setElencoIstitutiScolastico(elencoIstitutoScolastico);
			
			return Response.ok(soggetto).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
		
	}
	

	@Override
	public Response getDecodificaTipoSoggettoScolastico(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		log.info("Recupero decodifiche Tipo soggetto Personale Scolastico");
		List<TipoSoggetto> elencoDecodifiche = tipoSoggettoMapper.selectAll();
		//s    select * from d_tipo_soggetto dts where desc_tipo_soggetto like '%personale scolastico%'
		elencoDecodifiche.removeIf(new Predicate<TipoSoggetto>() {
			@Override
			public boolean test(TipoSoggetto t) {
				return !(t.getDescTipoSoggetto().toLowerCase().contains("personale scolastico"));
			}
		});
		
		return Response.ok(elencoDecodifiche).build();
	}

	@Override
	public Response insertSoggettoScolastico(SoggettoForPersonaleScolastico soggettoScolastico, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			if (soggettoScolastico.getNome()==null || soggettoScolastico.getNome().isEmpty()
					|| soggettoScolastico.getCognome()==null || soggettoScolastico.getCognome().isEmpty()
					|| soggettoScolastico.getCodiceFiscale()==null || soggettoScolastico.getCodiceFiscale().isEmpty()
					|| soggettoScolastico.getIdTipoSoggetto()==null)
			{
				StringBuilder message = new StringBuilder();
				if (soggettoScolastico.getNome() == null || soggettoScolastico.getNome().isEmpty()) {
					message.append("Nome obbligatorio");
				}
				if (soggettoScolastico.getCognome()==null || soggettoScolastico.getCognome().isEmpty()) {
					message.append("Cognome obbligatorio");
				}
				if (soggettoScolastico.getCodiceFiscale()==null || soggettoScolastico.getCodiceFiscale().isEmpty()) {
					message.append("codiceFiscale obbligatorio");
				}
				if (soggettoScolastico.getIdTipoSoggetto()==null) {
					message.append("idTipoSoggetto obbligatorio");
				}
				
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message(message.toString())).build();
				throw new WebApplicationException(resp);
			}
			
			
			SoggettoPersonaleScolastico soggetto = new SoggettoPersonaleScolastico();
			soggetto.setNome(soggettoScolastico.getNome().toUpperCase());
			soggetto.setCognome(soggettoScolastico.getCognome().toUpperCase());
			soggetto.setCodiceFiscale(soggettoScolastico.getCodiceFiscale());
			soggetto.setDataNascita(soggettoScolastico.getDataNascita());
			soggetto.setAslDomicilio(soggettoScolastico.getAslDomicilio());
			soggetto.setAslResidenza(soggettoScolastico.getAslResidenza());
			soggetto.setUtenteOperazione(currentUser.getCfUtente());
			soggetto.setDataCreazione(new Date());
			soggetto.setDataModifica(new Date());
			soggetto.setTelefonoRecapito(soggettoScolastico.getTelefonoRecapito());
			soggetto.setEmail(soggettoScolastico.getEmail());
			soggetto.setIdTipoSoggetto(soggettoScolastico.getIdTipoSoggetto().intValue());
			
			// Inserisco i dati dei medici (corrente e aura)
			Long idAura = soggettoScolastico.getIdAura();
			if (idAura!=null)
			{
				try {				
					// Aggiornamento del medico associato al soggetto, si legge da Aura
					Medico medicoAura = aggiornamentoMediciPerSoggetto(soggettoScolastico,idAura, req, currentUser);
					if (medicoAura!=null)
						soggetto.setIdMedico(medicoAura.getIdMedico());
				} catch (Exception e) {
					log.error("Errore durante il censimento del medico per idSoggetto "+soggetto.getIdSoggetto(), e);
				}
			
			}
			
			try {
				int ret = soggettoPersonaleScolasticoMapper.insert(soggetto);
				insertAudit("insert", "soggettoPersonaleScolasticoMapper", "insert("+soggettoScolastico.getCodiceFiscale()+")", currentUser.getCfUtente(), soggetto, req);
			} catch (org.springframework.dao.DuplicateKeyException e) {
				log.error("Soggetto già presente in piattaforma "+soggetto.getCodiceFiscale(), e);	
				throw new WebApplicationException(ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO)
						.title("Soggetto già presente")
						.dettaglio("E1", "Soggetto già presente in piattaforma " + soggetto.getCodiceFiscale()).exception()
						.getResponse());
				
			}

			// inserisco le informazioni sul risultato del sierologico (se presenti)
			if(soggettoScolastico.getDataTestSierologico()!=null && soggettoScolastico.getEsitoSierologico()!=null) {
				Referto r = insertRefertoForSoggetto(soggettoScolastico, currentUser);
				
				insertAudit("insert", "refertoDettaglioMapper", "insert("+soggettoScolastico.getCodiceFiscale()+")", currentUser.getCfUtente(), r, req);
			}
			
			
			// Gestione personale scolastico e studenti
			Date now = new Date();
			if(soggettoScolastico.getElencoIstitutiScolastico()!=null && !soggettoScolastico.getElencoIstitutiScolastico().isEmpty()) {
				
				for (IstitutoScolastico istitutoScolastico : soggettoScolastico.getElencoIstitutiScolastico()) {
					
					Integer idIstituto = istitutoScolastico.getIdIstituto();
					
					log.info("Inserimento associazione soggetto - istituto :" +soggettoScolastico.getIdSoggetto() + " - " + idIstituto);
					
					RSoggettoPersonaleScolasticoIstituto rsoggettoIstituto = new RSoggettoPersonaleScolasticoIstituto();
					rsoggettoIstituto.setIdSoggetto(soggetto.getIdSoggetto().intValue());
					rsoggettoIstituto.setIdIstituto(idIstituto);
					rsoggettoIstituto.setValiditaInizio(now);
					rsoggettoIstituto.setDataCreazione(now);
					rsoggettoIstituto.setDataModifca(now);
					rsoggettoIstituto.setUtenteOperazione(currentUser.getCfUtente());
					
					
					soggScolasticoIstitutoMapper.insert(rsoggettoIstituto);
				
				}				
				
			}

			return getSoggettoScolasticoByIdSoggetto(soggetto.getIdSoggetto(), securityContext, httpHeaders, req);
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		
		}
	}

	@Override
	public Response updateSoggettoScolastico(Long idSoggetto, SoggettoForPersonaleScolastico soggettoScolastico, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			Long idMedico = null;
			
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}
			
			SoggettoForPersonaleScolastico soggetto = soggettoPersonaleScolasticoMapper.selectSoggettoPersonaleScolasticoForVisuraMmgByIdSoggetto(idSoggetto, idMedico, currentUser.getCfUtente());
			

			if (soggetto==null)
			{
				log.warn("Soggetto scolastico:"+idSoggetto+" non presente");
				throw new WebApplicationException(ErroreBuilder.from(WebMessages.ENTITA_NON_TROVATA)
						.title("Soggetto personale scolastico non trovato.")
						.dettaglio("E1", "Soggetto personale scolastico non trovato con id " + idSoggetto).exception()
						.getResponse());
			}
			
			if (soggettoScolastico.getDataTestSierologico()==null
					|| soggettoScolastico.getEsitoSierologico()==null || soggettoScolastico.getEsitoSierologico().isEmpty()
				) {
				
				StringBuilder message = new StringBuilder();
				if (soggettoScolastico.getDataTestSierologico()==null) {
					message.append("dataTestSierologico obbligatorio");
				}
				if (soggettoScolastico.getEsitoSierologico()==null) {
					message.append("esitoSierologico obbligatorio");
				}
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message(message.toString())).build();
				throw new WebApplicationException(resp);
			}
			
			
			soggettoScolastico.setIdSoggetto(soggetto.getIdSoggetto());
			soggettoScolastico.setCodiceFiscale(soggetto.getCodiceFiscale());
			soggettoScolastico.setNome(soggetto.getNome());
			soggettoScolastico.setCognome(soggetto.getCognome());
			
			if(soggetto.getIdAura()!=null) {
				soggettoScolastico.setIdAura(soggetto.getIdAura());
			}
			
			soggettoScolastico.setMedico(soggetto.getMedico());
			soggettoScolastico.setTipoSoggetto(soggetto.getTipoSoggetto());
			soggettoScolastico.setEmail(soggetto.getEmail());
			
			if(soggetto.getDataNascita()!=null) {
				soggettoScolastico.setDataNascita(soggetto.getDataNascita());
			}
			
			if(soggetto.getTelefonoRecapito()!=null) {
				soggettoScolastico.setTelefonoRecapito(soggetto.getTelefonoRecapito());
			}
			
			if(soggetto.getAslDomicilio()!=null) {
				soggettoScolastico.setAslDomicilio(soggetto.getAslDomicilio());
			}
			
			
			// Gestione personale scolastico e studenti
			Date now = new Date();
			if(soggettoScolastico.getElencoIstitutiScolastico()!=null && !soggettoScolastico.getElencoIstitutiScolastico().isEmpty()) {
				
				for (IstitutoScolastico istitutoScolastico : soggettoScolastico.getElencoIstitutiScolastico()) {
					
					Integer idIstituto = istitutoScolastico.getIdIstituto();
					
					log.info("Inserimento associazione soggetto - istituto :" +soggettoScolastico.getIdSoggetto() + " - " + idIstituto);
					
					RSoggettoPersonaleScolasticoIstituto rsoggettoIstituto = new RSoggettoPersonaleScolasticoIstituto();
					rsoggettoIstituto.setIdSoggetto(soggettoScolastico.getIdSoggetto().intValue());
					rsoggettoIstituto.setIdIstituto(idIstituto);
					rsoggettoIstituto.setValiditaInizio(now);
					rsoggettoIstituto.setDataCreazione(now);
					rsoggettoIstituto.setDataModifca(now);
					rsoggettoIstituto.setUtenteOperazione(currentUser.getCfUtente());
					
					
					soggScolasticoIstitutoMapper.insert(rsoggettoIstituto);
				
				}				
				
			}
			
			
			Referto r = insertRefertoForSoggetto(soggettoScolastico, currentUser);
			
			insertAudit("insert", "refertoDettaglioMapper", "insert("+soggettoScolastico.getCodiceFiscale()+")", currentUser.getCfUtente(), r, req);

			soggettoPersonaleScolasticoMapper.updateByPrimaryKey2(soggettoScolastico);
			insertAudit("insert", "soggettoPersonaleScolasticoMapper", "update("+soggettoScolastico.getCodiceFiscale()+")", currentUser.getCfUtente(), r, req);

			
			return Response.ok().build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Errore in chiamata",e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	private Referto insertRefertoForSoggetto(SoggettoForPersonaleScolastico soggettoScolastico,
			UserLogged currentUser) {
		
		Referto r = new Referto();
		r.setCodfisc(soggettoScolastico.getCodiceFiscale().toUpperCase());
		r.setCognome(soggettoScolastico.getCognome().toUpperCase());
		r.setNome(soggettoScolastico.getNome().toUpperCase());
		r.setDatanascita(soggettoScolastico.getDataNascita());
		r.setCodTipoRichiesta(COD_TIPO_RICHIESTA_05);
		r.setUtenteoperazione(currentUser.getCfUtente());
		r.setDataCreazione(new Date());
		r.setIdAura(soggettoScolastico.getIdAura()!=null?soggettoScolastico.getIdAura().toString():null);
		r.setCodCl("visurammg.siero.scuola");
		r.setLegalauthenticator(currentUser.getCfUtente());
		r.setSesso(calcolaSessoFromCf(soggettoScolastico.getCodiceFiscale().toUpperCase()));
		r.setAslLaboratorio(soggettoScolastico.getAslDomicilio());
		refertoMapper.insert(r);
		
		RefertoDettaglio rd = new RefertoDettaglio();
		rd.setIdMessaggio(r.getIdMessaggio());
		rd.setDataCreazione(new Date());
		rd.setEffectivetimeValue(soggettoScolastico.getDataTestSierologico());
		rd.setValueCode(soggettoScolastico.getEsitoSierologico().toUpperCase());
		rd.setCodeCode("91.31.C");
		rd.setCodeDisplayname("test sierologico veloce");
		rd.setValueDisplayname(soggettoScolastico.getEsitoSierologico().toUpperCase().equals("P")?"POSITIVO":"NEGATIVO");
		
		refertoDettaglioMapper.insert(rd);
		return r;
	}

	
	/**
	 * Cancella un soggetto scolastico che non ha ancora compilato le informazioni sul test sierologico
	 * Per la delete l'unico controllo che deve esser fatto capire se esiste gi un referto inserito per quel paziente. 
	 * Quindi una select sulla dmarefcovid_t_referto. Se trova gia il risultato inserito allora deve impedire la cancellazione
	 */
	@Override
	public Response deleteSoggettoScolastico(Long idSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			Long idMedico = null;
			
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}
			
			SoggettoForPersonaleScolastico soggetto = soggettoPersonaleScolasticoMapper.selectSoggettoPersonaleScolasticoForVisuraMmgByIdSoggetto(idSoggetto, idMedico, currentUser.getCfUtente());
			

			if (soggetto==null)
			{
				log.warn("Soggetto scolastico:"+idSoggetto+" non presente");
				throw new WebApplicationException(ErroreBuilder.from(WebMessages.ENTITA_NON_TROVATA)
						.title("Soggetto personale scolastico non trovato.")
						.dettaglio("E1", "Soggetto personale scolastico non trovato con id " + idSoggetto).exception()
						.getResponse());
			}

			if (soggetto.getCodiceFiscale()==null || soggetto.getCodiceFiscale().isEmpty())
			{
				log.warn("Soggetto scolastico:"+idSoggetto+" senza codice fiscale, impossibile cancellare");
				throw new WebApplicationException(ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO)
						.title("Soggetto personale scolastico senza codice fiscale.")
						.dettaglio("E1", "Soggetto personale scolastico senza codice fiscale con id " + idSoggetto+". Impossibile verificare la presenza si eventuali referti")
						.exception()
						.getResponse());
			}
			
			List<Referto> referti = refertoMapper.selectByCFAndCodTipoRichiesta(soggetto.getCodiceFiscale(), COD_TIPO_RICHIESTA_05);
			if (referti!=null && !referti.isEmpty())
			{
				log.warn("Soggetto scolastico:"+idSoggetto+" con referti!");
				throw new WebApplicationException(ErroreBuilder.from(WebMessages.PARAMETRO_ERRATO)
						.title("Soggetto personale scolastico con referti.")
						.dettaglio("E1", "Soggetto personale scolastico con referti con id " + idSoggetto+", rispetto al Codice Fiscale" ).exception()
						.getResponse());
			}

			int deleteCount;
			try {
				 deleteCount =
				soggettoPersonaleScolasticoMapper.deleteByPrimaryKey(idSoggetto);
				//deleteCount = 1;
				log.info("deleted soggettoPersonaleScolastico: " + idSoggetto + " [deleteCount: " + deleteCount + "]");			
				insertAudit("delete", "soggettoPersonaleScolastico", "delete(" + idSoggetto + ")",
					currentUser.getCfUtente(), null, req);
			} catch (RuntimeException re) {
				log.error("Errore cancellazione soggettoPersonaleScolastico con id: " + idSoggetto, re);
				throw new WebApplicationException(
						ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).throwable(re).exception().getResponse()
						);
			}



			return Response.ok(deleteCount).build();

		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException) e;
			log.error("Errore in chiamata", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}


	@Override
	public Medico aggiornamentoMediciPerSoggetto(Soggetto soggetto,Long idAura, HttpServletRequest req, UserLogged currentUser) throws Exception {
		log.info("Recupero medico aura");
		
		// Lettuta Medico da AURA
		Medico medicoFromAura = extractMedicoFromAura(""+idAura);
		
		if (medicoFromAura!=null && medicoFromAura.getIdMedico()!=null && medicoFromAura.getCfMedico()!=null)
		{
			log.info("Richiesta inserimento medico "+medicoFromAura.getIdMedico()+"per soggetto "+soggetto.getIdSoggetto() +" idAura:"+idAura);
			if (medicoMapper.selectByPrimaryKey(medicoFromAura.getIdMedico())!=null)
			{
				log.info("Medico richiesto per soggetto "+soggetto.getIdSoggetto()+" trovato "+medicoFromAura.getIdMedico());							
			}
			else {
				log.info("Medico richiesto per soggetto "+soggetto.getIdSoggetto()+"non trovato : inserimento in corso");							
				medicoMapper.insert(medicoFromAura);
				insertAudit("insert", "medico", "insert("+medicoFromAura.getIdMedico()+")", currentUser.getCfUtente(), (Soggetto)soggetto, req);
			}
		}
		return medicoFromAura;
	}
	
	private static String calcolaSessoFromCf(String cf) {
		try {
			Integer day = Integer.valueOf(cf.substring(9, 11));
			return (day>40) ? "F" : "M";
		} catch(NumberFormatException e) {
			return null;
		}		
	}
	
}
