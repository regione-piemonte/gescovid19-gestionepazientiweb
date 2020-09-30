package it.csi.gestionepazienti.gestionepazientiweb.business.be.visurapazienti.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.impl.DecorsoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl.DisponibilitaApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.visurapazienti.VisuraSoggettoApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElencoPlain;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisuraWithFlags;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaArea;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReportTransposed;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.filter.gestionepazienti.IrideIdAdapterFilter;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecorsoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoAuraMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TamponeMapper;;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VisuraSoggettoApiServiceImpl extends AuditableApiServiceImpl implements VisuraSoggettoApi {

	@Autowired
	SoggettoMapper soggettoMapper;

	@Autowired
	TamponeMapper tamponeMapper;

	@Autowired
	DecorsoMapper decorsoMapper;

	@Autowired
	SoggettoAuraMapper soggettoAuraMapper;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Response getElencoSoggettiByDomicilioXlsx(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);
			String istatComune = null;
			
			// recupera istat dal sindaco 
			if (currentUser.getSindaco()!=null)
			{
				istatComune = currentUser.getSindaco().getComuneIstat();
			}

			if (istatComune==null) {
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza Sindaco associato")).build();
				return resp;
			}
			

			List<SoggettoForVisuraWithFlags> soggettoList = soggettoMapper
					.selectForElencoVisuraByIdTipoEventoByDomicilioByTampone(istatComune,
							new Integer[] { 
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_QUARANTENA_DOMIC,
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_QUARANTENA_EXTRA_DOMIC,
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_DOMIC,
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_EXTRA_DOMIC
									});

			insertAudit("select", "soggetto", "getElencoSoggettiByDomicilio(" + istatComune + ")", currentUser.getCfUtente(),
					null, req);


			StreamingOutput os = convertObjectsToXlsx(soggettoList);
			String fileName = "soggetti.xlsx";
			return Response.ok(os).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();

		} catch (Exception e) {
			log.error("Errore durante la conversione csv", e);
			return Response.serverError().entity(new Message("Errore durante la conversione csv")).build();
		}

	}
	// filtro trastfeimnto sempre

	@Override
	public Response getElencoSoggettiByDomicilio(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] Inizio esecuzione ");

			UserLogged currentUser = (UserLogged) req.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			
			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] current user ok!");
			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] cf: " + currentUser.getCfUtente());
			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] sindaco: " + currentUser.getSindaco().getCfUtente());

			String istatComune = null;
			
			// recupera istat dal sindaco 
			if (currentUser.getSindaco()!=null)
			{
				log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] sindaco is not null, recupero l'istat");
				istatComune = currentUser.getSindaco().getComuneIstat();
				log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] istat: " + istatComune);
			}
			

			if (istatComune==null) {
				log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] FORBIDDEN --> Utente senza Sindaco associato ");
				Response resp = Response.status(Status.FORBIDDEN).entity(new Message("Utente senza Sindaco associato")).build();
				return resp;
			}
			

			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] start esecuzione query");
			List<SoggettoForVisuraWithFlags> soggettoList = soggettoMapper
					.selectForElencoVisuraByIdTipoEventoByDomicilioByTampone(istatComune,
							new Integer[] { 
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_QUARANTENA_DOMIC,
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_QUARANTENA_EXTRA_DOMIC, 
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_ISOLAMENTO_FIDUCIARIO,
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_DOMIC,
									DecorsoApiServiceImpl.ID_TIPO_EVENTO_DISPOSTA_QUARANTENA_EXTRA_DOMIC, 
									
					});
			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] end esecuzione query");

			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] start insert audit");
			insertAudit("select", "soggetto", "getElencoSoggettiByDomicilio(" + istatComune + ")", currentUser.getCfUtente(),
					null, req);
			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] end insert audit");

			log.info("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] End servizio");
			return Response.ok(soggettoList).build();
		} catch (Exception e) {
			log.error("[VisuraSoggettoApiServiceImpl::getElencoSoggettiByDomicilio] Si e' verificato un errore! Messaggio: " + e.getMessage());
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
			//System.err.println(e);
			//return Response.serverError().build();
		}

	}

