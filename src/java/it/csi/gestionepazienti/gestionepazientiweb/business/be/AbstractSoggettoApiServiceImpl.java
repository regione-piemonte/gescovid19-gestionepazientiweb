package it.csi.gestionepazienti.gestionepazientiweb.business.be;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import it.csi.gestionepazienti.gestionepazientiweb.business.aura.find.DatiAnagrafici;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.find.FindProfiliAnagraficiResult;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.GetProfiloSanitarioResponse;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.SoggettoAuraBody;
import it.csi.gestionepazienti.gestionepazientiweb.business.delegate.AuraWsDelegate;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.MedicoSoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TestCovid;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoFromElencoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecorsoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.MedicoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.MedicoSoggettoAuraMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoAuraMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoContattoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoContattoTipoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TamponeMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TestCovidMapper;

public abstract class AbstractSoggettoApiServiceImpl extends AuditableApiServiceImpl {

	@Value("${auraprefix.url}")
	private String auraprefixUrl;
	@Value("${aura.user}")
	private String auraUser;
	@Value("${aura.pwd}")
	private String auraPwd;
	@Value("${aura.timeoutmills}")
	private int auraTimeout;
	@Autowired
	protected SoggettoMapper soggettoMapper;
	@Autowired
	protected SoggettoContattoMapper soggettoContattoMapper;
	@Autowired
	protected SoggettoContattoTipoMapper soggettoContattoTipoMapper;
	
	@Autowired
	protected TamponeMapper tamponeMapper;
	@Autowired
	protected DecorsoMapper decorsoMapper;
	@Autowired
	protected SoggettoAuraMapper soggettoAuraMapper;
	@Autowired
	MedicoSoggettoAuraMapper medicoSoggettoAuraMapper;
	@Autowired
	protected MedicoMapper medicoMapper;
	
	@Autowired
	protected TestCovidMapper testCovidMapper;

	
	public AbstractSoggettoApiServiceImpl() {
		super();
	}

	public Medico aggiornamentoMediciPerSoggetto(Soggetto soggetto,Long idAura, HttpServletRequest req, UserLogged currentUser) throws Exception {
		
		
		// Lettuta Medico da AURA
		Medico medicoFromAura = extractMedicoFromAura(""+idAura);
		
		if (medicoFromAura!=null && medicoFromAura.getIdMedico()!=null && medicoFromAura.getCfMedico()!=null)
		{
			log.info("Richiesta inserimento medico "+medicoFromAura.getIdMedico()+"per soggetto "+soggetto.getIdSoggetto() +" idAura:"+idAura);
	
			// controllo se il legame esiste
			MedicoSoggettoAura msa= medicoSoggettoAuraMapper.selectByPrimaryKey(medicoFromAura.getIdMedico(), idAura);
			if (msa != null)
			{
				log.info("Legame Medico  soggetto "+soggetto.getIdSoggetto()+" / "+medicoFromAura.getIdMedico() +" presente.");							
			}
			else {
				//cerco se medico esiste
				if (medicoMapper.selectByPrimaryKey(medicoFromAura.getIdMedico())!=null)
				{
					log.info("Medico richiesto per soggetto "+soggetto.getIdSoggetto()+" trovato "+medicoFromAura.getIdMedico());							
				}
				else {
					log.info("Medico richiesto per soggetto "+soggetto.getIdSoggetto()+"non trovato : inserimento in corso");							
					medicoMapper.insert(medicoFromAura);
					insertAudit("insert", "medico", "insert("+medicoFromAura.getIdMedico()+")", currentUser.getCfUtente(), (Soggetto)soggetto, req);
	
				}
				log.info("Legame Medico  soggetto "+soggetto.getIdSoggetto()+" / "+medicoFromAura.getIdMedico() +" NON presente.");							
				medicoSoggettoAuraMapper.deleteByIdAuraSogg(idAura);
				log.info("Eliminati legami precedenti "+soggetto.getIdSoggetto());	
				msa = new MedicoSoggettoAura();
				msa.setIdAuraSogg(idAura);
				msa.setIdMedico(medicoFromAura.getIdMedico());
				medicoSoggettoAuraMapper.insert(msa);
				log.info("Legame Medico  soggetto "+soggetto.getIdSoggetto()+" / "+medicoFromAura.getIdMedico() +" inserito.");	
			}
		}
		else {
			log.info("Medico non presente per soggetto "+soggetto.getIdSoggetto());
			
		}
		return medicoFromAura;
	}
	
