package it.csi.gestionepazienti.gestionepazientiapi.batch.referti;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import it.csi.gestionepazienti.gestionepazientiapi.batch.BaseBatch;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.AuraComponent;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asl;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.MedicoSoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RSoggettoIstituto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RSoggettoPersonaleScolasticoIstituto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoFromElencoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.notifica.Notifica;
import it.csi.gestionepazienti.gestionepazientiweb.dto.personalescolastico.SoggettoPersonaleScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.Elaborazione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.Referto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.RefertoDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.RefertoElaborazione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.test.Test;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AslMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AsrMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecorsoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.MedicoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.MedicoSoggettoAuraMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.RSoggettoPersonaleScolasticoIstitutoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoAuraMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoIstitutiScolasticiMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.notifica.generated.NotificaMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.personalescolastico.generated.SoggettoPersonaleScolasticoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.ElaborazioneMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoDettaglioMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoElaborazioneMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.test.generated.TestMapper;

@Component
public class RefertiBatch extends BaseBatch {

	//private static final String TESTO_REQUEST_SIERO = "Programma di screening: paziente positivo al test sierologico. Sarà previsto isolamento fiduciario";
	private static final String TESTO_REQUEST_SIERO = "Programma di screening: paziente positivo al test sierologico. Valutare necessità di isolamento fiduciario";
	private static final String TESTO_REQUEST_TAMPONE = "Programma di screening: paziente positivo al tampone.";
	private static final String[] CODICI_SIERO = { "91.31.c", "91.31.d" };
	public static final ImmutableList<String> CODICI_TIPO_RICHIESTA = ImmutableList.of("01", "02", "03", "04", "08", "09"); //Controlla query, non parametrica
	private static final String DECORSO_NOTE = "Richiesto isolamento fiduciario di 14GG  a seguito dell'esito positivo del test sierologico.";
	private static final String LOG_HEADER = "[REFERTI BATCH]: ";
	private static final String CF_WHOAMI = "BATCHREFERTI0001";
	private static final String ELAB_FUNC_DESC = "Batch di elaborazione referti.";
	private static final String WHOAMI = "RefertiBatch DOREFERTO";

	private static final Logger logger = Logger.getLogger(RefertiBatch.class);
	

	@Autowired
	SoggettoMapper soggettoMapper;
	
	@Autowired
	MedicoSoggettoAuraMapper medicoSoggettoAuraMapper;

	@Autowired
	RefertoMapper refertoMapper;

	@Autowired
	NotificaMapper notificaMapper;

	@Autowired
	RefertoDettaglioMapper refertoDettaglioMapper;

	@Autowired
	SoggettoAuraMapper soggettoAuraMapper;

	@Autowired
	MedicoMapper medicoMapper;

	@Autowired
	DecorsoMapper decorsoMapper;

	@Autowired
	RefertoElaborazioneMapper refertoElaborazioneMapper;

	@Autowired
	ElaborazioneMapper elaborazioneMapper;

	@Autowired
	TestMapper testMapper;
	
	@Autowired
	private AuraComponent auraComponent;
	
	@Autowired 
	AsrMapper asrMapper;
	
	@Autowired
	AslMapper aslMapper;
	
	@Autowired
	SoggettoPersonaleScolasticoMapper soggettoPersonaleScolasticoMapper;
	
	@Autowired
	RSoggettoPersonaleScolasticoIstitutoMapper rSoggScolasticoIstitutoMapper;
	
	@Autowired
	SoggettoIstitutiScolasticiMapper soggettoIstitutiScolasticiMapper;