//	@Override
//	public Response getSoggettoByIdSoggetto(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,
//			HttpServletRequest req) {
//		try {
//			UserLogged currentUser = getBeService().getCurrentUser(req);
//
//			Long idAsr = currentUser.getIdAsr();
//			
//			if (GenericUtils.currentUserHasProfilo(currentUser, 6))
//				idAsr=-1L;
//
//			
//			SoggettoForElenco soggettoForElenco = soggettoMapper.selectForElencoByIdSoggetto(id, idAsr);
//			if (soggettoForElenco==null)
//				return Response.ok(null).build();
//			List<TamponeForElenco> elencoTampone = tamponeMapper.selectForElencoByIdSoggetto(id);
//			List<DecorsoForElenco> elencoDecorso = decorsoMapper.selectForElencoByIdSoggetto(id);
//			List<SoggettoAura> elencoSoggettoAura = soggettoAuraMapper.selectByIdSoggetto(id);
//			SoggettoForDettaglio dettaglio = new SoggettoForDettaglio(soggettoForElenco, elencoTampone, elencoDecorso);
//			//Dovrebbe esserci al piu un solo soggettoAura ma il db ne permette piu di uno. scelgo il primo e loggo
//			if (elencoSoggettoAura!=null && !elencoSoggettoAura.isEmpty())
//			{
//				if (elencoSoggettoAura.size()>1)
//					log.warn("Trovato soggetto con "+elencoSoggettoAura.size()+"  id_aura associati!! "+id);
//				dettaglio.setIdAura(elencoSoggettoAura.get(0).getIdAura());
//			}
//			insertAudit("select", "soggetto tampone decorso", "selectForElencoByIdSoggetto("+id+")", currentUser.getCfUtente(), null, req);
//
//			
//			return Response.ok(dettaglio).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.serverError().build();
//		}
//	}
//

	public StreamingOutput convertObjectsToXlsx(List<SoggettoForVisuraWithFlags> list) {
		final XSSFWorkbook workbook = createWorkbook();

		if (list != null) {
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

			int indexRow = 1;
			for (SoggettoForVisuraWithFlags disp : list) {
				int indexColumn = 0;
			
//				"Codice Fiscale", "Cognome", "Nome", "Data di nascita", "Comune di residenza",
				
				// prime colonne con struttura
				Row row = sheet.createRow(indexRow);
				Cell cell = row.createCell(indexColumn);
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
				cell.setCellValue(disp.getTelefonoRecapito());
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getTipoEvento()!=null?
						disp.getTipoEvento().getDescTipoEvento():"");
				indexColumn++;

				
				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateStyle);
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getDataDimissioni():null);
				indexColumn++;

				cell = row.createCell(indexColumn);
				cell.setCellStyle(cellDateStyle);
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getDataPrevFineEvento():null);
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


				cell = row.createCell(indexColumn);
				cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
				cell.setCellValue(disp.getDecorso()!=null?
						disp.getDecorso().getNote():"");
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
				cell.setCellStyle(cellDateStyle);
				cell.setCellValue(disp.getAttualmentePositivo());
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

	private static String[] headers = { "Codice Fiscale", "Cognome", "Nome", "Data di nascita", "Comune di residenza",
			"Comune di domicilio", "Telefono mobile", "Dimissioni","Data inizio dimissioni", "Data fine quarantena",
			"Isolamento presso", "Struttura", "Area",  "Note", "Esito ultimo tampone", "Attualmente positivo" };

	private static IndexedColors[] headersColors = { IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE,
			IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE,
			IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE, IndexedColors.WHITE,
			IndexedColors.WHITE, IndexedColors.WHITE , IndexedColors.WHITE };
	private static Integer[] headersWidth = { 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000,
			6000, 6000, 6000 , 6000};

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
