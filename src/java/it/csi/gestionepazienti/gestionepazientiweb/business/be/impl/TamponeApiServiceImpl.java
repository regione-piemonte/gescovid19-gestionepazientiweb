package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toBoolean;
import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toFlag;
import static it.csi.gestionepazienti.gestionepazientiweb.mapper.util.MapperUtil.toLike;

import java.util.ArrayList;
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
import it.csi.gestionepazienti.gestionepazientiweb.business.be.TamponeApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAsr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Tampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TamponeMotivo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElencoNonAbbinati;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForReport;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForReportPlain;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TestExt;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.ErroreBuilder;
import it.csi.gestionepazienti.gestionepazientiweb.dto.errore.WebMessages;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecorsoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TamponeMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TamponeMotivoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.test.generated.TestMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.util.Paginazione;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

public class TamponeApiServiceImpl extends AuditableApiServiceImpl implements TamponeApi {

	
	private static final String TAMPONE_MOTIVO_SINTOMO_COD = "sintomo";
	
	@Autowired
	TamponeMapper tamponeMapper;
	
	@Autowired
	TamponeMotivoMapper tamponeMotivoMapper;
	
	@Autowired
	DecorsoMapper decorsoMapper;
	
	@Autowired
	TestMapper testMapper;

	@Autowired
	SoggettoMapper soggettoMapper;

	@Autowired
	private MessageSource messageSource;

	
	