	public  List<SoggettoFromElencoAura> findProfiliAnagrafici(String codiceFiscale) throws Exception{

//		log.info("| chiamo la findProfiliAnagrafici cf : "+codiceFiscale);
	    FindProfiliAnagraficiResult fp=	AuraWsDelegate.findProfiliAnagrafici(codiceFiscale, null, null, null, auraUser, auraPwd, auraprefixUrl, auraTimeout);
//	    log.info("| risultato findProfiliAnagrafici: " + fp );
	    return removedCFDuplicatedInactive(extractFromFindProfili(fp));
	}
	
	public  List<SoggettoFromElencoAura> findProfiliAnagraficiByNomeECognome(String nome, String cognome, String dataNascita) throws Exception{
		
//		log.info("| chiamo la findProfiliAnagraficiByNomeECognome ");
		FindProfiliAnagraficiResult fp=	AuraWsDelegate.findProfiliAnagrafici(null, nome, cognome, dataNascita, auraUser, auraPwd, auraprefixUrl, auraTimeout);
//		log.info("| risultato findProfiliAnagraficiByNomeECognome: " + fp );
		return removedCFDuplicatedInactive(extractFromFindProfili(fp));
	}

	
	private List<SoggettoFromElencoAura> removedCFDuplicatedInactive(List<SoggettoFromElencoAura> list) throws Exception {
		
		log.info("| Inizio removedCFDuplicatedInactive "  );
		
		if (list==null)
			return list;
		log.info("trovati num soggetti"+list.size());
		if (list.size()<2)
			return list;
		
		Map<String, List<SoggettoFromElencoAura>> codiciFiscaliConteggioMap = new HashMap<String, List<SoggettoFromElencoAura>>();
		
		for (SoggettoFromElencoAura soggettoFromElencoAura : list) {
			List<SoggettoFromElencoAura> lista =  codiciFiscaliConteggioMap.get(soggettoFromElencoAura.getCodiceFiscale());
			if (lista==null)
			{
				lista = new ArrayList<SoggettoFromElencoAura>();
				lista.add(soggettoFromElencoAura);
			}
			else {
				log.info("Elemento duplicato");
				lista.add(soggettoFromElencoAura);
			}
			codiciFiscaliConteggioMap.put(soggettoFromElencoAura.getCodiceFiscale(), lista);
		}
		
		 Iterator<Map.Entry<String, List<SoggettoFromElencoAura>>> iterator = codiciFiscaliConteggioMap.entrySet().iterator();

		 while (iterator.hasNext()) {
		     Map.Entry<String, List<SoggettoFromElencoAura>> entry = iterator.next();
		     if (entry.getValue().size()<2) {
		         iterator.remove();
		     }
		 }
		
		if (codiciFiscaliConteggioMap.isEmpty())
			return list;
		
		log.info("Identificati codici fiscali duplicati!");
		List<SoggettoFromElencoAura> newList = new ArrayList<SoggettoFromElencoAura>();
		
		for (SoggettoFromElencoAura soggettoFromElencoAura : list) {
			
			if (codiciFiscaliConteggioMap.containsKey(soggettoFromElencoAura.getCodiceFiscale()))
			{
				SoggettoForElenco sog = extractProfiloSanitario(soggettoFromElencoAura.getProfiloAnagrafico().toString(), false);
				if (sog!=null)
					newList.add(soggettoFromElencoAura);
			}
			else {
				newList.add(soggettoFromElencoAura);
			}
			
		}
		
//		log.info("| Fine removedCFDuplicatedInactive "  );
		return newList;
		
	}
	