	public void doBatchBase() {
		logger.info(LOG_HEADER + "| INIZIO BATCH");
		
		// Aggiunta nella select UNION 
		logger.info(LOG_HEADER + "| Eseguo query modificata ");
		List<Referto> referti = refertoMapper.selectPositiviNotElabAndSceening(CODICI_TIPO_RICHIESTA);
		
		if (referti.isEmpty()) {
			logger.info(LOG_HEADER + "| NON ci sono referti da ELABORARE! ");
		} else {
			logger.info(LOG_HEADER + "| CI SONO >> " + referti.size() + " << referti da ELABORARE.");
			
			for (Referto referto : referti) {
				if(referto.getCodfisc()!=null && referto.getCodfisc().equalsIgnoreCase("LBRWTR88T13C514K")) {
					logger.info(LOG_HEADER + "| trovato cf LBRWTR88T13C514K con idMessaggio: " + referto.getIdMessaggio());
					break;

				}
			}
			
			Date dataInizio = new Date();
			Elaborazione elaborazione = addElaborazione(dataInizio, ELAB_FUNC_DESC);
			for (Referto referto : referti) {
				if(referto.getCodfisc()!=null) {
					doRefertoBatch(referto, elaborazione);
				}
			}
			updateElaborazione(elaborazione);
		}
		
		logger.info(LOG_HEADER + "END BATCH");
	}

	private void updateElaborazione(Elaborazione elaborazione) {
		elaborazione.setElabFine(new Date());
		elaborazione.setElabExitCode("OK");
		elaborazioneMapper.updateByPrimaryKey(elaborazione);
	}

	private Elaborazione addElaborazione(Date dataInizio, String message) {
		Elaborazione elaborazione = new Elaborazione();
		elaborazione.setElabInizio(dataInizio);
		elaborazione.setElabFine(new Date());
		elaborazione.setElabFunctionName(message);
		elaborazione.setDataCreazione(dataInizio);
		elaborazione.setUtenteOperazione(WHOAMI);
		elaborazioneMapper.insert(elaborazione);
		return elaborazione;

	}