	@Override
	public Response getElencoTamponi(
			// Paginazione:
			String orderby, Long rowPerPage,Long pageNumber, String descendingStr,
			
			// Filtri: 
			String filter,
			String infoInsufficienti,
			String tamponeAutorizzato,
						
			SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			Paginazione paginazione = new Paginazione(orderby, rowPerPage, pageNumber, toBoolean(descendingStr));
			boolean infoInsuff = toBoolean(infoInsufficienti);
			
			UserLogged currentUser = getBeService().getCurrentUser(req);
			List<TamponeForReport> tamponeList = tamponeMapper.selectForReportByIdAsr(
					    //Filtri
						currentUser.getIdAsr(),
						toLike(filter),
						infoInsuff?"NI":toFlag(tamponeAutorizzato, "SI", "NO", null),
						
						//Paginazione
						paginazione.getOrderBy(),
						paginazione.orderAscDesc(),
						paginazione.getOffset(),
						paginazione.getLimit()
						
					);
			insertAudit("select", "tamponi", "getElencoTamponi("+currentUser.getIdAsr()+")", currentUser.getCfUtente(), null, req);
			
			
			Long rowsNumber;
			if(paginazione.isActive()) {
//				rowsNumber = tamponeMapper.selectForReportByIdAsrCount(
//					    //Filtri
//						currentUser.getIdAsr(),
//						toLike(filter),
//						infoInsuff?"NI":toFlag(tamponeAutorizzato, "SI", "NO", null)
//						);
				
				rowsNumber = 0L;
				if(tamponeList!=null && !tamponeList.isEmpty()) {
					rowsNumber = tamponeList.get(0).getTotalCount();
					if(rowsNumber==null) {
						rowsNumber = 0L;
					}
				}
			} else {
				rowsNumber = tamponeList==null?0L: tamponeList.size();
			}

			return Response.ok(tamponeList).header("Rows-Number", rowsNumber).build();


		} catch (Exception e) {
			log.error("Errore durante la conversione csv", e);
			return Response.serverError().entity(new Message("Errore durante la conversione csv")).build();
		}

	}
	

	@Override
	public Response getElencoTamponiCsv(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			UserLogged currentUser = getBeService().getCurrentUser(req);
			List<TamponeForReport> tamponeList = tamponeMapper.selectForReportByIdAsr(
					//Filtri
					currentUser.getIdAsr(), null, null,
					
					//Paginazione
					null, null, null, -1L
					
					);
			insertAudit("select", "tampone", "getElencoTamponiCsv("+currentUser.getIdAsr()+")", currentUser.getCfUtente(), null, req);

			List<TamponeForReportPlain> list = new ArrayList<TamponeForReportPlain>();
			if (tamponeList!=null)
			{
				for (TamponeForReport tampone : tamponeList) {
					TamponeForReportPlain sp = new TamponeForReportPlain(tampone);
					list.add(sp);
					
					
				}
			}
			
			String csv = convertObjectsToCsv(list);
			String fileName = "tamponi_asr.csv";
			return Response.ok(csv).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();


		} catch (Exception e) {
			log.error("Errore durante la conversione csv", e);
			return Response.serverError().entity(new Message("Errore durante la conversione csv")).build();
		}

	}

	@Override
	public Response insertTampone(TamponeForElenco tampone, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			/**

			 * Da usca l'idAsr e' null, quindi devo forzare il -1 altrimenti in query arriva null e il soggetto non 
			 * viene ritornato
			 */
			long idAsr = currentUser.getIdAsr() == null ? -1 : currentUser.getIdAsr();
			SoggettoForElenco soggetto = soggettoMapper.selectForElencoByIdSoggetto(tampone.getIdSoggetto(), idAsr);
			//SoggettoForElenco soggetto = soggettoMapper.selectForElencoByIdSoggetto(tampone.getIdSoggetto(), currentUser.getIdAsr());
			
			if (soggetto == null || soggetto.getIdAsr() ==null)
			{
//				Response resp = Response.status(Status.FORBIDDEN).build();
//				throw new WebApplicationException(resp);
				aggiornavisibilita(tampone.getIdSoggetto(),null, currentUser.getIdAsr());
			}
			if (tampone.getIdLaboratorio()==null 
					|| tampone.getContattoRichiedente()==null || tampone.getContattoRichiedente().isEmpty()
					|| tampone.getMedicoRichiedente()==null || tampone.getMedicoRichiedente().isEmpty())
			{
				StringBuilder message = new StringBuilder();
				if (tampone.getIdLaboratorio()==null) message.append("Laboratorio obbligatorio");
				if (tampone.getContattoRichiedente()==null || tampone.getContattoRichiedente().isEmpty()) message.append("Contatto richiedente obbligatorio");
				if (tampone.getMedicoRichiedente()==null || tampone.getMedicoRichiedente().isEmpty()) message.append("Medico richiedente obbligatorio");
				
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message(message.toString())).build();
				throw new WebApplicationException(resp);
			}
			
			tampone.setDataInserimentoRichiesta(new Date());
			
			//2020-09-08 se viene indicata la presenza di sintomi viene fatto un controllo
			//sui decorsi per cercare la presenza di sintomi
			if(tampone.getTamponeMotivoId() != null) {
				
				//decodifico il motivo tampone
				TamponeMotivo tm = tamponeMotivoMapper.selectByPrimaryKey(tampone.getTamponeMotivoId());
				if(tm.getTamponeMotivoCod().equalsIgnoreCase(TAMPONE_MOTIVO_SINTOMO_COD)) {
					
					boolean found = false;
					//scorro i decorsi
					List<DecorsoForElenco> decorsi = decorsoMapper.selectForElencoByIdSoggetto(tampone.getIdSoggetto());
					for(DecorsoForElenco dfe : decorsi) {
						if(dfe.getDataInizioSint() != null && dfe.getSintomi()!=null && dfe.getSintomi().equalsIgnoreCase("SI")) {
							found = true;
							break;
						}
					}
					
					//se non sono stati trovati sintomi corrispondenti sollevo un'eccezione
					if(!found) {
						Response resp = Response.status(Status.BAD_REQUEST).entity(new Message(
								"Sono stati indicati dei sintomi ma non sono stati trovati decorsi con dei sintomi associati"))
								.build();
						throw new WebApplicationException(resp);
					}
					
				}
			}
			
			
			//JIRA COV-200 autorizzato a SI in fase di inserimento
			tampone.setTamponeAutorizzato("SI");
			int ret = tamponeMapper.insert(tampone);
			
			insertAudit("insert", "tampone", "insert("+tampone.getIdTampone()+")", currentUser.getCfUtente(), tampone, req);

			return Response.ok(tampone).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Errore in chiamata",e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}
	
	@Override
	public Response updateTampone(TamponeForElenco tampone, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			SoggettoForElenco soggetto = soggettoMapper.selectForElencoByIdSoggetto(tampone.getIdSoggetto(), currentUser.getIdAsr());
			
			if (soggetto.getIdAsr() != currentUser.getIdAsr())
			{
				//Response resp = Response.status(Status.FORBIDDEN).build();
				//return resp;
				aggiornavisibilita(tampone.getIdSoggetto(),null, currentUser.getIdAsr());
			}
			
			// aggiorno solo il criterio epidemiologico e il tampone autorizzato
			Tampone tamponeOriginale = tamponeMapper.selectByPrimaryKey(tampone.getIdTampone());
			if(!"NI".equalsIgnoreCase(tamponeOriginale.getTamponeAutorizzato())){
				Response resp = Response.status(Status.FORBIDDEN).build();
				return resp;
			}
			if (tampone.getIdLaboratorio()==null 
					|| tampone.getContattoRichiedente()==null || tampone.getContattoRichiedente().isEmpty()
					|| tampone.getMedicoRichiedente()==null || tampone.getMedicoRichiedente().isEmpty())
			{
				StringBuilder message = new StringBuilder();
				if (tampone.getIdLaboratorio()==null) message.append("Laboratorio obbligatorio");
				if (tampone.getContattoRichiedente()==null || tampone.getContattoRichiedente().isEmpty()) message.append("Contatto richiedente obbligatorio");
				if (tampone.getMedicoRichiedente()==null || tampone.getMedicoRichiedente().isEmpty()) message.append("Medico richiedente obbligatorio");
				
				Response resp = Response.status(Status.BAD_REQUEST).entity(new Message(message.toString())).build();
				throw new WebApplicationException(resp);
			}

			
			if (tampone.getCriterioEpidemiologicoCovid19()!=null)
				tamponeOriginale.setCriterioEpidemiologicoCovid19(tampone.getCriterioEpidemiologicoCovid19().toUpperCase());
			else
				tamponeOriginale.setCriterioEpidemiologicoCovid19(null);
			tamponeOriginale.setTamponeAutorizzato("P");
			
			tamponeOriginale.setDataInserimentoRichiesta(new Date());
			tamponeOriginale.setDataUltimaModifica(new Date());
			tamponeOriginale.setContattoRichiedente(tampone.getContattoRichiedente());
			tamponeOriginale.setMedicoRichiedente(tampone.getMedicoRichiedente());
			tamponeOriginale.setIdLaboratorio(tampone.getIdLaboratorio());
			
			int ret = tamponeMapper.updateByPrimaryKey(tamponeOriginale);
			insertAudit("update", "tampone", "updateByPrimaryKey("+tampone.getIdTampone()+")", currentUser.getCfUtente(), tamponeOriginale, req);

			return Response.ok().build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Errore in chiamata",e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}
	
	@Override
	// TODO
	public Response risultatoTampone(TamponeForElenco tampone, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			SoggettoForElenco soggetto = soggettoMapper.selectForElencoByIdSoggetto(tampone.getIdSoggetto(), currentUser.getIdAsr());
			
			if (soggetto.getIdAsr() != currentUser.getIdAsr())
			{
				Response resp = Response.status(Status.FORBIDDEN).build();
				return resp;
			}
			
			int ret = tamponeMapper.updateByPrimaryKey(tampone);
			insertAudit("update", "tampone", "risultatoTampone("+tampone.getIdTampone()+")", currentUser.getCfUtente(), tampone, req);
			
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}


	@Override
	public Response deleteTampone(Long idTampone, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		
		
		Tampone tampone = tamponeMapper.selectByPrimaryKey(idTampone);
		if(tampone==null) {
			//return 404
			return ErroreBuilder.from(WebMessages.ENTITA_NON_TROVATA).title("Tampone non trovato.").dettaglio("E1", "Tampone non trovato con id "+idTampone).exception().getResponse();
		}
		
		if(tampone.getIdRisTamp()!=null) {
			//return non si puo' cancellare
			return ErroreBuilder.from(Status.BAD_REQUEST).title("Tampone non eliminabile in quanto già associato ad un risultato.").dettaglio("E1", "Tampone non eliminabile con id "+idTampone).exception().getResponse();
		}
		
		
		
		
		UserLogged currentUser = getBeService().getCurrentUser(req);
		
		Soggetto soggetto = soggettoMapper.selectByPrimaryKey(tampone.getIdSoggetto());
		Long idAsrSoggetto = soggetto.getIdAsr();
		
		if(currentUser.getIdAsr().compareTo(idAsrSoggetto)!=0) {
			List<SoggettoAsr> soggettiAsr = soggettoAsrMapper.selectByIdSoggetto(soggetto.getIdSoggetto());
			if(soggettiAsr==null || soggettiAsr.isEmpty()) {
				return ErroreBuilder.from(Status.BAD_REQUEST)
						.title("Tampone non eliminabile. Il Soggetto non appartiena alla tua ASR")
						.dettaglio("E1", "Decorso non eliminabile con id "+idTampone + " idAsr: "+currentUser.getIdAsr() + " idAsrSoggetto: "+idAsrSoggetto)
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
						.title("Tampone non eliminabile. Il Soggetto non appartiena alla tua ASR")
						.dettaglio("E1", "Decorso non eliminabile con id "+idTampone + " idAsr: "+currentUser.getIdAsr() + " idAsr lista")
						.exception().getResponse();
			}
		}
		
		
		
		
		
		
		int deleteCount;
		try {
			deleteCount = tamponeMapper.deleteByPrimaryKey(idTampone);
			log.info("deleted idTampone:"+ idTampone + " [deleteCount: "+deleteCount + "]");
		} catch (RuntimeException re) {
			log.error("Errore cancellazione tampone con id: "+idTampone, re);
			return ErroreBuilder.from(WebMessages.INTERNAL_SERVER_ERROR).throwable(re).exception().getResponse();
		}
		
		insertAudit("delete", "tampone", "delete("+idTampone+")", currentUser.getCfUtente(), null, req);
		
		
		return Response.ok(deleteCount).build();
	}


	@Override
	public Response getElencoTest(Long idSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			
			UserLogged currentUser = getBeService().getCurrentUser(req);
			List<TestExt> testList = testMapper.selectTestBySoggettoId(idSoggetto);
			insertAudit("select", "test", "getElencoTest("+idSoggetto+")", currentUser.getCfUtente(), null, req);

			return Response.ok(testList).build();


		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error("Errore in chiamata",e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}


	@Override
	public Response getElencoTestNonAbbinati(Long idAsr, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);
			List<TamponeForElencoNonAbbinati> tamponeList = tamponeMapper.selectForNonAbbinatiByIdAsr(""+idAsr);
//			insertAudit("select", "tampone", "getElencoTamponiCsv(" + currentUser.getIdAsr() + ")",
//					currentUser.getCfUtente(), null, req);


			return Response.ok(tamponeList).build();

		} catch (Exception e) {
			log.error("Errore durante la get Test non abbinati", e);
			return Response.serverError().entity(new Message("Errore durante la get Test non abbinati")).build();
		}
	}




	//
