package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.DecorsoApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Sintomo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAsr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.ErroreBuilder;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.WebMessages;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AslMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AsrMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecodeTipoEventoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecorsoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SintomoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.DisponibilitaMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.EnteMapper;;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)

public class DecorsoApiServiceImpl extends AuditableApiServiceImpl implements DecorsoApi {

	private static final int SEGNO_PIU_UNO = 1;

	public static final int SEGNO_MENO_UNO = -1;

	public static final int ID_TIPO_EVENTO_QUARANTENA_DOMIC = 1; //ISOLAMENTO_DOMICILIARE
	public static final int ID_TIPO_EVENTO_TRASFERITO_INTERNO_STRUTTURA = 3;
	public static final int ID_TIPO_EVENTO_TRASFERITO_ESTERNO_STRUTTURA = 4;
	
	public static final int ID_TIPO_EVENTO_QUARANTENA_EXTRA_DOMIC = 10; //ASSEGNAZIONE_DOMICILIO
	public static final int ID_TIPO_EVENTO_POST_RICOVERO = 9;
	public static final int ID_TIPO_EVENTO_GUARITO = 6;
	public static final int ID_TIPO_EVENTO_DECEDUTO_COVID = 5;
	public static final int ID_TIPO_SEGNALAZIONE =  11;
	public static final int ID_TIPO_EVENTO_PRESA_IN_CARICO =  12;  
	public static final int ID_TIPO_EVENTO_USCITO = 13;
	public static final int ID_TIPO_EVENTO_INFO_DA_INTEGRARE = 14;
	public static final int ID_TIPO_EVENTO_ISOLAMENTO_FIDUCIARIO = 15; // simile all'1
	
	public static final int ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_DOMIC = 16; // simile all'1
	public static final int ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_EXTRA_DOMIC = 17; // simile al 10
	public static final int ID_TIPO_EVENTO_DECEDUTO_NON_COVID = 18; // simile al 5

	public static final String ASL_EXTRA_REGIONE = "EXTRA-REGIONE";

	@Autowired
	DecorsoMapper decorsoMapper;
	
	@Autowired
	SintomoMapper sintomoMapper;

	@Autowired
	SoggettoMapper soggettoMapper;
	
	@Autowired
	DisponibilitaMapper disponibilitaMapper;
	
	@Autowired
	DecodeTipoEventoMapper decodeTipoEventoMapper;
	
	@Autowired
	AslMapper  aslMapper;
	
	@Autowired
	AsrMapper asrMapper;
	
	@Autowired
	EnteMapper enteMapper;

	@Autowired
	private MessageSource messageSource;


	@Override
	public Response insertDecorso(DecorsoForElenco decorso, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			Date dataOra = new Date();
			String messageReturn = null;
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			// idTipoEvento obbligatorio
			if (decorso.getIdTipoEvento()==null)
			{
				log.info("Decorso senza tipo evento");
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message("Tipologia evento obbligatorio")).build();
				throw new WebApplicationException(resp);
			}
			log.info("Richiesta inserimento decorso con id_tipo_evento "+decorso.getIdTipoEvento());
			if (
					(
						decorso.getIdTipoEvento().intValue()==(ID_TIPO_EVENTO_PRESA_IN_CARICO)
						||
						decorso.getIdTipoEvento().intValue()==(ID_TIPO_EVENTO_INFO_DA_INTEGRARE)
					)
					&& decorso.getDataDimissioni()==null)
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.clear(Calendar.MINUTE);
				calendar.clear(Calendar.SECOND);
				calendar.clear(Calendar.MILLISECOND);
		        decorso.setDataDimissioni(calendar.getTime());
			}
			if (decorso.getDataDimissioni()==null)
			{
				log.info("Decorso senza data dimissioni");
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message("Data dimissioni obbligatoria")).build();
				throw new WebApplicationException(resp);
			}
			// vengono raggruppati i check per campo
			