	private void doRefertoBatch(Referto referto, Elaborazione elaborazione) {
		boolean refertoSuccess = true;
		Integer scarto = null;
		logger.info(LOG_HEADER + "| Inizio doRefertoBatch");
		
		List<SoggettoForElenco> soggetti;
		
		if (referto.getCodfisc().equalsIgnoreCase("NON-POSSIEDE-CF")) {
			logger.info(LOG_HEADER + "| Leggo il soggetto, Eseguo query per condizione < cf = NON-POSSIEDE-CF > ");
			soggetti = soggettoMapper.selectForElencoByIdAsrPaged(null, null, referto.getCognome(), referto.getNome(), null, referto.getDatanascita(),
					null, null, null, null, null, null, null);
		} else {
			logger.info(LOG_HEADER + "| Leggo il soggetto, eseguo query per cf : " + referto.getCodfisc());
			soggetti = soggettoMapper.selectForElencoByIdAsrPaged(null, referto.getCodfisc(), null, null, null, null,
					null, null, null, null, null, null, null);
		}
		
				
		SoggettoForElenco soggetto;
		if (soggetti != null && !soggetti.isEmpty()) {
			logger.info(LOG_HEADER + "| Il soggetto è a sistema.");			
			soggetto = soggetti.get(0);
			// il soggetto è già a sistema
		} else if(referto.getCodfisc().equalsIgnoreCase("NON-POSSIEDE-CF")) {
			//soggetto senza cf non a sistema, NON cerco su Aura
			logger.info(LOG_HEADER + "| Il soggetto non è a sistema ed è sprovvisto di CF. Recupero le info dal referto.");
			referto.setCodfisc(null);
			soggetto = toSoggettoForElenco(referto);
			insertSoggerroFromReferto(referto, soggetto);
			
		} else {
			// il soggetto non è a sistema -> lo cerco su AURA
			logger.info(LOG_HEADER + "| Il soggetto non è a sistema,lo cerco su AURA.");	
			List<SoggettoFromElencoAura> listSoggetti = null;
			try {
				listSoggetti = auraComponent.findProfiliAnagrafici(referto.getCodfisc());
				
			} catch (Exception e) {
				logger.error(LOG_HEADER + "| RefertiBatch: error in: ", e);
			}
			if (listSoggetti != null && !listSoggetti.isEmpty()) {
				SoggettoFromElencoAura soggettoFromElencoAura = listSoggetti.get(0);
				soggetto = insertSoggettoFromAura(soggettoFromElencoAura, referto);

			} else {
				// il soggetto non è su aura
				logger.info(LOG_HEADER + "| Il soggetto non è presente su AURA. Recupero le info dal referto e inserisco il soggetto.");
				soggetto = toSoggettoForElenco(referto);
				logger.info(LOG_HEADER + "| Preparo la insert");
				insertSoggerroFromReferto(referto, soggetto);
				logger.info(LOG_HEADER + "| Fine insertSoggerroFromReferto");
			}
		}
		
		// Se il soggetto fa parte degli scolastici, integro le informazioni mancanti
		if(referto.getCodTipoRichiesta()!=null && referto.getCodTipoRichiesta().equalsIgnoreCase("05")) {
			logger.info(LOG_HEADER + "| Il soggetto è un soggetto scolastico. Completo le informazioni mancanti sulle tabelle");
			
			//inserisco l'idSoggetto nella tabella del personale scolastico
			SoggettoPersonaleScolastico soggettoScolastico = soggettoPersonaleScolasticoMapper.selectByCodiceFiscale(referto.getCodfisc());
			soggettoScolastico.setIdSoggettoFk(soggetto.getIdSoggetto());
			soggettoPersonaleScolasticoMapper.updateByPrimaryKey(soggettoScolastico);
			
			//prendo tutte le relazioni dalla tabella che lega soggetti scolastici e istituti
			//e le ribalto su quella che lega soggetti e istituti
			List<RSoggettoPersonaleScolasticoIstituto> relazioniSoggettiScolastici = rSoggScolasticoIstitutoMapper.selectByIdSoggettoScolastico(soggettoScolastico.getIdSoggetto());
			for(RSoggettoPersonaleScolasticoIstituto r : relazioniSoggettiScolastici) {
				
				RSoggettoIstituto s = new RSoggettoIstituto();
				s.setIdSoggetto(soggetto.getIdSoggetto().intValue());
				s.setDataCancellazione(r.getDataCancellazione());
				s.setDataCreazione(r.getDataCreazione());
				s.setDataModifca(r.getDataModifca());
				s.setIdIstituto(r.getIdIstituto());
				s.setUtenteOperazione(r.getUtenteOperazione());
				s.setValiditaFine(r.getValiditaFine());
				s.setValiditaInizio(r.getValiditaInizio());
				
				soggettoIstitutiScolasticiMapper.insert(s);
				
			}
			
			logger.info(LOG_HEADER + "| Fine lavorazione soggetto scolastico");
		}
		
		
		
		RefertoDettaglio dettaglio = new RefertoDettaglio();
		if(referto != null && referto.getIdMessaggio()!=null) {
			logger.info(LOG_HEADER + "| Leggo il dettaglio del referto, IdMessaggio: " + referto.getIdMessaggio());
			dettaglio = refertoDettaglioMapper.selectByIdMessaggio(referto.getIdMessaggio());			
		} else {
			logger.info(LOG_HEADER + "| IdMessaggio NULL. Salto la ricerca del dettaglio referto");
		}
		
		
		if(refertoSuccess) {
			//notifica(RefertiEnum.EXT, RefertiEnum.SISP, soggetto, isSiero(dettaglio), referto);
			//notifica(RefertiEnum.EXT, RefertiEnum.MMG, soggetto, isSiero(dettaglio), referto);
			
			boolean successNotificaMMG = false;
			boolean successNotificaSISP = false;
			logger.info(LOG_HEADER + "| valueCode:  "+ dettaglio.getValueCode());
			if(dettaglio.getValueCode().equalsIgnoreCase("P")) {
				logger.info(LOG_HEADER + "| Invio notifiche (solo per referto.dettaglio.getValueCode = P) ");
				successNotificaSISP = notifica(RefertiEnum.EXT, RefertiEnum.SISP, soggetto, isSiero(dettaglio), referto);
				successNotificaMMG = notifica(RefertiEnum.EXT, RefertiEnum.MMG, soggetto, isSiero(dettaglio), referto);
			}
			
			addTest(referto, dettaglio, soggetto);
//			if (dettaglio.getCodeCode().equalsIgnoreCase("91.31.c") || dettaglio.getCodeCode().equalsIgnoreCase("91.31.d")) {
//				addIsolamentoFiduciario(soggetto);
//				logger.info(LOG_HEADER + "inserito isolamento fiduciario.");
//				
//			}
			if (!successNotificaMMG && 
					( dettaglio.getCodeCode().equalsIgnoreCase("91.31.c") 
							|| dettaglio.getCodeCode().equalsIgnoreCase("91.31.d"))) {
				addIsolamentoFiduciario(soggetto);
				logger.info(LOG_HEADER + "inserito isolamento fiduciario.");
				
			}
			addElaborazioneReferto(dettaglio, elaborazione, null);
			
			logger.info(LOG_HEADER + "elaborazione ok.");
		}else {
			logger.info(LOG_HEADER + "inserisco in elaborazione lo scarto.");
			addElaborazioneReferto(dettaglio, elaborazione, scarto);
			
		}
	}

