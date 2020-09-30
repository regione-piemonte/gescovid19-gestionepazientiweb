package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.service.BackendService;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.Referto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.referto.RefertoDettaglio;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoDettaglioMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.referto.generated.RefertoMapper;

@Component
public class ImportReferto {

	@Autowired
	private RefertoMapper refertoMapper;
	
	@Autowired
	private RefertoDettaglioMapper refertoDettaglioMapper;
	
	@Autowired
	public BackendService beService;
	
	//InputStream uploadedInputStream,
	
	public static void main(String[] args) {
//		Matcher matcher = Formato.CodiceFiscale.getPattern().matcher("4SIDNC8dfdfdL219W");
//		System.out.println("CF match test: "+matcher.matches());
//		
//		System.out.println(Formato.AslPiemontesi.getPattern().matcher("908").matches());
//		
//		System.out.println(Colonna.tipoRichiesta.ordinal());
//		System.out.println(Colonna.values().length);
		
		
		
		//String testLinaConVuoti = "ISTITUTO TAMP;1111;12345;301;301;08;AAAAAA00A11B000J;;MNTSLV80A01H163Y;MONTALBANO;SALVO;M;01/01/1980;L219;L219;Via larga 1;L219;Via stretta 1;2.16.840.1.113883.2.9.2.10.4.4.1111;91.12.S ;Tampone;20200510050525+0200;P;Positivo;;;";
		//String testLinaOK="STUDIO MEDICO MIRAFIORI S.R.L.;;;;301;09;BSLSDR67L62G778U;;RCLGPP66E57L259C;ERCULANESE;GIUSEPPA;F;17/05/1966;=063084;=001309;=001309;=001309;;9_266505_20200727141052;91.31.c;Immunometrico IGG;20200727141052+0200;P;POSITIVO;Index (Index (S/C));3.6;Positivo >=1.4;";
		String testLinea="STUDIO MEDICO MIRAFIORI S.R.L.;;;;301;09;BSLSDR67L62G778U;;RCLGPP66E57L259C;ERCULANESE;GIUSEPPA;F;17/05/1966;=063084;=001309;=001309;=001309;;9_266505_20200727141052;91.31.c;Immunometrico IGG;20200727141052+0200;P;POSITIVO;Index (Index (S/C));;";
		Linea l = new Linea(testLinea, 1);
		//System.out.println(l.get(Colonna.referenceRange, String.class));
		System.out.println("Value colonna code: " + l.get(Colonna.code, String.class) +"\n\n");
		int i = 1;
		for(Colonna c : Colonna.values()) {
			System.out.println(c.toString() +" (posix " + i + ") value [ "+l.getString(c) +" ]");
			i++;
		}
		
//		String testLine = "ISTITUTO DEI TAMPONI;12345;00000A45;301;301;01;AAAAAA00A11B000J;433675;AAAAAA00A11B000J;Demo;Prova;M;01/01/1976;G674;G674;via prova;G674;via prova 2;123;91.31.c;Ab ANTI SARS-CoV-2 S1/S2 IgG;20200510050525+0200;N;NEGATIVO;AU/mL;3.8;Positivo>15 Dubbio 12-15 Negativo>12";
//		String testLine2ko = "ISTITUTO DEI TAMPONI;1234g5;00000A45;301;301;01;AAAAAA00A11B000J;433675;AAAAAA00A11B000J;Demo;Prova;M;01g/01/1976;G674;G674;via prova;G674;via prova 2;123;91.31.c;Ab ANTI SARS-CoV-2 S1/S2 IgG;20200510050525+0200;N;NEGATIVO;AU/mL;3.8;Positivo>15 Dubbio 12-15 Negativo>12";
//		//formalCheck(testLine);
//		
		//List<String> linee = new ArrayList<>();
		//linee.add(testLine+";dfdf");
		//linee.add(testLinaConVuoti);
		//new ImportReferto().importReferti(linee,  "utente test");
	}
		
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void importReferti(List<String> lineeString, String utenteOperazione) {
		long i = 1;
		
		List<Linea> lineeList = new ArrayList<>();
		StringBuilder msgBuilder = new StringBuilder();
		for(String lineaCsv : lineeString) {
			Linea linea;
			try {
				linea = new Linea(lineaCsv, i);
				lineeList.add(linea);
			} catch (RuntimeException e) {
				msgBuilder.append("Errore su riga "+i +": " +e.getMessage()+"\n");
			}
			i++;
		}
		String msg = msgBuilder.toString();
		checkCondition(msg.isEmpty(), "Import annullato:\n" + msg);
		
		for(Linea linea : lineeList) {
			importRefertoLinea(linea, utenteOperazione);
		}
		
	}
	
	
	
//	private void importRefertoLinea(String lineaCsv, long numeroLinea) {
//		Linea l = new Linea(lineaCsv, numeroLinea);
//		importRefertoLinea(l);
//	}