//			// campo previsione fine evento      COV-81 rimosso check
//			if (decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_QUARANTENA_DOMIC 
//					|| decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_QUARANTENA_EXTRA_DOMIC)
//			{
//				if (decorso.getDataPrevFineEvento()==null)
//				{
//					log.info("Data fine quarantena obbligatoria");
//					throw new WebApplicationException(
//							Response.status(Status.BAD_REQUEST).entity(new Message("Data fine quarantena obbligatoria")).build());
//				}
//			} else {
//				// campo che non ha senso per gli altri casi
//				decorso.setDataPrevFineEvento(null);
//			}

			
			
			// se isolamento il comune obbligatorio 
						// se non isolamento rimuovo il comune
			if (decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_QUARANTENA_DOMIC || 
					decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_ISOLAMENTO_FIDUCIARIO ||
					decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_DOMIC
					)
			{
				if (decorso.getComuneRicoveroIstat()==null 
						|| decorso.getComuneRicoveroIstat().isEmpty())
				{
					log.info("Decorso in isolamento senza comune ricovero");
					throw new WebApplicationException(
							Response.status(Status.BAD_REQUEST)
							.entity(new Message("In caso di isolamento il comune di ricovero deve essere indicato")).build());
				}
			}
			else if (
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_POST_RICOVERO
					&& 
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_PRESA_IN_CARICO
					&&
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_INFO_DA_INTEGRARE
					&& 
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_GUARITO
					&&
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_DECEDUTO_COVID
					&& 
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_DECEDUTO_NON_COVID
					&& 
					decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_USCITO){	 
				// idArea obbligatorio (se non isolamento) e
				decorso.setComuneRicoveroIstat(null);
				if (decorso.getIdArea()==null)
				{
					log.info("Decorso senza id area (non isolamento, ne post ricovero, ne guarito , ne deceduto)");
					throw new WebApplicationException(
							Response.status(Status.BAD_REQUEST)
							.entity(new Message("L'area deve essere indicata per questo tipo di evento")).build());
				}
			}
						
			// sintomi si/no obbligatorio
			// se si obbligatoria la data_sint
			if (decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_GUARITO
					&&
				decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_DECEDUTO_COVID
					&&
				decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_DECEDUTO_NON_COVID
					&&
				decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_PRESA_IN_CARICO
					&&
				decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_INFO_DA_INTEGRARE
					&&
				decorso.getIdTipoEvento().intValue()!=ID_TIPO_EVENTO_USCITO)
			{
				if (decorso.getSintomi()==null || decorso.getSintomi().isEmpty())
				{
					log.info("Decorso senza sintomi");
					throw new WebApplicationException(
							Response.status(Status.BAD_REQUEST)
							.entity(new Message("Campo sintomi obbligatorio")).build());
				}
				else {
					if (decorso.getSintomi().equalsIgnoreCase("SI") && decorso.getDataInizioSint()==null)
					{
						log.info("Decorso senza data sintomi, ma con sintomi");
						throw new WebApplicationException(
								Response.status(Status.BAD_REQUEST)
								.entity(new Message("Data inizio sintomi obbligatoria")).build());
					}
				}
			} 
			
			Long idAsrForQuery = currentUser.getIdAsr();
			if(idAsrForQuery==null) {
				idAsrForQuery = Long.valueOf(-1);
			}
			
			SoggettoForElenco soggetto = soggettoMapper.selectForElencoByIdSoggetto(decorso.getIdSoggetto(), idAsrForQuery);
			
			if(soggetto!= null && currentUser.getIdAsr()==null) {
				Integer idEnte = soggettoMapper.selectEnteByIdSoggetto(decorso.getIdSoggetto());
				Long idAsr = asrMapper.selectByIdEnte(idEnte.longValue() /*, soggetto.getAslDomicilio()*/);
				currentUser.setIdAsr(idAsr);
				
				log.info("CurrentUser idAsr ricavato dal soggetto [id:"+decorso.getIdSoggetto()+"]: " +idAsr);
			}

			if (soggetto == null || soggetto.getIdAsr() ==null )
			{
				log.info("Soggetto non conosciuto, aggiungo visibilita' al asr corrente "+ 
								decorso.getIdSoggetto() +
								" -> "+ currentUser.getIdAsr());
				
				this.aggiornavisibilita(decorso.getIdSoggetto(), null, currentUser.getIdAsr());
				
				// potrebbe essere nei trasferiti oppure trovato con ricerca puntuale
				// indi -> inutile cercarlo nei trasferiti
				
				// se non lo trovo, puo essere nei trasferiti e se e' lo cerco e cambio ASR				
//				if (GenericUtils.currentUserHasProfilo(currentUser, 6))
//				{
					// cerco nei trasferiti
//				List<SoggettoForTrasferimento> soggettiTrasferiti = soggettoMapper.
//						selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEvento(-1L, decorso.getIdSoggetto(),new Integer[]{3,4});
//				if (soggettiTrasferiti!=null && !soggettiTrasferiti.isEmpty())
//				{
//				log.info("Inizio procedura di trasferimento "+decorso.getIdSoggetto());
//
//				String idSoggettoStr= decorso.getIdSoggetto().toString();
//				String idAsrStr = currentUser.getIdAsr().toString();
//				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//				String dataOraStr = sdf.format(dataOra);
//				log.info("Richiamo migrazione con "+idSoggettoStr+","+idAsrStr+","+dataOraStr );
//				// TODO callAumentoVisibilita
//				int retMigrazione = soggettoMapper.callAumentoVisibilitaAsr(idSoggettoStr, idAsrStr, dataOraStr);
//				log.info("Migrazione -> codice di ritorno:"+retMigrazione);
//				if (retMigrazione!=0)
//				{
//					log.error("Errore durante la migrazione! codice di ritorno:"+retMigrazione);
//					throw new WebApplicationException(
//							Response.serverError()
//							.entity(new Message("Errore durante la migrazione! codice di ritorno:"+retMigrazione)).build());
//				}
				
				// ricarico soggetto
//				soggetto = soggettoMapper.selectForElencoByIdSoggetto(decorso.getIdSoggetto(), currentUser.getIdAsr());
//				messageReturn = "Soggetto ora in carico alla tua ASR";
//				log.info("Trasferimento soggetto "+decorso.getIdSoggetto() + " all'ASR "+currentUser.getIdAsr());
//				}
//				else { 
//					log.info("Soggetto non da migrare "+decorso.getIdSoggetto());
//					throw new WebApplicationException(Response.status(Status.FORBIDDEN).build());
//				}
//			}
//			else {
//				log.info("Utente senza autorizzazione alla migrazione");
//
//				throw new WebApplicationException(Response.status(Status.FORBIDDEN).build());
//			}
					
			}
			
			decorso.setDataEvento(dataOra);

			if (decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_QUARANTENA_EXTRA_DOMIC
					|| decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_EXTRA_DOMIC)
			{
				log.info("verifica se necessario cambiare ASR di destinazione (causa apertura)");
				Ente ente = enteMapper.selectByIdArea(decorso.getIdArea());
				List<Asr> listAsr = asrMapper.selectAll();
				Asr asrDestinazione = null;
				for (Asr asr : listAsr) {
					if (asr.getIdEnte()!=null && asr.getIdEnte().equals(ente.getIdEnte()))
						asrDestinazione = asr;
				}
				if (asrDestinazione!=null && !asrDestinazione.getIdAsr().equals(currentUser.getIdAsr()))
				{
//					log.info("migrazione utente");
//					log.info("Inizio procedura di trasferimento "+decorso.getIdSoggetto());
//
//					String idSoggettoStr= decorso.getIdSoggetto().toString();
//					String idAsrStr = asrDestinazione.getIdAsr().toString();
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//					String dataOraStr = sdf.format(dataOra);
//					log.info("Richiamo migrazione con "+idSoggettoStr+","+idAsrStr+","+dataOraStr );
//					// TODO callAumentoVisibilita
//					int retMigrazione = soggettoMapper.callAumentoVisibilitaAsr(idSoggettoStr, idAsrStr, dataOraStr);
//					log.info("Migrazione -> codice di ritorno:"+retMigrazione);
//					if (retMigrazione!=0)
//					{
//						log.error("Errore durante la migrazione! codice di ritorno:"+retMigrazione);
//						throw new WebApplicationException(
//								Response.serverError()
//								.entity(new Message("Errore durante la migrazione! codice di ritorno:"+retMigrazione)).build());
//					}
					this.aggiornavisibilita(decorso.getIdSoggetto(),null, asrDestinazione.getIdAsr());

					
					// ricarico soggetto
					//soggetto = soggettoMapper.selectForElencoByIdSoggetto(decorso.getIdSoggetto(), currentUser.getIdAsr());
//					messageReturn = "Soggetto ora in carico alla ASR "+asrDestinazione.getDescrizione();
					
					log.info("Trasferimento soggetto "+decorso.getIdSoggetto() + " all'ASR "+asrDestinazione.getIdAsr());
				}
				
				
			}
			
			// se il decorso e' di tipo post ricovero (9) allora viene migrato l'utente verso la ASR relativa all'ASL di domicilio
			// togliamo la migrazione