	private void insertSoggerroFromReferto(Referto referto, SoggettoForElenco soggetto) {
		
		try {
			logger.info(LOG_HEADER + "| Begin insert soggetto");
			soggettoMapper.insert(soggetto);
			logger.info(LOG_HEADER + "| End insert");

			// inserisco il mapping tra idAura e idSoggetto
			if (referto.getIdAura() != null) {
				SoggettoAura rSoggettoAura = new SoggettoAura();
				rSoggettoAura.setIdSoggetto(soggetto.getIdSoggetto());
				rSoggettoAura.setIdAura(toLong(referto.getIdAura()));
				soggettoAuraMapper.insert(rSoggettoAura);
			}

		} catch (Exception e) {
			logger.info(LOG_HEADER + "| ERRORE INSERT: ", e);
			throw e;
		}
	}

	private SoggettoForElenco insertSoggettoFromAura(SoggettoFromElencoAura soggettoAura, Referto referto) {
		SoggettoForElenco soggetto;
		SoggettoAura rSoggettoAura = null;
		try {
			soggetto = auraComponent.extractProfiloSanitario(soggettoAura.getProfiloAnagrafico().toString(),true);
			List<Asl> asls = aslMapper.selectAll();
			if (asls != null) {
				for (Asl asl : asls) {
					if (soggetto!=null && asl!=null && asl.getDescAslEstesa()!=null && asl.getDescAslEstesa().equalsIgnoreCase(soggetto.getAslDomicilio())) {
						soggetto.setIdAsr(asl.getIdAsr()); // Nota e' null quando e' ASL fuori regione
					}
				}
			}
			rSoggettoAura = new SoggettoAura();
			rSoggettoAura.setIdAura(toLong(soggettoAura.getProfiloAnagrafico().toString()));
			logger.info(LOG_HEADER + "Il soggetto è presente su AURA");		
			
		} catch (Exception e) {
			logger.error(LOG_HEADER + "non ho trovato i dettagli del soggetto da aura, restituisco soggetto senza dettagli", e);
			soggetto = toSoggettoForElenco(soggettoAura);			
		}
		
		if (soggetto.getIdAsr() == null) {
			
			
			if(referto.getAsr() != null) {
				soggetto.setIdAsr(asrMapper.findByIdAsr(toLong(referto.getAsr())));			
			}else {
				soggetto.setIdAsr(asrMapper.findByIdAsr(toLong(referto.getAslLaboratorio())));
			}

			//soggetto.setIdAsr(asrMapper.findByIdAsr(toLong(referto.getAsr())));
		}
		soggettoMapper.insert(soggetto);
		if(rSoggettoAura!=null) {
			rSoggettoAura.setIdSoggetto(soggetto.getIdSoggetto());
			soggettoAuraMapper.insert(rSoggettoAura);			
		}
		return soggetto;
	}