	private void importRefertoLinea(Linea l, String utenteOperazione) {
		Referto referto = toReferto(l, utenteOperazione);
		refertoMapper.insert(referto);
		
		RefertoDettaglio refertoDettaglio = toRefertoDettaglio(l);
		refertoDettaglio.setIdMessaggio(referto.getIdMessaggio());
		refertoDettaglioMapper.insert(refertoDettaglio);
	}

	private Referto toReferto(Linea l, String utenteOperazione) {
		
		Referto r = new Referto();
		
		r.setDescrStruttura(l.get(Colonna.descrStruttura, String.class));
		r.setIdStruttura(l.get(Colonna.idStruttura, Long.class));
		r.setMatricolaStruttura(l.get(Colonna.matrStruttura, String.class));
		r.setAsr(l.get(Colonna.idAsr, String.class));
		r.setAslLaboratorio(l.get(Colonna.aslAppartenenza, String.class));
		r.setCodTipoRichiesta(l.get(Colonna.tipoRichiesta, String.class));
		r.setLegalauthenticator(l.get(Colonna.legalAuthenticator, String.class));
		r.setIdAura(l.get(Colonna.idAura, String.class));
		r.setCodfisc(l.get(Colonna.codFisc, String.class));
		r.setCognome(l.get(Colonna.cognome, String.class));
		r.setNome(l.get(Colonna.nome, String.class));
		r.setSesso(l.get(Colonna.sesso, String.class));
		r.setDatanascita(l.get(Colonna.dataDiNascita, Date.class));
		r.setComunenascita(l.get(Colonna.comuneDiNascita, String.class));
		r.setResIstat(l.get(Colonna.residenza, String.class)); // valore esempio: G674
		r.setResAddr(l.get(Colonna.indirizzoResidenza, String.class));
		r.setDomIstat(l.get(Colonna.domicilio, String.class));
		r.setDomAddr(l.get(Colonna.indirizzoDomicilio, String.class));
		r.setIdDocumento(l.get(Colonna.idDocumento, String.class));
		//r.setIdMessaggio(1);// dalla sequence è la PK
		r.setDataCreazione(new Date());
		r.setCodCl("app.upload.csv");
		r.setUtenteoperazione(utenteOperazione);
		return r;
	}
	
	private static RefertoDettaglio toRefertoDettaglio(Linea l) {
		RefertoDettaglio rd = new RefertoDettaglio();
		rd.setCodeCode(l.get(Colonna.code, String.class));
		rd.setCodeDisplayname(l.get(Colonna.displayName, String.class));
		rd.setEffectivetimeValue(l.get(Colonna.effectiveTime, Date.class));
		rd.setValueCode(l.get(Colonna.esitoCode, String.class));
		rd.setValueDisplayname(l.get(Colonna.esitoDesc, String.class));
		rd.setValueUnit(l.get(Colonna.unit, String.class));
		rd.setValueValue(l.get(Colonna.value, String.class));
		rd.setRefrangeText(l.get(Colonna.referenceRange, String.class));
		rd.setDataCreazione(new Date());
		return rd;
	}
	
	private static void formalCheck(String lineCsv) {
		String[] a = lineCsv.split(" *; *");
		formalCheck(a);
	}
	