//	public Response getTableCSV(String table, String lang, SecurityContext securityContext, HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
//		try {
//			String csv;
//			ArrayList<AbstractDto> objs = null;
//			objs = (ArrayList<AbstractDto>) this.getTable(table, securityContext, httpHeaders, httpRequest).getEntity();
//			csv = convertObjectsToCsv(objs);
//			String fileName = table + ".csv";
//			return Response.ok(csv).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
//		} catch (Exception e) {
//			System.err.println(e);
//		}
//
//		return Response.ok().build();
//	}
//
//	public Response getTable(String table, SecurityContext securityContext, HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
//		User user = (User) httpRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
//		if (!user.getSuperadmin())
//			return Response.ok(new GenericError("The user isn't superAdmin")).status(401).build();
//		List<? extends AbstractDto> dbTable = null;
//
//		switch (table) {
//		case "projects":
//			dbTable = projectMapper.selectAllFullAdmin();
//			break;
//
//		case "collections":
//			dbTable = collectionMapper.selectAllFullAdmin();
//			break;
//
//		case "sources":
//			dbTable = sourceMapper.selectAllFullAdmin();
//			break;
//
//		case "datasets":
//			dbTable = datasetMapper.selectAllFullAdmin();
//			break;
//
//		case "collectionsRelations":
//			dbTable = collectionRelationMapper.selectAllFullAdmin();
//			break;
//
//		case "datasetsRelations":
//			dbTable = datasetRelationMapper.selectAllFullAdmin();
//			break;
//			
//		case "usages":
//			dbTable = usageMapper.selectAllFullAdmin();
//			break;
//
//		default:
//			return Response.ok(new GenericError("Table requested not found")).status(401).build();
//		}
//
//		return Response.ok(dbTable).build();
//	}
//
//	private String convertObjectsToCsv(ArrayList<AbstractDto> obj) {
//		try {
//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//			Locale locale = LocaleContextHolder.getLocale();
//			if(obj!=null && obj.size()>0) {
//				CsvMapper mapper = new CsvMapper();
//				mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
//				mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//				mapper.setDateFormat(df);
//				mapper.setPropertyNamingStrategy(new LocalizedPropertyNamingStrategy(messageSource, locale));
//				CsvSchema schema = mapper.schemaFor(obj.get(0).getClass());
//				SimpleModule module = new SimpleModule();
//				module.addSerializer(String.class, new CleanStringSerializer());
//				mapper.registerModule(module);				
//				schema = schema.withColumnSeparator(';');
//				schema = schema.withUseHeader(true);
//				schema = schema.withQuoteChar('"');
//				String csv = mapper.writer(schema).writeValueAsString(obj);
//				return csv;
//			}
//			return "";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "CSV conversion error";
//		}
//	}

}