	private void addTest(Referto referto, RefertoDettaglio refertoDettaglgio, SoggettoForElenco soggetto) {
		logger.info(LOG_HEADER + "| Inizio addTest");
		Test test = new Test();
		logger.info(LOG_HEADER + "| idSoggetto: " + soggetto.getIdSoggetto());
		logger.info(LOG_HEADER + "| refertoDettaglgio.intfrdetId: " + refertoDettaglgio.getIntfrdetId());
		logger.info(LOG_HEADER + "| refertoDettaglgio.ValueCode: " + refertoDettaglgio.getValueCode());
		test.setIdSoggetto(soggetto.getIdSoggetto());
		test.setIntfrdetId(refertoDettaglgio.getIntfrdetId());
		test.setTestLaboratorioDesc(referto.getDescrStruttura());
		test.setTestRangeRiferimento(refertoDettaglgio.getRefrangeText());
		test.setTestCodiceEsito(refertoDettaglgio.getValueCode());
		test.setTestDataEsecuzione(refertoDettaglgio.getEffectivetimeValue());
		test.setTestValore(refertoDettaglgio.getValueValue());
		test.setTestUnitaMisura(refertoDettaglgio.getValueUnit());
		test.setTestProgramma(referto.getProgramma());
		test.setTestEsitoId(refertoMapper.decodeValueCode(refertoDettaglgio.getValueCode()));
		test.setTestRichiestaTipoId(refertoMapper.decodeCodTipoRichiesta(referto.getCodTipoRichiesta())); // 01/02/03/04/05
		String tipoTest = isSiero(refertoDettaglgio) ? "SIEROLOGICO" : "TAMPONE";
		test.setTestTipoId(refertoMapper.decodeTipoTest(tipoTest));
		test.setDataCreazione(new Date());
		test.setDataModifica(new Date());
		test.setUtenteOperazione(WHOAMI);
		
		logger.info(LOG_HEADER + "| TestEsitoId: " + test.getTestEsitoId());
		logger.info(LOG_HEADER + "| TestRichiestaTipoId: " + test.getTestRichiestaTipoId());
		logger.info(LOG_HEADER + "| TipoTest: " + tipoTest);
		
		testMapper.insert(test);
		
		logger.info(LOG_HEADER + "| Fine addTest");
	}

	private void addElaborazioneReferto(RefertoDettaglio dettaglio, Elaborazione elaborazione, Integer scartoMotivoId) {

		logger.info(LOG_HEADER + "| Inizio addElaborazioneReferto");
		RefertoElaborazione refertoElaborazione = new RefertoElaborazione();
		refertoElaborazione.setDataCreazione(new Date());
		refertoElaborazione.setElabId(elaborazione.getElabId());
		refertoElaborazione.setIntfrdetId(dettaglio.getIntfrdetId());
		refertoElaborazione.setDataCreazione(new Date());
		refertoElaborazione.setUtenteOperazione(WHOAMI);
		if (scartoMotivoId == null) {
			refertoElaborazione.setElabdetScarto(false);
		} else {
			refertoElaborazione.setElabdetScarto(true);
			refertoElaborazione.setScartomotivoId(scartoMotivoId);
		}
		
		logger.info(LOG_HEADER + "| Inizio chiusura scarto");
		refertoElaborazioneMapper.chiudiScarto(refertoElaborazione.getIntfrdetId());
		logger.info(LOG_HEADER + "| Chiusura scarto completata");
		
		refertoElaborazioneMapper.insert(refertoElaborazione);
		logger.info(LOG_HEADER + "| Fine addElaborazioneReferto");
	}

	private boolean isSiero(RefertoDettaglio dettaglio) {
		for (String codiceSiero : CODICI_SIERO) {
			if (dettaglio.getCodeCode().equalsIgnoreCase(codiceSiero)) {
				return true;
			}
		}
		return false;
	}