	private static void formalCheck(String[] l) {
		int numeroParametri = Colonna.values().length;
		checkCondition(l.length == numeroParametri, "Il tracciato prevede "+numeroParametri +" colonne. Colonne presenti: "+l.length);
		
		int ultimaColonna = l.length -1;
		boolean verificaUltimoCarattere= l[ultimaColonna].contains(";");
		checkCondition(!verificaUltimoCarattere, "Il tracciato non rispetta il formato atteso" );

		
		StringBuilder msgBuilder = new StringBuilder();
		String valueCode = "";
		
		for(int i = 0; i<l.length; i++) {
			String s = l[i];
			Colonna e = Colonna.values()[i];
			
			if(e.toString().equalsIgnoreCase("code"))
				valueCode = s;
			
			if(e.isObbligatorio() && StringUtils.isBlank(s)) {
				msgBuilder.append("\n").append("La colonna "+ e.toString().toUpperCase() +" con posizione "+ (i+1) + " non valorizza il parametro obbligatorio.");
				continue;
			}
			
			if(e.toString().equalsIgnoreCase("unit") || e.toString().equalsIgnoreCase("value") || e.toString().equalsIgnoreCase("referenceRange") ) {
				
				if((valueCode.equalsIgnoreCase("91.31.c") || valueCode.equalsIgnoreCase("91.31.d")) && (StringUtils.isEmpty(s) || s.equalsIgnoreCase(";")) ) 
				{
					msgBuilder.append("\n").append("La colonna "+ e.toString().toUpperCase() + " con posizione "+ (i+1) + " non valorizza il parametro obbligatorio.");
				}
			}
			
			Formato formato = e.getFormato();
			appendCondition(msgBuilder, StringUtils.isBlank(s) || formato.match(s), 
					"La colonna con posizione "+ (i+1) 
						+ " non rispetta il formato atteso: "
						+ formato.getDescrizione()+ " valore attuale: "+s );
			
		}
		
		String msg = msgBuilder.toString();
		checkCondition(msg.isEmpty(), "Si sono verificati i seguenti errori formali: " + msg);
		
	}
	
	

	
	private static void appendCondition(StringBuilder msgBuilder, boolean condition, String msg){
		if(!condition) {
			msgBuilder.append("\n").append(msg);
		}
	}
	
	private static void checkCondition(boolean condition, String msg){
		if(!condition) {
			throw new IllegalArgumentException(msg);
		}
	}

	enum Formato {
		StringaFacoltativa(Pattern.compile(".*"), "Stringa generica"), // [\\w ]+
		StringaObbligatoria(Pattern.compile(".+"), "Stringa generica obbligatoria, occorre almeno un carattere"), // [\\w ]+
		Data1(Pattern.compile("[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}"), "Data nel formato GG/MM/YYYY"), //GG/MM/YYYY
		Data2(Pattern.compile("[0-9]{14}[\\+\\-][0-9]{4}"), "Data nel formato YYYYMMDDHHMMSS+0200"), //Valore di esempio: 20200510050525+0200
		NumberReale(Pattern.compile("[0-9]+[\\.,]*[0-9]*"), "Numero reale"),
		NumberIntero(Pattern.compile("[0-9]+"), "Numero intero (no virgole o punti)"),
		CodiceFiscale(Pattern.compile("NON-POSSIEDE-CF|[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]", Pattern.CASE_INSENSITIVE), "Codice fiscale"),
		TipoRichiesta(Pattern.compile("[0-9]{2}"), "Tipo richiesta (00, 01, 02, ecc..)"),
		AslPiemontesi(Pattern.compile("301|203|204|205|206|207|208|209|210|211|212|213|904|905|906|907|908|909"), "Asl Piemontesi (301, 203, 204, ecc..)"),
		EsitoCode(Pattern.compile("N|P|NP|D|I"), "Codice Esito (Ammessi: N, P, NP, D, I)"),
		CodeCode(Pattern.compile("91\\.31\\.C|91\\.31\\.D|91\\.12\\.S", Pattern.CASE_INSENSITIVE), "Code (Ammessi: 91.31.C, 91.31.D, 91.12.S)"),
		
		
		;
		
		private Pattern pattern;
		private String descrizione;
		
		Formato(Pattern pattern, String descrizione){
			this.pattern = pattern;
			this.descrizione = descrizione;
		}

		public Pattern getPattern() {
			return pattern;
		}
		
		public boolean match(String s) {
			Matcher matcher = pattern.matcher(s);
			return matcher.matches();
		}

		public String getDescrizione() {
			return descrizione;
		}
		
		
	}

	
	
