package it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.ArrayUtils;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.postiletto.DisponibilitaApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaArea;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReport;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReportTransposed;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReportTransposedPlain;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend.DisponibilitaMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DisponibilitaApiServiceImpl extends AuditableApiServiceImpl implements DisponibilitaApi {


	@Autowired
	DisponibilitaMapper disponibilitaMapper;

	@Override
	public Response getElencoDisponibilitaReport(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);

			// TODO estrarre l'idEnte o gli idEnte o il filtro che server a cui e abilitato
			// un utente e passarlo sotto.
			// Ad ora se null viene restituito tutto
			List<DisponibilitaForReport> retList = disponibilitaMapper.selectForReportByIdEnte(null, true);

			// TODO verificare se serve audit in lettura di questi dati

			return Response.ok(retList).build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}

	@Override
	public Response getElencoDisponibilitaReportTransposed(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);

			// TODO estrarre l'idEnte o gli idEnte o il filtro che server a cui e abilitato
			// un utente e passarlo sotto.
			// Ad ora se null viene restituito tutto
			List<DisponibilitaForReportTransposed> retList = disponibilitaMapper.selectForReportTransposedByIdEnte(null,
					true);

			return Response.ok(retList).build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}

	@Override
	public Response getElencoDisponibilitaReportTransposedCSV(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);

			// TODO estrarre l'idEnte o gli idEnte o il filtro che server a cui e abilitato
			// un utente e passarlo sotto.
			// Ad ora se null viene restituito tutto
			List<DisponibilitaForReportTransposed> retList = disponibilitaMapper.selectForReportTransposedByIdEnte(null,
					true);

			List<DisponibilitaForReportTransposedPlain> list = convertFromJsonToPlain(retList);

			String csv = convertObjectsToCsv(list);
			String fileName = "disponibilita.csv";
			return Response.ok(csv).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}

	private List<DisponibilitaForReportTransposedPlain> convertFromJsonToPlain(
			List<DisponibilitaForReportTransposed> retList) {
		List<DisponibilitaForReportTransposedPlain> list = new ArrayList<DisponibilitaForReportTransposedPlain>();
		if (retList != null) {
			for (DisponibilitaForReportTransposed dispo : retList) {
				DisponibilitaForReportTransposedPlain dp = new DisponibilitaForReportTransposedPlain();

				dp.setNaturaStruttura(dispo.getStruttura().getNatura());
				dp.setNomeEnte(dispo.getEnte().getNome());
				dp.setNomeStruttura(dispo.getStruttura().getNome());

				if (dispo.getDisponibilitaArea() != null) {
					Integer numeroTotaleOccupati = null;
					Integer numeroTotaleDisponibili = null;
					for (DisponibilitaArea singleDispoArea : dispo.getDisponibilitaArea()) {
						Integer numAreaDispOccupati = null;
						Integer numAreaDispDisponibili = null;
						if (singleDispoArea.getDisponibilita() != null
								&& singleDispoArea.getDisponibilita().getPostiOccupati() != null)
							numAreaDispOccupati = singleDispoArea.getDisponibilita().getPostiOccupati();
						if (singleDispoArea.getDisponibilita() != null
								&& singleDispoArea.getDisponibilita().getPostiDisponibili() != null)
							numAreaDispDisponibili = singleDispoArea.getDisponibilita().getPostiDisponibili();

						if (numAreaDispDisponibili != null) {
							if (numeroTotaleDisponibili == null)
								numeroTotaleDisponibili = new Integer(0);
							numeroTotaleDisponibili += numAreaDispDisponibili;
							switch (singleDispoArea.getArea().getIdDArea()) {
							case "TER_INT":
								dp.setNumPLAttivatiTerapiaIntensiva(String.valueOf(numAreaDispDisponibili));
								break;
							case "TER_SEMI_INT":
								dp.setNumPLAttivatiTerapiaSemiIntensiva(String.valueOf(numAreaDispDisponibili));
								break;
							case "INFETT":
								dp.setNumPLAttivatiMalInfettive(String.valueOf(numAreaDispDisponibili));
								break;
							case "PNEUMO":
								dp.setNumPLAttivatiPneumologia(String.valueOf(numAreaDispDisponibili));
								break;
							case "MED_INT":
								dp.setNumPLAttivatiMediaIntensita(String.valueOf(numAreaDispDisponibili));
								break;
							default:
								dp.setNumPLAttivatiAltro(String.valueOf(numAreaDispDisponibili));
								break;
							}
						}
						if (numAreaDispOccupati != null) {
							if (numeroTotaleOccupati == null)
								numeroTotaleOccupati = new Integer(0);
							numeroTotaleOccupati += numAreaDispOccupati;
							switch (singleDispoArea.getArea().getIdDArea()) {
							case "TER_INT":
								dp.setNumPLOccupatiTerapiaIntensiva(String.valueOf(numAreaDispOccupati));
								break;
							case "TER_SEMI_INT":
								dp.setNumPLOccupatiTerapiaSemiIntensiva(String.valueOf(numAreaDispOccupati));
								break;
							case "INFETT":
								dp.setNumPLOccupatiMalInfettive(String.valueOf(numAreaDispOccupati));
								break;
							case "PNEUMO":
								dp.setNumPLOccupatiPneumologia(String.valueOf(numAreaDispOccupati));
								break;
							case "MED_INT":
								dp.setNumPLOccupatiMediaIntensita(String.valueOf(numAreaDispOccupati));
								break;
							default:
								dp.setNumPLOccupatiAltro(String.valueOf(numAreaDispOccupati));
								break;
							}
						}
					}
					if (numeroTotaleDisponibili != null)
						dp.setNumPLAttivatiTotale(String.valueOf(numeroTotaleDisponibili));
					if (numeroTotaleOccupati != null)
						dp.setNumPLOccupatiTotale(String.valueOf(numeroTotaleOccupati));
				}
				list.add(dp);
			}
		}
		return list;
	}


	@Override
	public Response getElencoDisponibilitaReportTransposedXlsx(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {

			UserLogged currentUser = getBeService().getCurrentUser(req);

			// TODO estrarre l'idEnte o gli idEnte o il filtro che server a cui e abilitato
			// un utente e passarlo sotto.
			// Ad ora se null viene restituito tutto
			List<DisponibilitaForReportTransposed> retList = disponibilitaMapper.selectForReportTransposedByIdEnte(null,
					true);

			StreamingOutput os = convertObjectsToXlsx(retList);
			String fileName = "disponibilita.xlsx";
			return Response.ok(os).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
		} catch (Exception e) {
			log.error("Errore nel recupero elenco struttura ", e);
			return Response.serverError().build();
		}
	}

	private class EnteTarget {
		int numRighe;
		int firstRow;
		Integer numTotaleAttivatiEnte=null;
	}
	
	private static final String EXCEL_TOT_COL_LETTER = "T";
	private static final int EXCEL_TOTALE_CELL_INDEX = 21;
	private static final int EXCEL_LAST_PL_INDEX = 19;
	private static final int EXCEL_FIRST_PL_INDEX = 4;
	private static final int EXCEL_TIME_CELL_INDEX = 24;
	
	/**
	 * Correzione fix colonna occupati 'Altro'
	 * @param list
	 * @return
	 */
	public StreamingOutput convertObjectsToXlsx(List<DisponibilitaForReportTransposed> list) {
		final XSSFWorkbook workbook = createWorkbook();
		
		if (list!=null)
		{
			Map<Integer, EnteTarget> enti = new HashMap<Integer, EnteTarget>();
			Sheet sheet= workbook.getSheet("Situazione");
			
			// fix date for last column
			CellStyle cellDateStyle = sheet.getRow(0).getCell(EXCEL_TIME_CELL_INDEX).getCellStyle();
			cellDateStyle.setDataFormat(
					workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));
			sheet.getRow(0).getCell(EXCEL_TIME_CELL_INDEX).setCellStyle(cellDateStyle);
			
			int indexRow = 1;
			for (DisponibilitaForReportTransposed disp : list) {
				
				String naturaStruttura = disp.getStruttura().getNatura();
				boolean isNaturaStruttraRSAorCasermaOrRicettiva = "RSA".equals(naturaStruttura) || "Caserma".equals(naturaStruttura) || "Ricettiva".equals(naturaStruttura) ; 
				
				if (disp.getDisponibilitaArea()!=null && !disp.getDisponibilitaArea().isEmpty())
				{
					EnteTarget enteTarget = enti.get(disp.getEnte().getIdEnte());
					if (enteTarget==null)
					{
						enti.put(disp.getEnte().getIdEnte(), new EnteTarget());
						enteTarget = enti.get(disp.getEnte().getIdEnte());
						enteTarget.firstRow = indexRow;
					}
					enteTarget.numRighe++;
					int indexColumn=0;
					// prime colonne con struttura
					Row row = sheet.createRow(indexRow);
					Cell cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					cell.setCellValue(disp.getEnte().getNome());
					indexColumn++;
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					if (disp.getEnte().getTotPostiTarget()!=null)
						cell.setCellValue(disp.getEnte().getTotPostiTarget());
					indexColumn++;
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					cell.setCellValue(disp.getStruttura().getNome());
					indexColumn++;
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					cell.setCellValue(disp.getStruttura().getNatura());
					indexColumn++;
					// Fine colonne con struttura
					Integer numeroTotaleStrutturaDisponibili = null;
					Integer numeroTotaleStrutturaOccupati = null;
					Date dataAggiornamentoStruttura = null;
					// inserisco i colori delle celle su tutte le colonne
					for(int i=EXCEL_FIRST_PL_INDEX;i<EXCEL_LAST_PL_INDEX;i++)
					{
						cell = row.createCell(i);
						cell.setCellStyle(sheet.getRow(0).getCell(i).getCellStyle());
					}
					
					
					Integer nPlAttivatiAltro = null;
					Integer nPlOccupatiAltro = null;
					for (DisponibilitaArea singleDispoArea : disp.getDisponibilitaArea()) {
						
						
						if (singleDispoArea.getDisponibilita().getDataEvento()!=null)
						{
							if (dataAggiornamentoStruttura==null)
							{
								dataAggiornamentoStruttura = singleDispoArea.getDisponibilita().getDataEvento();
							}
							else if (dataAggiornamentoStruttura.before(singleDispoArea.getDisponibilita().getDataEvento()))
							{
								dataAggiornamentoStruttura = singleDispoArea.getDisponibilita().getDataEvento();
							}
						}
						
						
						
						Integer columnAttivatiIndex = areaColumnAttivati().get(singleDispoArea.getArea().getIdDArea());
						if (columnAttivatiIndex==null)
							columnAttivatiIndex =  areaColumnAttivati().get("ALTRO");
						
						Cell cellAttivati = row.createCell(columnAttivatiIndex);
						cellAttivati.setCellStyle(sheet.getRow(0).getCell(columnAttivatiIndex).getCellStyle());
						
						Integer numAreaDispDisponibili = null;
						
						
						if (!singleDispoArea.getArea().getIdDArea().equals("IN_ATTESA"))
						{
							if (singleDispoArea.getDisponibilita() != null
									&& singleDispoArea.getDisponibilita().getPostiDisponibili() != null)
								numAreaDispDisponibili = singleDispoArea.getDisponibilita().getPostiDisponibili();
							
							
							
							
							if (numAreaDispDisponibili != null) {
								if (numeroTotaleStrutturaDisponibili == null)
									numeroTotaleStrutturaDisponibili = new Integer(0);
								numeroTotaleStrutturaDisponibili += numAreaDispDisponibili;
								if(!isNaturaStruttraRSAorCasermaOrRicettiva) {
								cellAttivati.setCellValue((numAreaDispDisponibili));
								}else {
									nPlAttivatiAltro = numAreaDispDisponibili;
								}
							}
						}
						

						Integer numAreaDispOccupati = null;
						
						int numerocolonna = columnAttivatiIndex+1;
						//System.out.println("AREA NOME: " + singleDispoArea.getArea().getNome());
						//System.out.println("DAREA NOME: " + singleDispoArea.getArea().getIdDArea());
						//System.out.println("occupati NUMERO COLONNA: " + numerocolonna);
						
						
						if(singleDispoArea.getArea().getIdDArea().equalsIgnoreCase("IN_ATTESA"))
							numerocolonna = columnAttivatiIndex;
							
						Cell cellOccupati = row.createCell(numerocolonna);//Cell cellOccupati = row.createCell(columnAttivatiIndex+1);
						cellOccupati.setCellStyle(sheet.getRow(0).getCell(numerocolonna).getCellStyle());//cellOccupati.setCellStyle(sheet.getRow(0).getCell(columnAttivatiIndex+1).getCellStyle());	
						if (singleDispoArea.getDisponibilita() != null
								&& singleDispoArea.getDisponibilita().getPostiOccupati() != null)
							numAreaDispOccupati = singleDispoArea.getDisponibilita().getPostiOccupati();
						if (numAreaDispOccupati != null) {
							if (numeroTotaleStrutturaOccupati == null)
								numeroTotaleStrutturaOccupati = new Integer(0);
							numeroTotaleStrutturaOccupati += numAreaDispOccupati;
							if(!isNaturaStruttraRSAorCasermaOrRicettiva) {
								cellOccupati.setCellValue((numAreaDispOccupati));
							}else {
								nPlOccupatiAltro = numAreaDispOccupati;
							}
						}


						
						if (singleDispoArea.getDisponibilita()!=null 
								&& singleDispoArea.getDisponibilita().getPostiDisponibili()!=null)
						{
							if (enteTarget.numTotaleAttivatiEnte==null) enteTarget.numTotaleAttivatiEnte=new Integer(0);
								enteTarget.numTotaleAttivatiEnte += singleDispoArea.getDisponibilita().getPostiDisponibili();
						}

					} // fine for areadisponibilita
					// aggiornamento totale attivati ente
					indexColumn=EXCEL_LAST_PL_INDEX;
					
				
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					if (!isNaturaStruttraRSAorCasermaOrRicettiva && numeroTotaleStrutturaDisponibili!=null)
					{
						cell.setCellType(CellType.FORMULA);
						cell.setCellFormula(formula("SUM", columnNameAttivati, indexRow+1));
					}
					indexColumn++;
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					if (!isNaturaStruttraRSAorCasermaOrRicettiva && numeroTotaleStrutturaOccupati!=null) {
						cell.setCellType(CellType.FORMULA);
						cell.setCellFormula(formula("SUM", columnNameOccupati, indexRow+1));
					}
					indexColumn++;
					//stile per totale ente e aggiornamento
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					indexColumn++;
					
					
					
					
					// Totali per Posti letto attivati strutture intermedie
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					
					if (isNaturaStruttraRSAorCasermaOrRicettiva && numeroTotaleStrutturaOccupati!=null) {
						//cell.setCellType(CellType.FORMULA);
						//cell.setCellFormula(formula("SUM", columnNameAttivati, indexRow+1));
						cell.setCellValue((nPlAttivatiAltro));
						
					}
					indexColumn++;
					
					
					// Totali per Posti letto occupati strutture intermedie
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					if (isNaturaStruttraRSAorCasermaOrRicettiva && numeroTotaleStrutturaOccupati!=null) {
						//cell.setCellType(CellType.FORMULA);
						//cell.setCellFormula(formula("SUM", columnNameOccupati, indexRow+1));
						cell.setCellValue((nPlOccupatiAltro));

					}
					indexColumn++;
					
					
					// Dati aggiornati al
					cell = row.createCell(indexColumn);
					cell.setCellStyle(sheet.getRow(0).getCell(indexColumn).getCellStyle());
					if (dataAggiornamentoStruttura!=null)
						cell.setCellValue(dataAggiornamentoStruttura);
					indexColumn++;
					
					
					indexRow++;
				}
			}
			
			// totalone
			Cell cellTot = sheet.createRow(indexRow).createCell(3);
			cellTot.setCellStyle(sheet.getRow(0).getCell(3).getCellStyle());
			cellTot.setCellValue("Totale");
			
			for(int i=EXCEL_FIRST_PL_INDEX;i<EXCEL_TIME_CELL_INDEX;i++)
			{
				cellTot = sheet.getRow(indexRow).createCell(i);
				cellTot.setCellStyle(sheet.getRow(0).getCell(i).getCellStyle());
				cellTot.setCellType(CellType.FORMULA);
				cellTot.setCellFormula(formula("SUM", CellReference.convertNumToColString(cellTot.getColumnIndex())+"2", 
						CellReference.convertNumToColString(cellTot.getColumnIndex())+""+indexRow));
			}

			
			// merge colonne
			for (Map.Entry<Integer, EnteTarget> row : enti.entrySet()) {
				// Totale per ente da mergiare
				Cell cell = sheet.getRow(row.getValue().firstRow).createCell(EXCEL_TOTALE_CELL_INDEX);
				cell.setCellStyle(sheet.getRow(0).getCell(EXCEL_TOTALE_CELL_INDEX).getCellStyle());
				if (row.getValue().numTotaleAttivatiEnte!=null) {
//					cell.setCellValue(row.getValue().numTotaleAttivatiEnte);
					cell.setCellType(CellType.FORMULA);
					cell.setCellFormula(formula("SUM", EXCEL_TOT_COL_LETTER.concat(Integer.toString(row.getValue().firstRow+1)), 
							EXCEL_TOT_COL_LETTER.concat(Integer.toString(row.getValue().firstRow+row.getValue().numRighe))));
				}
				if (row.getValue().numRighe>1)
				{
				sheet.addMergedRegion(new CellRangeAddress(row.getValue().firstRow, 
						row.getValue().firstRow+row.getValue().numRighe-1, 
						0, 0));
				sheet.addMergedRegion(new CellRangeAddress(row.getValue().firstRow, 
						row.getValue().firstRow+row.getValue().numRighe-1, 
						1, 1));
				sheet.addMergedRegion(new CellRangeAddress(row.getValue().firstRow, 
						row.getValue().firstRow+row.getValue().numRighe-1, 
						EXCEL_TOTALE_CELL_INDEX, EXCEL_TOTALE_CELL_INDEX));
				}
			}
			
			
			
			cellTot = sheet.getRow(indexRow).createCell(22);
			cellTot.setCellStyle(sheet.getRow(0).getCell(22).getCellStyle());
			cellTot.setCellType(CellType.FORMULA);
			cellTot.setCellFormula(formula("SUM", CellReference.convertNumToColString(cellTot.getColumnIndex())+"2", 
					CellReference.convertNumToColString(cellTot.getColumnIndex())+""+indexRow));
			
			cellTot = sheet.getRow(indexRow).createCell(23);
			cellTot.setCellStyle(sheet.getRow(0).getCell(23).getCellStyle());
			cellTot.setCellType(CellType.FORMULA);
			cellTot.setCellFormula(formula("SUM", CellReference.convertNumToColString(cellTot.getColumnIndex())+"2", 
					CellReference.convertNumToColString(cellTot.getColumnIndex())+""+indexRow));
			
			
			
			
		}

		StreamingOutput streamOutput = new StreamingOutput() {

			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException {
				workbook.write(out);
			}
			
		};
		
		return streamOutput;
	}

	

	private static XSSFWorkbook createWorkbook() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Situazione");
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

	private static String[] headers = {
			"Descrizione Ente","Target minimo richiesto",
			"Descrizione Struttura", "Natura Struttura", 
			"N. PL  attivati media intensita", "N. PL occupati media intensita",	
			"N. PL  attivati terapia intensiva","N. PL occupati intensiva",
			"N. PL  attivati terapia semintensiva","N. PL occupati semiintensiva",
			"N. PL  attivati NIV in letti ordinari","N. PL occupati NIV in letti ordinari",
			"N. PL  attivati pneumologia","N. PL occupati pneumologia",
			"N. PL  attivati mal. Infettive","N. PL occupati malattie infettive",
			"N. PL  attivati altro","N. PL occupati altro",
			"N. PL  occupati in attesa di conferma TEST",
			"TOTALE PL COVID ATTIVATI OSPEDALE","TOTALE PL COVID OCCUPATI OSPEDALE",
			"TOTALE PL COVID ATTIVATI ENTE",
			"N. PL  attivati STRUTTURE INTERMEDIE",
			"N. PL  occupati STRUTTURE INTERMEDIE",
			"dati aggiornati al"
	};
	private static IndexedColors[] headersColors = {
			IndexedColors.WHITE,IndexedColors.WHITE, 
			IndexedColors.WHITE,IndexedColors.WHITE, 
			IndexedColors.LIGHT_TURQUOISE,IndexedColors.LIGHT_TURQUOISE,
			IndexedColors.LIGHT_YELLOW, IndexedColors.LIGHT_YELLOW,
			IndexedColors.LIGHT_YELLOW, IndexedColors.LIGHT_YELLOW,
			IndexedColors.LIGHT_TURQUOISE,IndexedColors.LIGHT_TURQUOISE,
			IndexedColors.LIGHT_TURQUOISE,IndexedColors.LIGHT_TURQUOISE,
			IndexedColors.LIGHT_YELLOW, IndexedColors.LIGHT_YELLOW,
			IndexedColors.LIGHT_TURQUOISE,IndexedColors.LIGHT_TURQUOISE,
			IndexedColors.LIGHT_ORANGE,
			IndexedColors.LIGHT_GREEN,IndexedColors.LIGHT_GREEN,
			IndexedColors.LIGHT_GREEN,
			
			IndexedColors.LIGHT_ORANGE,IndexedColors.LIGHT_ORANGE,
			
			IndexedColors.LIGHT_YELLOW
	};
	private static Integer[] headersWidth = {
			6000,2000, 
			6000,6000, 
			2000, 2000,
			2000, 2000,
			2000, 2000,
			2000, 2000,
			2000, 2000,
			2000, 2000,
			2000, 2000,
			2000,
			2000, 2000,
			2000,
			
			2000,2000,
			
			2000
	};	
	
	private static Map<String, Integer> areaColumnAttivati()
	{
		int startNumeroPostiIndex = 4;
		Map<String, Integer> areaColumn = new HashMap<String, Integer>();
		areaColumn.put("MED_INT", startNumeroPostiIndex);
		areaColumn.put("TER_INT", startNumeroPostiIndex+2);
		areaColumn.put("TER_SEMI_INT", startNumeroPostiIndex+4);
		areaColumn.put("NIV", startNumeroPostiIndex+6);
		areaColumn.put("PNEUMO", startNumeroPostiIndex+8);
		areaColumn.put("INFETT", startNumeroPostiIndex+10);
		areaColumn.put("ALTRO", startNumeroPostiIndex+12);
		areaColumn.put("IN_ATTESA", startNumeroPostiIndex+14);
		return areaColumn;
	}
	
	private static String formula(String formula, String[] cols, int row)
	{
		// SUM(A1;B2;V3)
		return formula+"("+StringUtils.arrayToDelimitedString(cols, row+",")+row+")";		
	}
	private static String formula(String formula, String cell1, String cell2)
	{
		// SOMMA(Q2:Q7)
		return formula+"("+cell1+":"+cell2+")";		
	}
	private static String[] columnNameAttivati=
	{
		"Q","O","M","K","I","G","E"
	};
	private static String[] columnNameOccupati=
	{
		"S","R","P","N","L","J","H","F"
	};
	
	public static void main(String[] args) throws Exception {
		XSSFWorkbook workbook = DisponibilitaApiServiceImpl.createWorkbook();
		FileOutputStream fileOut = new FileOutputStream("d:/a.xlsx");
		Sheet sheet= workbook.getSheet("Situazione");
		for (int i = 1; i<  5; i++) 
		{
			int j=0;
			Row row = sheet.createRow(i);
			Cell cell = row.createCell(j);
			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
			cell.setCellValue("Nome_"+i);
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
			cell.setCellValue("Struttur_"+i);
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
			cell.setCellValue("natura_"+i);
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
			cell.setCellValue("");
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(sheet.getRow(0).getCell(j).getCellStyle());
			cell.setCellValue(7);
			j++;
		}
		
		//write this workbook to an Outputstream.
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		workbook.close();
	}
}