//			if (decorso.getIdTipoEvento().intValue()==ID_TIPO_EVENTO_QUARANTENA_DOMIC)
//			{
//				log.info("Soggetto richiesto post ricovero "+soggetto.getIdSoggetto());
//				String aslDomicilio = soggetto.getAslDomicilio();
//				Asl alsDestination = null;
//				if (aslDomicilio==null || aslDomicilio.isEmpty())
//				{
//					log.info("Soggetto senza aslDomicilio e richiesto post ricovero");
//					throw new WebApplicationException(
//							Response.status(Status.BAD_REQUEST)
//							.entity(new Message("Il Post ricovero richiede un ASL domicilio sul soggetto. Si prega di inserirla.")).build());
//				}
//				if (!aslDomicilio.equalsIgnoreCase(ASL_EXTRA_REGIONE))
//				{
//					List<Asl> asls = aslMapper.selectAll();
//					if (asls!=null)
//					{
//						for (Asl asl : asls) {
//							if (asl.getDescAslEstesa().equalsIgnoreCase(aslDomicilio))
//							{
//								alsDestination = asl;  // Nota e' null quando e' ASL fuori regione
//							}
//						}
//					}
//					if (alsDestination==null)
//					{
//						log.info("Nessuna ASR associata all'asl"+aslDomicilio);
//						throw new WebApplicationException(
//								Response.status(Status.BAD_REQUEST)
//								.entity(new Message("Il Post ricovero richiede un ASL domicilio sul soggetto scritta correttamente. Si prega di inserirla.")).build());
//					}
//					else {
//						log.info("Soggetto post ricovero idAsr partenza"+soggetto.getIdAsr()+" destinazione "+alsDestination.getIdAsr());
//						if (!alsDestination.getIdAsr().equals(soggetto.getIdAsr()))
//						{
//							log.info("Soggetto post ricovero "+soggetto.getIdSoggetto()+" migrazione verso asr "+alsDestination.getIdAsr());
//							String idSoggettoStr= decorso.getIdSoggetto().toString();
//							String idAsrStr = alsDestination.getIdAsr().toString();
//							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//							String dataOraStr = sdf.format(dataOra);
//							// TODO callAumentoVisibilita
//							int retMigrazione = soggettoMapper.callAumentoVisibilitaAsr(idSoggettoStr, idAsrStr, dataOraStr);
//							log.info("Migrazione avvenuta con ritorno:"+retMigrazione);
//							if (retMigrazione!=0)
//							{
//								log.error("Errore durante la migrazione! codice di ritorno:"+retMigrazione);
//								throw new WebApplicationException(Response.serverError().entity(new Message("Errore durante la migrazione! codice di ritorno:"+retMigrazione)).build());
//							}
//							messageReturn = "Soggetto ora in carico alla ASL "+alsDestination.getDescAslEstesa();
////
//						}
//					}
//				}
//			}
			
			//verifico l'inseribilità del decorso
			checkInseribilitaEvento(decorso.getIdSoggetto(), decorso);
			
			int ret = decorsoMapper.insert(decorso);
						
			Sintomo sintomo = decorso.getSintomo();
			if (sintomo!=null){
				{
					sintomo.setIdDecorso(decorso.getIdDecorso());
					ret = sintomoMapper.insert(sintomo);
					insertAudit("insert", "sintomo", "insert("+sintomo.getIdSintomo()+")", currentUser.getCfUtente(), sintomo, req);
				}		
			}
			
			insertAudit("insert", "decorso", "insert("+decorso.getIdDecorso()+")", currentUser.getCfUtente(),decorso, req);
			
			
			// check su decorso nuovo e con operazione da fare