	enum Colonna {
		 descrStruttura(Formato.StringaObbligatoria, true),
		 idStruttura(Formato.NumberIntero), //unico int8 su db
		 matrStruttura(Formato.StringaFacoltativa),
		 idAsr(Formato.StringaFacoltativa), //String su db
		 aslAppartenenza(Formato.AslPiemontesi, true), //301, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 904, 905, 906, 907, 908, 909
		 tipoRichiesta(Formato.TipoRichiesta, true),
		 legalAuthenticator(Formato.StringaObbligatoria, true),
		 idAura(Formato.StringaFacoltativa),
		 codFisc(Formato.CodiceFiscale, true),
		 cognome(Formato.StringaObbligatoria, true),
		 nome(Formato.StringaObbligatoria, true),
		 sesso(Formato.StringaObbligatoria, true),
		 dataDiNascita(Formato.Data1, true),
		 comuneDiNascita(Formato.StringaObbligatoria, true),
		 residenza(Formato.StringaObbligatoria, true),
		 indirizzoResidenza(Formato.StringaFacoltativa),
		 domicilio(Formato.StringaFacoltativa, true), // messo a true da raffa 11/08/2020
		 indirizzoDomicilio(Formato.StringaFacoltativa),
		 idDocumento(Formato.StringaObbligatoria, true), //String su db
		 code(Formato.CodeCode, true), //91.31.c - test sierologico igg 91.31.d - test sierologico igm
		 displayName(Formato.StringaObbligatoria, true),
		 effectiveTime(Formato.Data2, true), // Data 20200510050525+0200
		 esitoCode(Formato.StringaObbligatoria, true), //N,P,NP,D,I
		 esitoDesc(Formato.StringaObbligatoria, true), //NEGATIVO, POSITIVO, NON PERVENUTO, DUBBIO, INDETERMINATO
		 unit(Formato.StringaFacoltativa),
		 value(Formato.StringaFacoltativa),
		 referenceRange(Formato.StringaFacoltativa),
		 
		 ;
		
		boolean obbligatorio; 
		private Formato formato;
		
		Colonna(Formato formato){
			this.formato = formato;
			this.obbligatorio = false;
		}
		
		Colonna(Formato formato, boolean obbligatorio){
			this.formato = formato;
			this.obbligatorio = obbligatorio;
		}

		public Formato getFormato() {
			return formato;
		}

		public boolean isObbligatorio() {
			return obbligatorio;
		}
		
		
		
		
		
	}

	private static class Linea {
		long numero;
		String[] l;
		
		public Linea(String lineaCsv, long numero) {
			checkCondition(!StringUtils.isBlank(lineaCsv), "Linea "+numero+" vuota!");
			this.l =  lineaCsv.split(";", Colonna.values().length);
			
			formalCheck(l);
		}
		
		
		
		public String getString(Colonna c) {
			return l[c.ordinal()];
		}
		
		public <T> T get(Colonna c, Class<T> clazz) {
			String valueStr = getString(c);
			if(StringUtils.isBlank(valueStr)) {
				return null;
			}
			Formato formato = c.getFormato();
			if(Formato.StringaFacoltativa.equals(formato) 
					|| Formato.StringaObbligatoria.equals(formato)
					|| Formato.AslPiemontesi.equals(formato)
					|| Formato.EsitoCode.equals(formato)
					|| Formato.TipoRichiesta.equals(formato)
					|| Formato.CodeCode.equals(formato)
					|| Formato.CodiceFiscale.equals(formato)) {
				return (T) valueStr;
			} else if (Formato.Data1.equals(formato)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					return (T) sdf.parse(valueStr);
				} catch (ParseException e) {
					throw new IllegalArgumentException("Data non corretta: "+ valueStr, e);
				}
			} else if (Formato.Data2.equals(formato)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssZ");
				try {
					return (T) sdf.parse(valueStr);
				} catch (ParseException e) {
					throw new IllegalArgumentException("Data non corretta: "+ valueStr, e);
				}
			} else if(Formato.NumberIntero.equals(formato)) {
				if(Long.class.equals(clazz)) {
					return (T) Long.valueOf(valueStr);
				} else if(Integer.class.equals(clazz)) {
					return (T) Integer.valueOf(valueStr);
				}
			} else if(Formato.NumberReale.equals(formato)) {
				if(Double.class.equals(clazz)) {
					return (T) Double.valueOf(valueStr);
				} else if(BigDecimal.class.equals(clazz)) {
					return (T) BigDecimal.valueOf(Double.valueOf(valueStr));
				}
			}
			
			
			throw new IllegalArgumentException("Conversione Formato non supportato: " + formato.name() + " clazz: "+ (clazz!=null?clazz.getSimpleName():null));
			
		}

		public long getNumero() {
			return numero;
		}

		
	}
	
		

		
	
}