	public SoggettoForElenco extractProfiloSanitario(String id, boolean aslDecodifica) throws Exception {
		GetProfiloSanitarioResponse gr = AuraWsDelegate.getProfiloSanitario(id, auraUser, auraPwd, auraprefixUrl, auraTimeout );
		if (gr==null || gr.getGetProfiloSanitarioResult()==null 
				|| gr.getGetProfiloSanitarioResult().getHeader()==null
				|| gr.getGetProfiloSanitarioResult().getHeader().getCodiceRitorno()==null
				|| !gr.getGetProfiloSanitarioResult().getHeader().getCodiceRitorno().equals("1")
				|| gr.getGetProfiloSanitarioResult().getBody()==null
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoAnag()==null
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoAnag().getDatiPrimari()==null
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoAnag().getDatiPrimari().getStatoProfiloAnagrafico()==null
				|| !gr.getGetProfiloSanitarioResult().getBody().getInfoAnag().getDatiPrimari().getStatoProfiloAnagrafico().equals("1")
				|| (gr.getGetProfiloSanitarioResult().getFooter()!=null 
					&& gr.getGetProfiloSanitarioResult().getFooter().getMessages()!=null 
					&& gr.getGetProfiloSanitarioResult().getFooter().getMessages().getMessage()!=null
					&& !gr.getGetProfiloSanitarioResult().getFooter().getMessages().getMessage().isEmpty())
				)
		{
			return null;
		}
		SoggettoForElenco soggetto = new SoggettoForElenco();
		SoggettoAuraBody body =  gr.getGetProfiloSanitarioResult().getBody();
		soggetto.setCodiceFiscale(body.getInfoAnag().getDatiPrimari().getCodiceFiscale());
		soggetto.setCognome(body.getInfoAnag().getDatiPrimari().getCognome());
		soggetto.setNome(body.getInfoAnag().getDatiPrimari().getNome());
		
		if (body.getInfoAnag().getDatiPrimari().getDataNascita()!=null &&
				body.getInfoAnag().getDatiPrimari().getDataNascita().toGregorianCalendar()!=null)
			soggetto.setDataNascita(body.getInfoAnag().getDatiPrimari().getDataNascita().toGregorianCalendar().getTime());
		String telefono = null;
		if (body.getInfoAnag().getDomicilio()!=null)
		{
			Comuni comuneDom = new Comuni();
			comuneDom.setIstatComune(body.getInfoAnag().getDomicilio().getCodComune());
			comuneDom.setNomeComune(body.getInfoAnag().getDomicilio().getDescComune());
			soggetto.setComuneDomicilio(comuneDom);
			soggetto.setComuneDomicilioIstat(comuneDom.getIstatComune());
			soggetto.setIndirizzoDomicilio(body.getInfoAnag().getDomicilio().getIndirizzo() + " "+body.getInfoAnag().getDomicilio().getNumCivico());
			if (body.getInfoAnag().getDomicilio().getTelefono()!=null 
					&& !body.getInfoAnag().getDomicilio().getTelefono().isEmpty())
			{
				telefono = body.getInfoAnag().getDomicilio().getTelefono();
			}
		}
		if (body.getInfoAnag().getResidenza()!=null)
		{
			Comuni comuneRes = new Comuni();
			comuneRes.setIstatComune(body.getInfoAnag().getResidenza().getCodComune());
			comuneRes.setNomeComune(body.getInfoAnag().getResidenza().getDescComune());
			soggetto.setComuneResidenza(comuneRes);
			soggetto.setComuneResidenzaIstat(comuneRes.getIstatComune());
			if (telefono==null && body.getInfoAnag().getResidenza().getTelefono()!=null
					&& !body.getInfoAnag().getResidenza().getTelefono().isEmpty())
			{
				telefono = body.getInfoAnag().getResidenza().getTelefono();
			}
		}
		soggetto.setTelefonoRecapito(telefono);
//		
//		if (aslDecodifica && body.getInfoSan()!=null){
//			String aslDomicilioStr = findAslDescrizioneByCodice(body.getInfoSan().getAslDomicilio());
//			if (aslDomicilioStr!=null && !aslDomicilioStr.isEmpty())
//				soggetto.setAslDomicilio(aslDomicilioStr);
//			String aslResidenzaStr = findAslDescrizioneByCodice(body.getInfoSan().getAslResidenza());
//			if (aslResidenzaStr!=null && !aslResidenzaStr.isEmpty())
//				soggetto.setAslResidenza(aslResidenzaStr);
//		}
//		else {
//			log.info("Soggetto con dati sanitari nulli");
//		}
		
		
		
		return soggetto;
	}
	
