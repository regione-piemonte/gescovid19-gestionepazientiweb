package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.DecodificheApi;
import it.csi.gestionepazienti.gestionepazientiweb.business.service.BackendService;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asl;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.DecodeTipoEvento;
import it.csi.gestionepazienti.gestionepazientiweb.dto.EsitoDimissioni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Laboratorio;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Nazione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Regione;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RepartoRicovero;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RisTampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoContattoTipo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TamponeMotivo;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TestCovid;
import it.csi.gestionepazienti.gestionepazientiweb.dto.TipoSoggetto;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AslMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AsrMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.ComuniMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.DecodeTipoEventoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.EsitoDimissioniMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.LaboratorioMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.RepartoRicoveroMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.RisTamponeMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.SoggettoContattoTipoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TamponeMotivoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TestCovidMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.TipoSoggettoMapper;
import it.csi.gestionepazienti.gestionepazientiweb.util.SpringSupportedResource;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DecodificheApiImpl extends SpringSupportedResource implements DecodificheApi{

	private static final int ID_LABOR_ALTRI = 6;
	@Autowired
	public BackendService beService;

	public BackendService getBeService() {
//	if (beService==null) {
//  		beService = (BackendService)SpringApplicationContextHelper.getBean("backendService");
//  	}
		return beService;
	}

	public void setBeService(BackendService beService) {
		this.beService = beService;
	}	
	
	
	@Autowired
	AsrMapper asrMapper;
	
	@Autowired
	AslMapper aslMapper;
	
	@Override
	public Response getElencoAsl(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<Asl> aslList = aslMapper.selectAll();
			return Response.ok(aslList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}


	
	@Override
	public Response getElencoAsr(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<Asr> asrList = asrMapper.selectAll();
			return Response.ok(asrList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}

	
	@Autowired
	ComuniMapper comuniMapper;
	
	@Override
	public Response getElencoComuni(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<Comuni> comuniList = comuniMapper.selectAll();
			return Response.ok(comuniList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}
	
	
	
	@Autowired
	TestCovidMapper testCovidMapper;
	
	@Override
	public Response getElencoTestCovid(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<TestCovid> retList = testCovidMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}	
	

	
	@Autowired
	RepartoRicoveroMapper repartoRicoveroMapper;
	
	@Override
	public Response getElencoRepartoRicovero(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<RepartoRicovero> retList = repartoRicoveroMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}	
	
	
	@Autowired
	RisTamponeMapper risTamponeMapper;
	
	@Override
	public Response getElencoRisTampone(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<RisTampone> retList = risTamponeMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}		
	
	
	
	
	@Autowired
	EsitoDimissioniMapper esitoDimissioniMapper;
	
	@Override
	public Response getElencoEsitoDimissioni(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<EsitoDimissioni> retList = esitoDimissioniMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}		
		
	
	@Autowired
	LaboratorioMapper laboratorioMapper;
	
	@Override
	public Response getElencoLaboratorio(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<Laboratorio> retList = laboratorioMapper.selectAll();
			List<Laboratorio> retListSenzaAltri = new ArrayList<Laboratorio>();
			for (Laboratorio laboratorio : retList) {
				if (laboratorio.getIdLaboratorio().intValue()!=ID_LABOR_ALTRI)
					retListSenzaAltri.add(laboratorio);
			}
			return Response.ok(retListSenzaAltri).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}	
	
	@Autowired
	DecodeTipoEventoMapper decodeTipoEventoMapper;
	
	@Override
	public Response getElencoTipoEvento(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			List<DecodeTipoEvento> retList = decodeTipoEventoMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}	
	
	@Autowired
	TipoSoggettoMapper tipoSoggettoMapper;
	
	@Override
	public Response getElencoTipoSoggetto(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req){
		try {
			List<TipoSoggetto> retList = tipoSoggettoMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}

	}

	
	@Autowired
	SoggettoContattoTipoMapper soggettoContattoTipoMapper;
	
	
	@Override  
	public Response getTipiContattoSoggetto(
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest req) {
		try {
			List<SoggettoContattoTipo> retList = soggettoContattoTipoMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}
	}

	@Autowired 
	TamponeMotivoMapper tamponeMotivoMapper;
	
	@Override
	public Response getTamponeMotivi(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
			List<TamponeMotivo> retList = tamponeMotivoMapper.selectAll();
			return Response.ok(retList).build();
		} catch (Exception e) {
			System.err.println(e);
			return Response.serverError().build();
		}
	}

	/**
	 * Restituisce l'elenco delle nazioni mondiali (al momento hardcoded)
	 */
	@Override
	public Response getElencoNazioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		String[][] elenco = { { "AF", "Afghanistan" }, { "AL", "Albania" }, { "DZ", "Algeria" }, { "AD", "Andorra" },
				{ "AO", "Angola" }, { "AI", "Anguilla" }, { "AQ", "Antartide" }, { "AG", "Antigua e Barbuda" },
				{ "SA", "Arabia Saudita" }, { "AR", "Argentina" }, { "AM", "Armenia" }, { "AW", "Aruba" },
				{ "AU", "Australia" }, { "AT", "Austria" }, { "AZ", "Azerbaigian" }, { "BS", "Bahamas" },
				{ "BH", "Bahrein" }, { "BD", "Bangladesh" }, { "BB", "Barbados" }, { "BE", "Belgio" },
				{ "BZ", "Belize" }, { "BJ", "Benin" }, { "BM", "Bermuda" }, { "BT", "Bhutan" }, { "BY", "Bielorussia" },
				{ "MM", "Birmania" }, { "BO", "Bolivia" }, { "BA", "Bosnia ed Erzegovina" }, { "BW", "Botswana" },
				{ "BR", "Brasile" }, { "BN", "Brunei" }, { "BG", "Bulgaria" }, { "BF", "Burkina Faso" },
				{ "BI", "Burundi" }, { "KH", "Cambogia" }, { "CM", "Camerun" }, { "CA", "Canada" },
				{ "CV", "Capo Verde" }, { "TD", "Ciad" }, { "CL", "Cile" }, { "CN", "Cina" }, { "CY", "Cipro" },
				{ "VA", "Città del Vaticano" }, { "CO", "Colombia" }, { "KM", "Comore" }, { "KP", "Corea del Nord" },
				{ "KR", "Corea del Sud" }, { "CI", "Costa d'Avorio" }, { "CR", "Costa Rica" }, { "HR", "Croazia" },
				{ "CU", "Cuba" }, { "CW", "Curaçao" }, { "DK", "Danimarca" }, { "DM", "Dominica" }, { "EC", "Ecuador" },
				{ "EG", "Egitto" }, { "SV", "El Salvador" }, { "AE", "Emirati Arabi Uniti" }, { "ER", "Eritrea" },
				{ "EE", "Estonia" }, { "ET", "Etiopia" }, { "FJ", "Figi" }, { "PH", "Filippine" },
				{ "FI", "Finlandia" }, { "FR", "Francia" }, { "GA", "Gabon" }, { "GM", "Gambia" }, { "GE", "Georgia" },
				{ "GS", "Georgia del Sud e Isole Sandwich Australi" }, { "DE", "Germania" }, { "GH", "Ghana" },
				{ "JM", "Giamaica" }, { "JP", "Giappone" }, { "GI", "Gibilterra" }, { "DJ", "Gibuti" },
				{ "JO", "Giordania" }, { "GR", "Grecia" }, { "GD", "Grenada" }, { "GL", "Groenlandia" },
				{ "GP", "Guadalupa" }, { "GU", "Guam" }, { "GT", "Guatemala" }, { "GG", "Guernsey" },
				{ "GN", "Guinea" }, { "GW", "Guinea-Bissau" }, { "GQ", "Guinea Equatoriale" }, { "GY", "Guyana" },
				{ "GF", "Guyana francese" }, { "HT", "Haiti" }, { "HN", "Honduras" }, { "HK", "Hong Kong" },
				{ "IN", "India" }, { "ID", "Indonesia" }, { "IR", "Iran" }, { "IQ", "Iraq" }, { "IE", "Irlanda" },
				{ "IS", "Islanda" }, { "BV", "Isola Bouvet" }, { "IM", "Isola di Man" }, { "CX", "Isola di Natale" },
				{ "NF", "Isola Norfolk" }, { "AX", "Isole Åland" }, { "BQ", "Isole BES" }, { "KY", "Isole Cayman" },
				{ "CC", "Isole Cocos (Keeling)" }, { "CK", "Isole Cook" }, { "FO", "Fær Øer" },
				{ "FK", "Isole Falkland" }, { "HM", "Isole Heard e McDonald" },
				{ "MP", "Isole Marianne Settentrionali" }, { "MH", "Isole Marshall" },
				{ "UM", "Isole minori esterne degli Stati Uniti" }, { "PN", "Isole Pitcairn" },
				{ "SB", "Isole Salomone" }, { "VG", "Isole Vergini britanniche" }, { "VI", "Isole Vergini americane" },
				{ "IL", "Israele" }, { "IT", "Italia" }, { "JE", "Jersey" }, { "KZ", "Kazakistan" }, { "KE", "Kenya" },
				{ "KG", "Kirghizistan" }, { "KI", "Kiribati" }, { "KW", "Kuwait" }, { "LA", "Laos" },
				{ "LS", "Lesotho" }, { "LV", "Lettonia" }, { "LB", "Libano" }, { "LR", "Liberia" }, { "LY", "Libia" },
				{ "LI", "Liechtenstein" }, { "LT", "Lituania" }, { "LU", "Lussemburgo" }, { "MO", "Macao" },
				{ "MK", "Macedonia del Nord" }, { "MG", "Madagascar" }, { "MW", "Malawi" }, { "MY", "Malaysia" },
				{ "MV", "Maldive" }, { "ML", "Mali" }, { "MT", "Malta" }, { "MA", "Marocco" }, { "MQ", "Martinica" },
				{ "MR", "Mauritania" }, { "MU", "Mauritius" }, { "YT", "Mayotte" }, { "MX", "Messico" },
				{ "FM", "Micronesia" }, { "MD", "Moldavia" }, { "MN", "Mongolia" }, { "ME", "Montenegro" },
				{ "MS", "Montserrat" }, { "MZ", "Mozambico" }, { "NA", "Namibia" }, { "NR", "Nauru" },
				{ "NP", "Nepal" }, { "NI", "Nicaragua" }, { "NE", "Niger" }, { "NG", "Nigeria" }, { "NU", "Niue" },
				{ "NO", "Norvegia" }, { "NC", "Nuova Caledonia" }, { "NZ", "Nuova Zelanda" }, { "OM", "Oman" },
				{ "NL", "Paesi Bassi" }, { "PK", "Pakistan" }, { "PW", "Palau" }, { "PS", "Palestina" },
				{ "PA", "Panama" }, { "PG", "Papua Nuova Guinea" }, { "PY", "Paraguay" }, { "PE", "Perù" },
				{ "PF", "Polinesia francese" }, { "PL", "Polonia" }, { "PR", "Porto Rico" }, { "PT", "Portogallo" },
				{ "MC", "Monaco" }, { "QA", "Qatar" }, { "GB", "Regno Unito" }, { "CD", "RD del Congo" },
				{ "CZ", "Rep. Ceca" }, { "CF", "Rep. Centrafricana" }, { "CG", "Rep. del Congo" },
				{ "DO", "Rep. Dominicana" }, { "RE", "Riunione" }, { "RO", "Romania" }, { "RW", "Ruanda" },
				{ "RU", "Russia" }, { "EH", "Sahara Occidentale" }, { "KN", "Saint Kitts e Nevis" },
				{ "LC", "Saint Lucia" }, { "SH", "Sant'Elena, Ascensione e Tristan da Cunha" },
				{ "VC", "Saint Vincent e Grenadine" }, { "BL", "Saint-Barthélemy" }, { "MF", "Saint-Martin" },
				{ "PM", "Saint-Pierre e Miquelon" }, { "WS", "Samoa" }, { "AS", "Samoa Americane" },
				{ "SM", "San Marino" }, { "ST", "São Tomé e Príncipe" }, { "SN", "Senegal" }, { "RS", "Serbia" },
				{ "SC", "Seychelles" }, { "SL", "Sierra Leone" }, { "SG", "Singapore" }, { "SX", "Sint Maarten" },
				{ "SY", "Siria" }, { "SK", "Slovacchia" }, { "SI", "Slovenia" }, { "SO", "Somalia" },
				{ "ES", "Spagna" }, { "LK", "Sri Lanka" }, { "US", "Stati Uniti" }, { "ZA", "Sudafrica" },
				{ "SD", "Sudan" }, { "SS", "Sudan del Sud" }, { "SR", "Suriname" }, { "SJ", "Svalbard e Jan Mayen" },
				{ "SE", "Svezia" }, { "CH", "Svizzera" }, { "SZ", "Swaziland" }, { "TW", "Taiwan" },
				{ "TJ", "Tagikistan" }, { "TZ", "Tanzania" }, { "TF", "Terre australi e antartiche francesi" },
				{ "IO", "Territorio britannico dell'Oceano Indiano" }, { "TH", "Thailandia" }, { "TL", "Timor Est" },
				{ "TG", "Togo" }, { "TK", "Tokelau" }, { "TO", "Tonga" }, { "TT", "Trinidad e Tobago" },
				{ "TN", "Tunisia" }, { "TR", "Turchia" }, { "TM", "Turkmenistan" }, { "TC", "Turks e Caicos" },
				{ "TV", "Tuvalu" }, { "UA", "Ucraina" }, { "UG", "Uganda" }, { "HU", "Ungheria" }, { "UY", "Uruguay" },
				{ "UZ", "Uzbekistan" }, { "VU", "Vanuatu" }, { "VE", "Venezuela" }, { "VN", "Vietnam" },
				{ "WF", "Wallis e Futuna" }, { "YE", "Yemen" }, { "ZM", "Zambia" }, { "ZW", "Zimbabwe" } };
		
		List<Nazione> returnList = new ArrayList<>();
		
		for(String[] x : elenco) {
			Nazione n = new Nazione();
			n.setAttiva(true);
			n.setCodice(x[0]);
			n.setDescrizione(x[1]);
			returnList.add(n);
		}
		
		return Response.ok(returnList).build();
	}

	/**
	 * Restituisce l'elenco delle regioni italiane (al momento hardcoded)
	 */
	@Override
	public Response getElencoRegioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		
		String[] elenco = { "Abruzzo", "Basilicata", "Calabria", "Campania", "Emilia-Romagna", "Friuli-Venezia Giulia",
				"Lazio", "Liguria", "Lombardia", "Marche", "Molise", "Piemonte", "Puglia", "Sardegna", "Sicilia",
				"Toscana", "Trentino-Alto Adige", "Umbria", "Valle d'Aosta", "Veneto" };
		
		List<Regione> returnList = new ArrayList<>();
		
		for(String x : elenco) {
			Regione r = new Regione();
			r.setAttiva(true);
			r.setCodice(x);
			r.setDescrizione(x);
			returnList.add(r);
		}
		
		return Response.ok(returnList).build();
	}
	
	


}
