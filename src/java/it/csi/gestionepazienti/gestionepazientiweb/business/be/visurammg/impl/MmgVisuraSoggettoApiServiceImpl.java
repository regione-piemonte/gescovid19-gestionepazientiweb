package it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AbstractSoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.visurammg.MmgVisuraSoggettoApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asl;
import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.MedicoSoggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RSoggettoIstituto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Sintomo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoSintomo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForSegnalazione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.filter.gestionepazienti.IrideIdAdapterFilter;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AslMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecodeTipoEventoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecorsoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.IstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.MedicoSoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.RMedicoMedicoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SintomoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoIstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TestCovidMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MmgVisuraSoggettoApiServiceImpl extends AbstractSoggettoApiServiceImpl implements MmgVisuraSoggettoApi {

	public static final String ASL_EXTRA_REGIONE = "EXTRA-REGIONE";

	private static final Integer ID_TIPO_EVENTO_SEGNALAZIONE =11;  
	
	@Autowired
	TestCovidMapper testCovideMapper;

	@Autowired
	DecorsoMapper decorsoMapper;
	
	@Autowired
	SintomoMapper sintomoMapper;

	@Autowired
	DecodeTipoEventoMapper decodeTipoEventoMapper;
	
	@Autowired
	AslMapper  aslMapper;
	
	@Autowired
	MedicoSoggettoMapper medicoSoggettoMapper;
	
	@Autowired
	IstitutiScolasticiMapper istitutiScolasticiMapper;
	
	@Autowired
	RMedicoMedicoMapper rMedicoMedicoMapper;
	

	@Autowired
	SoggettoIstitutiScolasticiMapper rSoggettoIstitutoMapper;

	@Override
	public Response getElencoSoggettiByMedicoXlsx(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);
			Long idMedico = null;
			
			// recupera istat dal sindaco 
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}
			

			List<SoggettoForVisura> soggettoList = soggettoMapper
					.selectForElencoVisuraByMedico(idMedico);

			insertAudit("select", "soggetto", "selectForElencoVisuraByMedico()", currentUser.getCfUtente(),
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
	public Response getElencoSoggettiByMedico(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = (UserLogged) req.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);

			Long idMedico = null;
			
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}
			
			// Raffa modifica  per gestione sostituto
			// List<SoggettoForVisura> soggettoList = soggettoMapper.selectForElencoVisuraByMedico(idMedico);
			List<SoggettoForVisura> soggettoList = soggettoMapper.selectSoggettiForVisuraMmgByMedico(idMedico);
			insertAudit("select", "soggetto", "selectSoggettiForVisuraMmgByMedico()", currentUser.getCfUtente(),
					null, req);

			return Response.ok(soggettoList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}

	@Override
	public Response getSoggettoByIdSoggetto(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);

			Long idMedico = null;
			
			// recupera istat dal sindaco //
			if (currentUser.getMedico()!=null)
			{
				idMedico = currentUser.getMedico().getIdMedico();
			}

			if (idMedico==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza medico associato")).build();
				return resp;
			}

			
			SoggettoForElenco soggettoForElenco = soggettoMapper.selectForElencoByIdSoggettoIdMedico(id, idMedico);
			if (soggettoForElenco==null)
				return Response.ok(null).build();
			
			List<TamponeForElenco> elencoTampone = tamponeMapper.selectForElencoByIdSoggetto(id);
			List<DecorsoForElenco> elencoDecorso = decorsoMapper.selectForElencoByIdSoggetto(id);
			List<SoggettoAura> elencoSoggettoAura = soggettoAuraMapper.selectByIdSoggetto(id);
			
			Date now= new Date();
			//List<IstitutoScolastico> elencoIstitutoScolastico = istitutiScolasticiMapper.selectIstitutiValidiBySoggettoId(id, now);
			List<IstitutoScolastico> elencoIstitutoScolastico = istitutiScolasticiMapper.selectBySoggettoId(id);
			Long idAura = null;
			Medico medico = null;
			//Dovrebbe esserci al piu un solo soggettoAura ma il db ne permette piu di uno. scelgo il primo e loggo
			if (elencoSoggettoAura!=null && !elencoSoggettoAura.isEmpty())
			{
				if (elencoSoggettoAura.size()>1)
					log.warn("Trovato soggetto con "+elencoSoggettoAura.size()+"  id_aura associati!! "+id);
				idAura = elencoSoggettoAura.get(0).getIdAura();
				List<Medico> elencoMedico = medicoMapper.selectByIdAuraSogg(idAura);
				if (elencoMedico!=null && !elencoMedico.isEmpty())
				{
					if (elencoMedico.size()>1)
						log.warn("Trovato soggetto con "+elencoMedico.size()+"  medici associati!! "+id);
					medico = elencoMedico.get(0);
				}
			}
			
			SoggettoForDettaglio dettaglio = new SoggettoForDettaglio(soggettoForElenco, elencoTampone, elencoDecorso, medico);
			dettaglio.setIdAura(idAura);
			
			// nuovo per gestione studenti
			dettaglio.setElencoIstitutiScolastico(elencoIstitutoScolastico);
			
			insertAudit("select", "soggetto tampone decorso", "selectForElencoByIdSoggettoIdMedico("+id+","+idMedico+")", currentUser.getCfUtente(), null, req);

			
			return Response.ok(dettaglio).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	
	public Response insertSoggettoSegnalazione(SoggettoForSegnalazione soggetto, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			Medico medicoFromAura = null;
			String returnMessage = null;
			boolean soggettoPresenteAndTentatoInserimento = false;
			log.info("Richiesta inserimento segnalazione from medico "+currentUser.getCfUtente());
			DecorsoSintomo decorso = soggetto.getDecorso();

			boolean isPazienteConSegnalazione = false;
			
			Long idSoggettoNew = null;
						
			
			/**
			 * Raffa:  modifica per rendere non obbligatorio l'inserimento
			 * della segnalazione con il soggetto
			 */
			if (decorso!=null && 
					(decorso.getSintomi().equalsIgnoreCase("SI") && decorso.getSintomo()==null))
			{
				log.error("Richiesta segnalazione per soggetto ma senza decorso o sintomi");
				Response resp = Response.status(Status.BAD_REQUEST)
						.entity(new Message("E' necessario fornire un decorso di tipo segnalazione con un oggetto sintomi incluso")).build();
				throw new WebApplicationException(resp);
			}
			
			boolean isPazienteConSintomi = false;

			if(decorso!=null)
				isPazienteConSegnalazione = true;
			
			// raffa 29-04-2020 //////////////
			if(decorso !=null && decorso.getSintomi().equalsIgnoreCase("SI") && decorso.getSintomo()!=null)
				isPazienteConSintomi = true;
				
			//			if (decorso==null || 
//					(decorso.getSintomi().equalsIgnoreCase("SI") && decorso.getSintomo()==null))
//			{
//				log.error("Richiesta segnalazione per soggetto ma senza decorso o sintomi");
//				Response resp = Response.status(Status.BAD_REQUEST)
//						.entity(new Message("E' necessario fornire un decorso di tipo segnalazione con un oggetto sintomi incluso")).build();
//				throw new WebApplicationException(resp);
//			}
			
			// Verifica richiesta di inserimento anche del soggetto:
			if (soggetto.getIdSoggetto()!=null)
			{
				log.info("Richiesta aggiunta segnalazione from medico "+currentUser.getCfUtente() +" per idSoggetto "+soggetto.getIdSoggetto());
				// check se utente in visibilita del medico
				Response resp = getSoggettoByIdSoggetto(soggetto.getIdSoggetto(), securityContext, httpHeaders, req);			
				if (resp.getStatus()>=300 || resp.getStatus()<200 || resp.getEntity()==null)
				{
					log.error("Richiesta aggiunta segnalazione from medico "+currentUser.getCfUtente() +" per idSoggetto "+soggetto.getIdSoggetto() +" non suo!");
					if (resp.getEntity()==null)
						resp = Response.status(Status.FORBIDDEN).entity(null).build();
					throw new WebApplicationException(resp);
				}
					
			} else {
				log.info("Inizio inserimento nuovo soggetto per medico "+currentUser.getCfUtente());
				log.info("Recupero isASR da associare");
				String aslDomicilio = soggetto.getAslDomicilio();
				Long idsAsrDestination = null; // TODO o do priority a asr passata?
				if (idsAsrDestination==null && aslDomicilio!=null && !aslDomicilio.equalsIgnoreCase(ASL_EXTRA_REGIONE))
				{
					List<Asl> asls = aslMapper.selectAll();
					if (asls!=null)
					{
						for (Asl asl : asls) {
							if (asl.getDescAslEstesa().equalsIgnoreCase(aslDomicilio))
							{
								idsAsrDestination = asl.getIdAsr();  // Nota e' null quando e' ASL fuori regione
							}
						}
					}
					if (idsAsrDestination==null)
					{
						log.info("Nessuna ASR associata all'asl"+aslDomicilio);
					}
					else {
						log.info("Soggetto ASR trovata:"+idsAsrDestination );
						soggetto.setIdAsr(idsAsrDestination);
					}
				} 
				if (soggetto.getIdAsr()==null) {
					log.info("ID ASR non trovata sul domicilio e non passata"); // utilizzo quello passato
					throw new WebApplicationException( 
							Response.status(Status.BAD_REQUEST).entity(new Message("Impossibile determinare l'ASR: deve essere passata")).build()
							);
				}
				
				// a questo punto l'asr e' asssociata al soggetto
				// not duplicate CF for same idASR
				if	 (soggetto.getCodiceFiscale()!=null && !soggetto.getCodiceFiscale().isEmpty())
				{
					List<Soggetto> soggetti = soggettoMapper.selectForElencoByCodiceFiscaleIdAsr(soggetto.getCodiceFiscale().toUpperCase(),-1L);
					if (soggetti!=null && !soggetti.isEmpty())
					{
						log.info("Identificato soggetto duplicato "+soggetto.getCodiceFiscale()+",idAsr="+soggetto.getIdAsr() +" utilizzo il primo");
						// utilizzo il soggetto giA inserito
						soggetto.setIdSoggetto(soggetti.get(0).getIdSoggetto());
						soggettoPresenteAndTentatoInserimento = true;
						// TODO verificare se fare update
					}
				}
				else
					log.info("Inserimento soggetto senza codice fiscale");
				
				if (soggetto.getIdSoggetto()==null)
				{
					int result = soggettoMapper.insert(soggetto);
					log.info("Inserimento soggetto id:" + result);
					insertAudit("insert", "soggetto", "insert("+soggetto.getIdSoggetto()+")", currentUser.getCfUtente(), (Soggetto)soggetto, req);
				}
				
				// RM: Gestione Istituto scolastico
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
						
						
						rSoggettoIstitutoMapper.insert(rsoggettoIstituto);
				
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
					soggettoAuraMapper.deleteByIdSoggetto(soggetto.getIdSoggetto());
					log.info("Inserimento soggetto con associazione aura "+soggetto.getIdSoggetto());
					soggettoAuraMapper.insert(sa);
					try {
						medicoFromAura = aggiornamentoMediciPerSoggetto(soggetto,soggetto.getIdAura(), req, currentUser);
					} catch (Exception e) {
						log.error("Errore durante il censimento del medico per idSoggetto "+soggetto.getIdSoggetto(), e);
					}
				
				}
		
				if (medicoFromAura!=null && medicoFromAura.getIdMedico().equals(currentUser.getMedico().getIdMedico()))
				{
					log.info("Medico oggetto di segnalazione coincidente con medico aura");
				}
				else if(!soggettoPresenteAndTentatoInserimento) {					
					log.info("Medico oggetto di segnalazione nullo o non coincidente inserimento relazione nuova");
					MedicoSoggetto record = new MedicoSoggetto();
					record.setIdMedico(currentUser.getMedico().getIdMedico());
					record.setIdSoggetto(soggetto.getIdSoggetto());
					
					List<MedicoSoggetto> medici = medicoSoggettoMapper.selectByPk(record);
					if (medici==null || medici.isEmpty()) {
						medicoSoggettoMapper.insert(record);
					}
				}
				
				if (soggettoPresenteAndTentatoInserimento) {
					boolean soggettoAppartieneAMedico = soggettoAppartieneAMedico(soggetto, currentUser);
					
					returnMessage = "Soggetto gia' presente sulla piattaforma, ";
					if(soggettoAppartieneAMedico) {
						returnMessage = returnMessage + "lo troverai nella lista dei tuoi pazienti. ";
					} else {
						returnMessage = returnMessage + "non appartiene alla lista dei tuoi pazienti. ";
					}
					
					returnMessage = returnMessage  + "Si rammenta che le segnalazioni ai SISP vanno effettuate solo nel caso di prima segnalazione.";
					
				}
				
			}

			/**
			 * Raffa:  modifica per rendere non obbligatorio l'inserimento
			 * della segnalazione con il soggetto
			 * 
			 * 
			 */
			if (!soggettoPresenteAndTentatoInserimento 
					&& isPazienteConSegnalazione)
			{		
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.clear(Calendar.MINUTE);
				calendar.clear(Calendar.SECOND);
				calendar.clear(Calendar.MILLISECOND);
				Date dataOra = calendar.getTime();
		        
				decorso.setIdTipoEvento(ID_TIPO_EVENTO_SEGNALAZIONE);
				decorso.setDataDimissioni(dataOra);
				decorso.setDataEvento(new Date());
				//Check sintomi e condizioni cliniche 
				decorso.setIdSoggetto(soggetto.getIdSoggetto());
				decorso.setNote("Medico segnalazione:["+currentUser.getMedico().getNome()+" "+currentUser.getMedico().getCognome()+"]");
				int ret = decorsoMapper.insert(decorso);
				
				if(isPazienteConSintomi) {
					Sintomo sintomo = decorso.getSintomo();
					if (sintomo!=null){
						{
							sintomo.setIdDecorso(decorso.getIdDecorso());
							ret = sintomoMapper.insert(sintomo);
							insertAudit("insert", "sintomo", "insert("+sintomo.getIdSintomo()+")", currentUser.getCfUtente(), sintomo, req);
						}		
					}
				}
				insertAudit("insert", "decorso", "insert("+decorso.getIdDecorso()+")", currentUser.getCfUtente(),decorso, req);
			}else log.info("Inserimento soggetto senza DECORSO e SINTOMO");
			
			
			
			if (returnMessage!=null)
			{
				return Response.ok().entity(new Message(returnMessage)).build();
			}
					
			
			return Response.ok().entity(soggetto).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	private boolean soggettoAppartieneAMedico(SoggettoForSegnalazione soggetto, UserLogged currentUser) {
		MedicoSoggetto record = new MedicoSoggetto();
		record.setIdSoggetto(soggetto.getIdSoggetto());
		List<MedicoSoggetto> mediciDelSoggetto = medicoSoggettoMapper.selectByIdSoggetto(record);
		for(MedicoSoggetto ms : mediciDelSoggetto) {
			boolean trovato = currentUser!=null && 
					currentUser.getMedico()!=null && 
					currentUser.getMedico().getIdMedico() != null &&
					ms.getIdMedico() !=null &&
					currentUser.getMedico().getIdMedico().compareTo(ms.getIdMedico())==0;
			if(trovato) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	public Response searchSoggettoSegnalazione(String codiceFiscale, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			Medico medicoFromAura = null;
			String returnMessage = null;
			boolean soggettoPresente = false;
			
			log.info("searchSoggettoSegnalazione - Ricerca soggetto by cf "+codiceFiscale+" from medico "+currentUser.getCfUtente());
			
			if (codiceFiscale==null || codiceFiscale.isEmpty())
				throw new WebApplicationException(Response.serverError().entity(new Message("codice fiscale obbligatorio")).build());
				
			List<Soggetto> elencoSoggetti =soggettoMapper.selectForElencoByCodiceFiscaleIdAsr(codiceFiscale.toUpperCase(), -1L);
			
			if (elencoSoggetti==null || elencoSoggetti.isEmpty())
			{
				log.info("searchSoggettoSegnalazione - Nessun soggetto trovato in piattaforma per cf"+codiceFiscale);
				return Response.status(Status.NOT_FOUND).build();
			}
			else {
				soggettoPresente = true;
				log.info("searchSoggettoSegnalazione - Soggetto trovato "+codiceFiscale);
				for (Soggetto soggettoTrovato : elencoSoggetti) {
					// se associato al medico, lo vede gia'
					List<SoggettoAura> elencoSoggettoAura = soggettoAuraMapper.selectByIdSoggetto(soggettoTrovato.getIdSoggetto());
					// so che e' sempre uno solo 
					if (elencoSoggettoAura!=null && !elencoSoggettoAura.isEmpty())
					{
						SoggettoAura soggettoAura = elencoSoggettoAura.get(0); // sempre uno solo e poi aggiorna tutto
						log.info("searchSoggettoSegnalazione - Verifica su aura per "+codiceFiscale);
						try {
							medicoFromAura = aggiornamentoMediciPerSoggetto(soggettoTrovato,soggettoAura.getIdAura(), req, currentUser);
						} catch (Exception e) {
							log.error("searchSoggettoSegnalazione - Errore durante il censimento del medico per idSoggetto "+soggettoTrovato.getIdSoggetto(), e);
						}
					}
					if (medicoFromAura!=null && medicoFromAura.getIdMedico().equals(currentUser.getMedico().getIdMedico()))
					{
						log.info("searchSoggettoSegnalazione - Medico oggetto di segnalazione coincidente con medico aura");
					}
					else if(!soggettoPresente) { // solo se non presente!			
						log.info("searchSoggettoSegnalazione - Medico oggetto di segnalazione nullo o non coincidente inserimento relazione nuova");
						MedicoSoggetto record = new MedicoSoggetto();
						record.setIdMedico(currentUser.getMedico().getIdMedico());
						record.setIdSoggetto(soggettoTrovato.getIdSoggetto());
						
						List<MedicoSoggetto> medici = medicoSoggettoMapper.selectByPk(record);
						if (medici==null || medici.isEmpty())
							medicoSoggettoMapper.insert(record);
						returnMessage = "Medico presente nella tua lista.";

					}

					
				}
			}
			
			if (soggettoPresente) {
				returnMessage = "Soggetto gia' presente sulla piattaforma, se di tua competenza lo troverai nella lista dei tuoi pazienti.  "
						+ "Si rammenta che le segnalazioni ai SISP vanno effettuate solo nel caso di prima segnalazione.";
			}
				

			
			if (returnMessage!=null)
			{
				return Response.ok().entity(new Message(returnMessage)).build();
			}
				
				
			return Response.ok().build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	


//	public static void main(String[] args) throws Exception {
//		XSSFWorkbook workbook = DisponibilitaApiServiceImpl.createWorkbook();
//		FileOutputStream fileOut = new FileOutputStream("d:/a.xlsx");
//		Sheet sheet= workbook.getSheet("Situazione");
//		for (int i = 1; i<  5; i++) 
//		{
//			int j=0;
//			Row row = sheet.createRow(i);
//			Cell cell = row.createCell(j);
//			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
//			cell.setCellValue("Nome_"+i);
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
//			cell.setCellValue("Struttur_"+i);
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
//			cell.setCellValue("natura_"+i);
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
//			cell.setCellValue("");
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
//			cell.setCellValue(7);
//			j++;
//		}
//		
//		//write this workbook to an Outputstream.
//		workbook.write(fileOut);
//		fileOut.flush();
//		fileOut.close();
//		workbook.close();
//	}


}