	private SoggettoForElenco toSoggettoForElenco(Referto referto) {
		SoggettoForElenco ret = new SoggettoForElenco();
		ret.setCodiceFiscale(referto.getCodfisc());
		ret.setNome(referto.getNome());
		ret.setCognome(referto.getCognome());
		ret.setDataNascita(referto.getDatanascita());
		if(referto.getAsr() != null) {
			ret.setIdAsr(asrMapper.findByIdAsr(toLong(referto.getAsr())));			
		}else {
			ret.setIdAsr(asrMapper.findByIdAsr(toLong(referto.getAslLaboratorio())));
		}
		
		/*
		 * TODO COMMENTATO PERCHE I DATI CSV NON SONO COERENTI CON I CODICI TABELLA COMUNi
		ret.setComuneResidenzaIstat(referto.getResIstat());			
		ret.setComuneDomicilioIstat(referto.getDomIstat());
		ret.setIndirizzoDomicilio(referto.getDomAddr());*/
		
		return ret;
	}

	private SoggettoForElenco toSoggettoForElenco(SoggettoFromElencoAura soggettoFromElencoAura) {
		SoggettoForElenco ret = new SoggettoForElenco();
		ret.setCodiceFiscale(soggettoFromElencoAura.getCodiceFiscale());
		ret.setNome(soggettoFromElencoAura.getNome());
		ret.setCognome(soggettoFromElencoAura.getCognome());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		try {
			ret.setDataNascita(df.parse(soggettoFromElencoAura.getDataNascitaStr()));
		} catch (ParseException e) {
			logger.error(LOG_HEADER + "data di nascita non impostata. ", e);
		}

		return ret;
	}

	private boolean notifica(RefertiEnum mittente, RefertiEnum destinatario, SoggettoForElenco soggetto, boolean siero,
			Referto referto) {
		
		boolean returnStatus = false;
		
		//FIXME: Raffa gli id andranno sostituiti con i codici
		Notifica notifica = new Notifica();
		notifica.setUtenteOperazione(WHOAMI);
		notifica.setCfUtenteRichiesta(CF_WHOAMI);
		notifica.setNotDataNotifica(new Date());
		
		notifica.setNotUtTipoId(mittente.getValue());
		notifica.setNotUtTipoIdDest(destinatario.getValue());
		
		notifica.setIdSoggetto(soggetto.getIdSoggetto().intValue());
		notifica.setNotTipoId(RefertiEnum.INFORMATIVA.getValue());
		//notifica.setPrioritaId(RefertiEnum.PRIORITA_MEDIA.getValue());
		notifica.setPrioritaId(RefertiEnum.PRIORITA_ALTA.getValue());
		notifica.setDataCreazione(new Date());
		notifica.setDataModifica(new Date());
		notifica.setNotEvId(RefertiEnum.SCREENING.getValue());
		// se sisp aggiungo idEnte. codice asl coincide con codiceEnte
		if (siero) {
			notifica.setNotTestoRequest(TESTO_REQUEST_SIERO);
		} else {
			notifica.setNotTestoRequest(TESTO_REQUEST_TAMPONE);
		}
		notifica.setNotAzId(1); // raffa aggiunto presa visione
		if (destinatario.equals(RefertiEnum.MMG)) {
			returnStatus = notificaToMMG(soggetto, siero, notifica, referto.getIdAura());
		} else if (destinatario.equals(RefertiEnum.SISP)) {
			returnStatus = notificaToSISP(soggetto, referto.getAslLaboratorio(), notifica);
		} else {
			logger.info(LOG_HEADER + "notifica non inviata, il destinatario non è ne SISP ne MMG");
		}
		
		return returnStatus;

	}