	private List<SoggettoFromElencoAura> extractFromFindProfili(FindProfiliAnagraficiResult fp) {
//		log.info("| Inizio exstractFromFindProfili "  );
		if (fp==null 
				|| fp.getHeader()==null
				|| fp.getHeader().getCodiceRitorno()==null
				|| fp.getBody()==null
				|| fp.getBody().getElencoProfili()==null
				|| !fp.getHeader().getCodiceRitorno().equals("1")
				|| (fp.getFooter()!=null 
					&& fp.getFooter().getMessages()!=null 
					&& fp.getFooter().getMessages().getMessage()!=null
					&& !fp.getFooter().getMessages().getMessage().isEmpty())
			)
		{
			return new ArrayList<SoggettoFromElencoAura>();
		}
		
		List<DatiAnagrafici> datis =  fp.getBody().getElencoProfili().getDatianagrafici() ;
		if (datis==null || datis.isEmpty())
		{
			return new ArrayList<SoggettoFromElencoAura>();
		}
		List<SoggettoFromElencoAura> soggetti = new ArrayList<SoggettoFromElencoAura>();
		for (DatiAnagrafici dati : datis) {
			SoggettoFromElencoAura sog = new SoggettoFromElencoAura();
			sog.setCodiceFiscale(dati.getCodiceFiscale());
			sog.setNome(dati.getNome());
			sog.setCognome(dati.getCognome());
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
				sog.setDataNascitaStr(sdf2.format(sdf.parse(dati.getDataNascita())));
			} catch (Exception e) {
			}
			sog.setComuneNascita(dati.getComuneNascita());
			sog.setProvinciaNascita(dati.getProvinciaNascita());
			sog.setStatoNascita(dati.getStatoNascita());
			sog.setSesso(dati.getSesso());
			if (dati.getDataDecesso()!=null && !dati.getDataDecesso().isEmpty())
			{
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
					sog.setDataDecessoStr(sdf2.format(sdf.parse(dati.getDataDecesso())));
				} catch (Exception e) {
				}
			}
			sog.setProfiloAnagrafico(dati.getIdProfiloAnagrafico());
			soggetti.add(sog);
		}