//			if (decorso.getIdArea()!=null 
//					&& decorso.getIdTipoEvento()!=null)
//			{
//				log.info("Inizio movimentazione decorso per idArea:"+decorso.getIdArea()+" e idTipoEvento:"+decorso.getIdTipoEvento());
//
//				DecodeTipoEvento tipoEvento = decodeTipoEventoMapper.selectByPrimaryKey(decorso.getIdTipoEvento());
//				if (tipoEvento!=null && tipoEvento.getSegno()!=null )
//				{
//					String sign = tipoEvento.getSegno();
//					int toAddOccupati = 0;
//					if (sign.equals("-"))
//					{
//						toAddOccupati = SEGNO_MENO_UNO;
//					}
//					if (sign.equals("+"))
//					{
//						toAddOccupati = SEGNO_PIU_UNO;
//					}
//					log.info("AddOccupati "+toAddOccupati+" movimentazione decorso per idArea:"+decorso.getIdArea()+" e idTipoEvento:"+decorso.getIdTipoEvento());
//					if (toAddOccupati!=0)
//					{
//						Disponibilita oldDisp= disponibilitaMapper.selectByValidAndIdArea(decorso.getIdArea());
//						if (oldDisp==null)
//						{
//							log.error("Non ho trovato disponibilita valida per idArea"+decorso.getIdArea());
//							oldDisp= disponibilitaMapper.selectLastByIdArea(decorso.getIdArea());
//							if (oldDisp==null)
//							{
//								log.error("Non ho trovato disponibilita ALCUNA per idArea"+decorso.getIdArea());
//								throw new WebApplicationException(Response.serverError().entity(new Message("Impossibile gestire la disponibilita'")).build());
//							}
//						}
//						
//						oldDisp.setFlagValido("NO");
//						disponibilitaMapper.updateInvalidByPrimaryKey(oldDisp);
//						
//						Disponibilita newDisp = new Disponibilita();
//						newDisp.setDataEvento(dataOra); // metto per ora now invece che dataevento decorso.getDataEvento()
//						newDisp.setDataInserimento(dataOra);
//						newDisp.setFlagValido("SI");
//						newDisp.setIdArea(decorso.getIdArea());
//						newDisp.setPostiDisponibili(oldDisp.getPostiDisponibili());
//						newDisp.setPostiOccupati(oldDisp.getPostiOccupati()+toAddOccupati);
//						newDisp.setPostiTarget(oldDisp.getPostiTarget());
//						newDisp.setTipoOperazione("OCCUPATI");
//						newDisp.setUtenteInserimento(currentUser.getCfUtente());
//						disponibilitaMapper.insert(newDisp);
//					}
//						
//				}
//			}
					