	private boolean notificaToMMG(SoggettoForElenco soggetto, boolean siero, Notifica notifica, String refertoIdAura) {
		
		boolean returnStatus = false;
		String idAura = null;
		if(refertoIdAura != null) {
			idAura = refertoIdAura;
		} else {
			SoggettoForDettaglio dettaglio = getDettaglioSoggettoByIdSoggettoIdAsr(soggetto.getIdSoggetto(),
					soggetto.getIdAsr());
			if(dettaglio != null && dettaglio.getIdAura()!=null) {					
					idAura = dettaglio.getIdAura().toString();
			}
		}
		if (idAura == null) {
			logger.error(LOG_HEADER + "Id aura non valorizzato: impossibile inviare notifica a MMG");

		} else {

			Medico medico = null;
			try {
				medico = auraComponent.extractMedicoFromAura(idAura);
				
				/**
				 * Aggiorno legami medico e soggetto
				 */
				if (medico != null ) 
					aggiornamentoRMedicoSoggetto(medico, soggetto.getIdSoggetto(), Long.valueOf(idAura));
				
			} catch (Exception e) {
				logger.error(LOG_HEADER + "eccezione aura nell'estrazione del medico", e);
				logger.error(LOG_HEADER
						+ "notifica MMG non inviata. Il soggetto non ha idAura: impossibile trovare il medico.");
			}
			
			if (medico != null && medico.getCfMedico()!=null) {
				
				notifica.setCfUtenteDest(medico.getCfMedico()!=null ? medico.getCfMedico() : ""); // raffa scommentato
				notificaMapper.insert(notifica);
// 				Raffa --> Portato fuori all'altezza di addTest e addElaborazione
//				if (siero) {
//					addIsolamentoFiduciario(soggetto);
//					logger.info(LOG_HEADER + "inserito isolamento fiduciario.");
//					logger.info(LOG_HEADER + "inserita notifica MMG. ");
//				}
				returnStatus = true;
			}
			
		}
		return returnStatus;
	}	

//	private void notificaIsolamentoFiduciario(Notifica notifica) {
//		notifica.setNotEvId(RefertiEnum.ISOLAMENTO.getValue());
//		notifica.setNotUtTipoIdDest(RefertiEnum.MMG.getValue());
//		notificaMapper.insert(notifica);
//		notifica.setNotUtTipoIdDest(RefertiEnum.SISP.getValue());
//		notificaMapper.insert(notifica);
//	}

	private boolean notificaToSISP(SoggettoForElenco soggetto, String aslLaboratorio, Notifica notifica) {
		boolean returnStatus = false;
		Integer idEnte = null;
		if (soggetto.getAslDomicilio() != null) {
			idEnte = soggettoMapper.selectEnteByIdSoggetto(soggetto.getIdSoggetto());
			if (idEnte == null) {
				idEnte = toInteger(aslLaboratorio);
			}
		} else {
			idEnte = toInteger(aslLaboratorio);
		}
		if (idEnte != null) {
			notifica.setIdEnte(idEnte);
			notificaMapper.insert(notifica);
			returnStatus = true;
			logger.info(LOG_HEADER + "Notifica al SISP inviata!");
		} else {
			logger.error(LOG_HEADER + "Notifica al SISP non inviata, idEnte non valorizzato");
		}
		
		return returnStatus;
	}

	private void addIsolamentoFiduciario(SoggettoForElenco soggetto) {
		logger.info(LOG_HEADER + "inserimento isolamento fiduciario. ");
		Decorso decorso = new Decorso();
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		decorso.setIdTipoEvento(RefertiEnum.ISOLAMENTO_FID.getValue());
		decorso.setIdSoggetto(soggetto.getIdSoggetto());
		decorso.setDataEvento(now);
		decorso.setDataDimissioni(now);
		decorso.setDataPrevFineEvento(getDateOffset(now, 14));
		decorso.setNote(DECORSO_NOTE);
		decorsoMapper.insert(decorso);
		logger.info(LOG_HEADER + "inserito isolamento fiduciario. ");

	}