//		log.info("| Fine exstractFromFindProfili "  );
		return soggetti;
	}
	
	public Medico extractMedicoFromAura(String idAura) throws Exception {
		GetProfiloSanitarioResponse gr = AuraWsDelegate.getProfiloSanitario(idAura, auraUser, auraPwd, auraprefixUrl, auraTimeout );
		if (gr==null || gr.getGetProfiloSanitarioResult()==null 
				|| gr.getGetProfiloSanitarioResult().getHeader()==null
				|| gr.getGetProfiloSanitarioResult().getHeader().getCodiceRitorno()==null
				|| !gr.getGetProfiloSanitarioResult().getHeader().getCodiceRitorno().equals("1")
				|| gr.getGetProfiloSanitarioResult().getBody()==null
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoAnag()==null
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoAnag().getDatiPrimari()==null
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoAnag().getDatiPrimari().getStatoProfiloAnagrafico()==null
				|| !gr.getGetProfiloSanitarioResult().getBody().getInfoAnag().getDatiPrimari().getStatoProfiloAnagrafico().equals("1")
				|| gr.getGetProfiloSanitarioResult().getBody().getInfoSan()==null
				|| (gr.getGetProfiloSanitarioResult().getFooter()!=null 
					&& gr.getGetProfiloSanitarioResult().getFooter().getMessages()!=null 
					&& gr.getGetProfiloSanitarioResult().getFooter().getMessages().getMessage()!=null
					&& !gr.getGetProfiloSanitarioResult().getFooter().getMessages().getMessage().isEmpty())
				)
		{
			return null;
		}
	
		Medico medico = null;
		SoggettoAuraBody body =  gr.getGetProfiloSanitarioResult().getBody();
		
		if (body.getInfoSan().getIdMedico()!=null){
			medico = new Medico();
			medico.setCfMedico(body.getInfoSan().getCodiceFiscaleMedico());
			medico.setCodiceReg(body.getInfoSan().getCodRegionaleMedico());
			medico.setCognome(body.getInfoSan().getCognomeMedico());
			medico.setNome(body.getInfoSan().getNomeMedico());
			medico.setIdMedico(body.getInfoSan().getIdMedico().longValue());

		}
		else {
			log.info("Soggetto con dati sanitari nulli");
		}
		
		
		
		return medico;
	}

	
	public StreamingOutput convertObjectsToXlsx(List<SoggettoForVisura> list) {
		final XSSFWorkbook workbook = createWorkbook();

		if (list != null) {
			List<TestCovid> elencoTestCovid = testCovidMapper.selectAll();
			
			Sheet sheet = workbook.getSheet("Soggetti");

			XSSFFont headerFont = workbook.createFont();
			headerFont.setFontName("Arial");
			headerFont.setFontHeightInPoints((short) 8);
			headerFont.setBold(true);

			CellStyle cellDateStyle = workbook.createCellStyle();
			cellDateStyle.setBorderBottom(BorderStyle.THIN);
			cellDateStyle.setBorderTop(BorderStyle.THIN);
			cellDateStyle.setBorderRight(BorderStyle.THIN);
			cellDateStyle.setBorderLeft(BorderStyle.THIN);
			cellDateStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellDateStyle.setFillForegroundColor(IndexedColors.WHITE.index);
			cellDateStyle.setFont(headerFont);
			cellDateStyle.setWrapText(true);
			cellDateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));

			CellStyle cellDateTorqStyle = workbook.createCellStyle();
			cellDateTorqStyle.setBorderBottom(BorderStyle.THIN);
			cellDateTorqStyle.setBorderTop(BorderStyle.THIN);
			cellDateTorqStyle.setBorderRight(BorderStyle.THIN);
			cellDateTorqStyle.setBorderLeft(BorderStyle.THIN);
			cellDateTorqStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellDateTorqStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
			cellDateTorqStyle.setFont(headerFont);
			cellDateTorqStyle.setWrapText(true);
			cellDateTorqStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));


			int indexRow = 1;
			for (SoggettoForVisura disp : list) {
				int indexColumn = 0;
			
//				"Codice Fiscale", "Cognome", "Nome", "Data di nascita", "Comune di residenza",
				
				// prime colonne con struttura
				Row row = sheet.createRow(indexRow);
				Cell cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				if (disp.getAsr()!=null && disp.getAsr().getDescrizione()!=null)
					cell.setCellValue(disp.getAsr().getDescrizione());
				else
					cell.setCellValue("");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getCodiceFiscale());
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getCognome());
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getNome());
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateStyle);
				cell.setCellValue(disp.getDataNascita());
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getComuneResidenza()!=null?
						disp.getComuneResidenza().getNomeComune():"");
				indexColumn++;