//			insertAudit("insert", "decorso", "insert("+decorso.getIdDecorso()+")", currentUser.getCfUtente(),decorso, req);
			
//			if (messageReturn!=null)
//				return Response.ok().entity(new Message(messageReturn)).build();
//			else
			return Response.ok(decorso).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Error generic", e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}
	
	
	@Override
	public Response updateDecorso(Long idDecorso, DecorsoForElenco decorso, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			
			decorso.setIdDecorso(idDecorso);
			
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			SoggettoForElenco soggetto = soggettoMapper.selectForElencoByIdSoggettoASR(decorso.getIdSoggetto(), currentUser.getIdAsr() != null ? currentUser.getIdAsr() : -1 );
			
			if (soggetto == null || soggetto.getIdAsr() ==null) {
//				Response resp = Response.status(Status.FORBIDDEN).build();
//				throw new WebApplicationException(resp);
				
				return ErroreBuilder.from(Status.FORBIDDEN).title("Paziente non appartiene alla ASR del medico")
						.dettaglio("id_asr", ""+currentUser.getIdAsr())
						.dettaglio("id_soggetto", ""+decorso.getIdSoggetto())
						.exception().getResponse();
			}
			
			
			//verifico l'inseribilità del decorso
			checkInseribilitaEvento(decorso.getIdSoggetto(), decorso);
			
			int ret = decorsoMapper.updateByPrimaryKey(decorso);
			log.info("updateDecorso - idDecorso: "+decorso.getIdDecorso() + " ret:"+ret);
			insertAudit("update", "decorso", "update("+decorso.getIdDecorso()+")", currentUser.getCfUtente(), decorso, req);

			return Response.ok(decorso).build();
		} catch (Exception e) {
			log.error("Errore update decorso", e);
			throw new RuntimeException("Errore update decorso",e);
		}
	}


	@Override
	public Response deleteDecorso(Long idDecorso, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		
		

		Decorso decorso = decorsoMapper.selectByPrimaryKey(idDecorso);
		if(decorso==null) {
			//return 404
			return ErroreBuilder.from(WebMessages.ENTITA_NON_TROVATA)
					.title("Decorso non trovato.")
					.dettaglio("E1", "Decorso non trovato con id "+idDecorso)
					.exception().getResponse();
		}
		
		//DecodeTipoEventoMapper
		// se il Decorso ha questi tipi evento non si può cancellare: 11 Segnalazione, 12 Presa in carico, 14 Da Completare
		Integer ite = decorso.getIdTipoEvento();
		if(ite !=null && (ite.equals(11) || ite.equals(12)|| ite.equals(14))) { 
			//return non si puo' cancellare
			return ErroreBuilder.from(Status.BAD_REQUEST)
					.title("Decorso non eliminabile.")
					.dettaglio("E1", "Decorso non eliminabile con id "+idDecorso + " idTipoEvento: "+ite)
					.exception().getResponse();
		}
		
		UserLogged currentUser = getBeService().getCurrentUser(req);
		
		Soggetto soggetto = soggettoMapper.selectByPrimaryKey(decorso.getIdSoggetto());
		Long idAsrSoggetto = soggetto.getIdAsr();
		
		if(currentUser.getIdAsr().compareTo(idAsrSoggetto)!=0) {
			List<SoggettoAsr> soggettiAsr = soggettoAsrMapper.selectByIdSoggetto(soggetto.getIdSoggetto());
			if(soggettiAsr==null || soggettiAsr.isEmpty()) {
				return ErroreBuilder.from(Status.BAD_REQUEST)
						.title("Decorso non eliminabile. Il Soggetto non appartiena alla tua ASR")
						.dettaglio("E1", "Decorso non eliminabile con id "+idDecorso + " idAsr: "+currentUser.getIdAsr() + " idAsrSoggetto: "+idAsrSoggetto)
						.exception().getResponse();
			}
			boolean trovato = false;
			for(SoggettoAsr sa : soggettiAsr) {
				if(currentUser.getIdAsr().compareTo(sa.getIdAsr())==0) {
					trovato = true;
					break;
				}
			}
			if(!trovato) {
				return ErroreBuilder.from(Status.BAD_REQUEST)
						.title("Decorso non eliminabile. Il Soggetto non appartiena alla tua ASR")
						.dettaglio("E1", "Decorso non eliminabile con id "+idDecorso + " idAsr: "+currentUser.getIdAsr() + " idAsr lista")
						.exception().getResponse();
			}
		}
		
		
		//Effettuo la delete
		
		int deleteCount;
		try {
			deleteCount = decorsoMapper.deleteByPrimaryKey(idDecorso);
			log.info("deleted idDecorso:"+ idDecorso + " [deleteCount: "+deleteCount + "]");
		} catch (RuntimeException re) {
			log.error("Errore cancellazione decorso con id: "+idDecorso, re);
			return ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).throwable(re).exception().getResponse();
		}
		
		insertAudit("delete", "decorso", "delete("+idDecorso+")", currentUser.getCfUtente(), null, req);
		
		
		return Response.ok(deleteCount).build();
	}

	
	
	/**
	 * Controlla che l'evento inserito non sia successivo ad un evento di tipo decesso e vv
	 * @param idSoggetto
	 * @param decorso
	 */
	private void checkInseribilitaEvento(Long idSoggetto, DecorsoForElenco decorso) {
		
		if(idSoggetto == null || idSoggetto <= 0) {
			log.info("Decorso senza idSoggetto");
			return;
		}
		
		if(decorso == null || decorso.getIdTipoEvento() == null) {
			log.info("ID Tipo Evento obbligatorio");
			return;
		}
		
		//recupero gli eventi del soggetto
		List<DecorsoForElenco> listDecorsi = decorsoMapper.selectForElencoByIdSoggetto(idSoggetto);
		
		//se l'evento che si vuole inserire è DECESSO
		if(decorso.getIdTipoEvento().intValue() == ID_TIPO_EVENTO_DECEDUTO_COVID
			|| decorso.getIdTipoEvento().intValue() == ID_TIPO_EVENTO_DECEDUTO_NON_COVID) {
			
			for(DecorsoForElenco dfe : listDecorsi) {
				
				//verifico che non ci sia già un altro decesso inserito
				if(dfe.getIdTipoEvento()!=null && 
						(dfe.getIdTipoEvento().intValue() == ID_TIPO_EVENTO_DECEDUTO_COVID
						|| dfe.getIdTipoEvento().intValue() == ID_TIPO_EVENTO_DECEDUTO_NON_COVID)) {
					log.info("Impossibile inserire decesso per soggetto "+idSoggetto+". Un evento decesso è già presente per questo soggetto.");
					throw new WebApplicationException(
							Response.status(Status.BAD_REQUEST)
							.entity(new Message("Impossibile inserire decesso per soggetto "+idSoggetto+". Un evento decesso è già presente per questo soggetto.")).build());
				}
				
				//controllo che non ci siano eventi con inizio successivo al decesso
				if(dfe.getDataDimissioni()!=null && 
					dfe.getDataDimissioni().after(decorso.getDataDimissioni()) ) {
					log.info("Impossibile inserire decesso per soggetto "+idSoggetto+". Esistono altri decorsi con data di inizio successiva al decesso.");
					throw new WebApplicationException(
							Response.status(Status.BAD_REQUEST)
							.entity(new Message("Impossibile inserire decesso per soggetto "+idSoggetto+". Esistono altri decorsi con data di inizio successiva al decesso.")).build());
				}
			}
		}
		
		//se l'evento che si vuole inserire NON è un decesso
		if(decorso.getIdTipoEvento().intValue() != ID_TIPO_EVENTO_DECEDUTO_COVID
			&& decorso.getIdTipoEvento().intValue() != ID_TIPO_EVENTO_DECEDUTO_NON_COVID) {
			
			for(DecorsoForElenco dfe : listDecorsi) {
				
				//verifico che non ci sia già un decesso inserito
				if(dfe.getIdTipoEvento()!=null && 
						(dfe.getIdTipoEvento().intValue() == ID_TIPO_EVENTO_DECEDUTO_COVID
						|| dfe.getIdTipoEvento().intValue() == ID_TIPO_EVENTO_DECEDUTO_NON_COVID)) {
					log.info("Impossibile inserire decorso per soggetto "+idSoggetto+". Un evento decesso è presente per questo soggetto.");
					throw new WebApplicationException(
							Response.status(Status.BAD_REQUEST)
							.entity(new Message("Impossibile inserire decorso per soggetto "+idSoggetto+". Un evento decesso è presente per questo soggetto.")).build());
				}
				
			}
		}
		
	}


}