	private Date getDateOffset(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, offset);
		return calendar.getTime();
	}

	private SoggettoForDettaglio getDettaglioSoggettoByIdSoggettoIdAsr(Long idSoggetto, Long idAsr) {
		SoggettoForElenco soggettoForElenco = soggettoMapper.selectForElencoByIdSoggetto(idSoggetto, idAsr);
		if (soggettoForElenco == null) {
			// il soggetto non è a sistema quindi non avrò un dettaglio ritorno null
			return null;
		}

		List<SoggettoAura> elencoSoggettoAura = soggettoAuraMapper.selectByIdSoggetto(idSoggetto);

		Long idAura = null;
		
		// Dovrebbe esserci al piu un solo soggettoAura ma il db ne permette piu di uno.
		// scelgo il primo e loggo
		if (elencoSoggettoAura != null && !elencoSoggettoAura.isEmpty()) {
			idAura = elencoSoggettoAura.get(0).getIdAura();			
		}

		SoggettoForDettaglio dettaglio = new SoggettoForDettaglio(soggettoForElenco, null, null, null);
		dettaglio.setIdAura(idAura);
		return dettaglio;
	}

	private Long toLong(String str) {
		return toLong(str, null);
	}

	private Long toLong(String str, Long def) {
		if (StringUtils.isBlank(str)) {
			return def;
		}
		try {
			return Long.valueOf(str);
		} catch (NumberFormatException nfe) {
			return def;
		}
	}

	private Integer toInteger(String str) {
		return toInteger(str, null);
	}

	private Integer toInteger(String str, Integer def) {
		if (StringUtils.isBlank(str)) {
			return def;
		}
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException nfe) {
			return def;
		}
	}
	
	
	
	//fixme aggiungere metodo per aggiungere rel medico soggetto
	//public Medico aggiornamentoRMedicoSoggetto(Medico medicoFromAura, Soggetto soggetto,Long idAura) throws Exception {
	public Medico aggiornamentoRMedicoSoggetto(Medico medicoFromAura, Long idSoggetto,Long idAura) throws Exception {
		
		if (medicoFromAura!=null && 
				medicoFromAura.getIdMedico()!=null 
				&& medicoFromAura.getCfMedico()!=null) // FIXME: occhio qui!
		{
			
			logger.info(LOG_HEADER + " Richiesta inserimento medico:  " +medicoFromAura.getIdMedico()+" per soggetto: "+ idSoggetto  +" idAura:"+idAura);
			// 1) controllo se il legame esiste
			MedicoSoggettoAura medicoSoggettoAura = medicoSoggettoAuraMapper.selectByPrimaryKey(medicoFromAura.getIdMedico(), idAura);
			if (medicoSoggettoAura != null) {
				// non faccio nulla
				logger.info(LOG_HEADER + " Legame Medico  soggetto "+ idSoggetto + " / "+medicoFromAura.getIdMedico() +" presente.");							
			
			} else {
				// 2) Controllo se il medico esiste 
				if (medicoMapper.selectByPrimaryKey(medicoFromAura.getIdMedico())!=null) {
					
					logger.info(LOG_HEADER + " Medico richiesto per soggetto " + idSoggetto + " trovato "+medicoFromAura.getIdMedico());							
				
				}else {
					
					logger.info(LOG_HEADER + " Medico richiesto per soggetto "+ idSoggetto +" non trovato : inserimento in corso");							
					medicoMapper.insert(medicoFromAura);
				}
				
				logger.info(LOG_HEADER + " Legame Medico  soggetto "+idSoggetto+" / "+medicoFromAura.getIdMedico() +" NON presente.");							
				medicoSoggettoAuraMapper.deleteByIdAuraSogg(idAura);
				logger.info(LOG_HEADER + " Eliminati legami precedenti "+idSoggetto);	
				medicoSoggettoAura = new MedicoSoggettoAura();
				medicoSoggettoAura.setIdAuraSogg(idAura);
				medicoSoggettoAura.setIdMedico(medicoFromAura.getIdMedico());
				medicoSoggettoAuraMapper.insert(medicoSoggettoAura);
				logger.info(LOG_HEADER + " Legame Medico  soggetto "+idSoggetto+" / "+medicoFromAura.getIdMedico() +" inserito.");	
			}
			
		} else {
			logger.info(LOG_HEADER+ " Medico non presente su AURA per soggetto " + idSoggetto + ", non faccio nulla.");
		}
		return medicoFromAura;
	}

}