//				"Comune di domicilio", "Telefono mobile", "Data inizio quarantena", "Data fine quarantena",

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getComuneDomicilio()!=null?
						disp.getComuneDomicilio().getNomeComune():"");
				indexColumn++;
				
				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getIndirizzoDomicilio()!=null?
						disp.getIndirizzoDomicilio():"");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getTelefonoRecapito());
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getTipoSoggetto()!=null?disp.getTipoSoggetto().getDescTipoSoggetto():"");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getTipoEvento()!=null?
						disp.getTipoEvento().getDescTipoEvento():"");
				indexColumn++;

				
				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateTorqStyle);
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getDataDimissioni():null);
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateTorqStyle);
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getDataPrevFineEvento():null);
				indexColumn++;

			//	decorsi.condizioni cliniche, decorsi.sintomi, 
				// Data esordio sintomi //note
				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getCondizioniCliniche():"");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getSintomi():"");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateTorqStyle);
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getDataInizioSint():null);
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getNote():"");
				indexColumn++;
			
				
				
				//				"Isolamento presso", "Struttura", "Area", "Dimissioni", "Note", "Esito ultimo tampone"

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				
				String comuneRic = disp.getComuneRicovero()!=null?disp.getComuneRicovero().getNomeComune():"";
				String indRic = "";
				String pressoRic = "";
				if (disp.getDecorso()!=null)
				{
					if (disp.getDecorso().getIndirizzoDecorso()!=null)
						indRic = " "+disp.getDecorso().getIndirizzoDecorso();
					if (disp.getDecorso().getDecorsoPresso()!=null)
						pressoRic = " "+disp.getDecorso().getDecorsoPresso();
				}
				cell.setCellValue(comuneRic+indRic+pressoRic);
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getStruttura()!=null?
						disp.getStruttura().getNome():"");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getArea()!=null?disp.getArea().getNome():"");
				indexColumn++;

				// data richiesta//  tampone.criterio, // tipologia test 
				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateTorqStyle);
				cell.setCellValue(disp.getTampone()!=null?
						disp.getTampone().getDataInserimentoRichiesta():null);
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getTampone()!=null?disp.getTampone().getCriterioEpidemiologicoCovid19():"");
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				if (disp.getTampone()!=null 
						&& disp.getTampone().getIdTestCovid()!=null
						&& elencoTestCovid!=null && !elencoTestCovid.isEmpty())
				{
					String testDecode = "";
					for (TestCovid testCovid : elencoTestCovid) {
						if (disp.getTampone().getIdTestCovid().equals(testCovid.getIdTestCovid()))
						{
							testDecode = testCovid.getTestCovid();
							break;
						}
					}
					cell.setCellValue(testDecode);
				}
				else
					cell.setCellValue("");					
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				if (disp.getTampone()!=null 
						&& disp.getTampone().getIdRisTamp()!=null)
				{
					String esitoDecode = esitiTampone().get(disp.getTampone().getIdRisTamp());
					cell.setCellValue(esitoDecode!=null?esitoDecode:"");
				}
				else
					cell.setCellValue("");					
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateTorqStyle);
				cell.setCellValue(disp.getTampone()!=null?
						disp.getTampone().getDataTest():null);
				indexColumn++;


				indexRow++;
			}
		}

		StreamingOutput streamOutput = new StreamingOutput() {

			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException {
				workbook.write(out);
			}

		};

		return streamOutput;
	}

	private static Map<Long, String> esitiTampone(){
		Map<Long, String> esiti = new HashMap<Long, String>();
		esiti.put(new Long(1), "In corso");
		esiti.put(new Long(2), "Positivo");
		esiti.put(new Long(4), "Negativo");
		esiti.put(new Long(6), "Sospeso");
		esiti.put(new Long(7), "Non idoneo");
		esiti.put(new Long(8), "Non pervenuto");
		return esiti;
	}
	

	private static XSSFWorkbook createWorkbook() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Soggetti");
		Row header = sheet.createRow(0);

		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 8);
		headerFont.setBold(true);

		XSSFFont rowFont = workbook.createFont();
		rowFont.setFontName("Arial");
		rowFont.setFontHeightInPoints((short) 8);
		rowFont.setBold(false);
		int i = 0;
		for (String hName : headers) {
			CellStyle headerStyle = workbook.createCellStyle();
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(hName);
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderTop(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setFillForegroundColor(headersColors[i].index);
			headerStyle.setFont(headerFont);
			headerStyle.setWrapText(true);
			headerCell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, headersWidth[i]);
			i++;
		}

		return workbook;
	}

	private static String[] headers = { "ASR in carico", "Codice Fiscale", "Cognome", "Nome", "Data di nascita", "Comune di residenza",
			"Comune di domicilio","Indirizzo di domicilio", "Telefono mobile", "Tipo paziente", "Evento","Data evento", "Data fine quarantena",
			"Condizioni cliniche","Sintomi","Data esordio sintomi malattia","Note",
			"Isolamento presso", "Struttura", "Area",   
			"Data richiesta tampone","Criterio epid.","Tipologia test covid", 
			"Esito ultimo tampone","Data test" };

	
	private static IndexedColors[] headersColors = { IndexedColors.WHITE,IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE,
			IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, 
			IndexedColors.LIGHT_TURQUOISE,
			IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE, 
			IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE, 
			IndexedColors.WHITE,IndexedColors.WHITE, IndexedColors.WHITE,IndexedColors.LIGHT_TURQUOISE, 
			IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE,IndexedColors.LIGHT_TURQUOISE, IndexedColors.LIGHT_TURQUOISE, };
	private static Integer[] headersWidth = { 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000,
			6000, 6000, 6000, 6000,6000, 6000, 6000,6000, 6000, 6000,6000  };

}