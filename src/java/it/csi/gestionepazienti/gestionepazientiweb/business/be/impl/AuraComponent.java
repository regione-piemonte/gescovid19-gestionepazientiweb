package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.csi.gestionepazienti.gestionepazientiweb.business.aura.find.DatiAnagrafici;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.find.FindProfiliAnagraficiResult;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.GetProfiloSanitarioResponse;
import it.csi.gestionepazienti.gestionepazientiweb.business.aura.get.SoggettoAuraBody;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.AuditableApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.delegate.AuraWsDelegate;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Asl;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Comuni;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoFromElencoAura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AslMapper;

@Component
public class AuraComponent extends AuditableApiServiceImpl {
	private static final Logger log = Logger.getLogger(AuraComponent.class);

	@Value("${auraprefix.url}")
	private String auraprefixUrl;
	@Value("${aura.user}")
	private String auraUser;
	@Value("${aura.pwd}")
	private String auraPwd;
	@Value("${aura.timeoutmills}")
	private int auraTimeout;
	
	@Autowired
	AslMapper aslMapper;
	
	
	public Response findAnagraficaById(String id,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req)
	{
		SoggettoForElenco soggetto;
		try {
			soggetto = extractProfiloSanitario(id, true);
			if (soggetto==null)
				Response.ok("{}").build();
			return Response.ok().entity(soggetto).build();
		} catch (Exception e) {
			return Response.serverError().entity(new Message(e.getMessage())).build();
		}

	}
	
	public Response getSoggettiAuraByNomeCognomeData(String nome,String cognome,
			String giorno, String mese,String anno,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest req)
	{
		
		try {
			String dataNascita = giorno+"/"+mese+"/"+anno;
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			if (nome.isEmpty() || cognome.isEmpty() || dataNascita.isEmpty())
				return Response.status(Status.BAD_REQUEST).entity(new it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message("Nome, cognome o data di nascita non valorizzati")).build();
						
			List<SoggettoFromElencoAura> dati =  findProfiliAnagrafici(nome, cognome, dataNascita);
			
			insertAudit("aura", "getSoggettiAuraByNomeCognomeData", "("+nome+","+cognome+","+dataNascita+")", currentUser.getCfUtente(), null, req);
			
			return Response.ok(dati).build();
		} catch (Exception e) {
			log.error("Eccezione durante la chiamata a Aura", e);
			return Response.serverError().entity(new Message(e.getMessage())).build();
		}

	}
	
	private  List<SoggettoFromElencoAura> findProfiliAnagrafici(String nome,String cognome,
			String dataNascita) throws Exception{
		FindProfiliAnagraficiResult fp =  AuraWsDelegate.findProfiliAnagrafici(null, nome, cognome, dataNascita, auraUser, auraPwd, auraprefixUrl, auraTimeout);
		return removedCFDuplicatedInactive(extractFromFindProfili(fp));
	}
	
	
	
	public Response getSoggettiAuraByCF(String codiceFiscale,SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {
		try {
			
			UserLogged currentUser = getBeService().getCurrentUser(req);
			
			List<SoggettoFromElencoAura> dati =  findProfiliAnagrafici(codiceFiscale);
			
			insertAudit("aura", "getSoggettiAuraByCF", "("+codiceFiscale+")", currentUser.getCfUtente(), null, req);
			
			return Response.ok(dati).build();
		} catch (Exception e) {
			log.error("Eccezione durante la chiamata a Aura", e);
			return Response.serverError().entity(new Message(e.getMessage())).build();
		}

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
			
			log.info("[AuraComponent:: extractMedicoFromAura] medico.id: " + medico.getIdMedico());
			log.info("[AuraComponent:: extractMedicoFromAura] medico.cf: " + medico.getCfMedico()!=null ? medico.getCfMedico(): "cf medico aura NULLO.");
			
		}
		else {
			log.info("Soggetto con dati sanitari nulli");
		}
		
		
		
		return medico;
	}
	
	public  List<SoggettoFromElencoAura> findProfiliAnagrafici(String codiceFiscale) throws Exception{

	    FindProfiliAnagraficiResult fp=	AuraWsDelegate.findProfiliAnagrafici(codiceFiscale, null, null, null, auraUser, auraPwd, auraprefixUrl, auraTimeout);
		return removedCFDuplicatedInactive(extractFromFindProfili(fp));
	}
	
	
	private List<SoggettoFromElencoAura> extractFromFindProfili(FindProfiliAnagraficiResult fp) {
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

		
		return soggetti;
	}
	
	private List<SoggettoFromElencoAura> removedCFDuplicatedInactive(List<SoggettoFromElencoAura> list) throws Exception {
		if (list==null)
			return list;
		log.info("Trovati num soggetti "+list.size());
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
		log.info("Fine elaborazione removedCFDuplicatedInactive");
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
			log.info("Risposta dal servizio AURA non correttamente compilata ");
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
		
		if (aslDecodifica && body.getInfoSan()!=null){
			String aslDomicilioStr = findAslDescrizioneByCodice(body.getInfoSan().getAslDomicilio());
			if (aslDomicilioStr!=null && !aslDomicilioStr.isEmpty())
				soggetto.setAslDomicilio(aslDomicilioStr);
			String aslResidenzaStr = findAslDescrizioneByCodice(body.getInfoSan().getAslResidenza());
			if (aslResidenzaStr!=null && !aslResidenzaStr.isEmpty())
				soggetto.setAslResidenza(aslResidenzaStr);
		}
		else {
			log.info("Soggetto con dati sanitari nulli");
		}
		
		
		
		return soggetto;
	}
	
	private String findAslDescrizioneByCodice(String aslCodice) {
		if (aslCodice!=null && !aslCodice.isEmpty())
		{
			if (aslCodice.length()!=6)
			{
				log.warn("Codice ASL su Aura non di 6 caratteri "+ aslCodice);
			}
			else {
				Asl asl = aslMapper.selectByPrimaryKey(aslCodice.substring(0, 3),aslCodice.substring(3, 6));
				if (asl!=null)
				{
					return asl.getDescAslEstesa();
				}
				else
					log.info("Asl non trovata");
			}
		}
		return null;
	}

	
	
}
