package it.csi.gestionepazienti.gestionepazientiweb.business.be.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.gestionepazienti.gestionepazientiweb.business.be.AbstractSoggettoApiServiceImpl;
import it.csi.gestionepazienti.gestionepazientiweb.business.be.MmgVisuraDeleganteApi;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Payload;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Payload2;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RMedicoMedico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.MedicoExt;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.util.Message;;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MmgVisuraDeleganteApiServiceImpl extends AbstractSoggettoApiServiceImpl implements MmgVisuraDeleganteApi {

	@Override
	public Response searchMediciDeleganti(String codiceFiscale, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {

		
		try {
			String returnMessage = null;

			log.info("searchMediciDeleganti - Ricerca deleganti by cf "+codiceFiscale);
			
			if (codiceFiscale==null || codiceFiscale.isEmpty())
				throw new WebApplicationException(Response.serverError().entity(new Message("codice fiscale obbligatorio")).build());
				
			List<MedicoExt> elencoMedici =medicoMapper.selectForDelegantiByCodiceFiscale(codiceFiscale.toUpperCase());
			
			if (elencoMedici==null || elencoMedici.isEmpty())
			{
				log.info("searchMediciDeleganti - Nessun delegante trovato in piattaforma per cf "+codiceFiscale);
				//return Response.status(Status.NOT_FOUND).build();
			}

			log.info("searchMediciDeleganti - Delegante trovato per cf " + codiceFiscale);

			return Response.ok(elencoMedici).build();

		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException) e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}

	}
	
	@Override
	public Response getMediciDelegati(String codiceFiscale, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		
		
		try {
			
			log.info("getMediciDelegati - Ricerca delegati by cf "+codiceFiscale);
			
			if (codiceFiscale==null || codiceFiscale.isEmpty())
				throw new WebApplicationException(Response.serverError().entity(new Message("codice fiscale obbligatorio")).build());
				
			List<MedicoExt> elencoMedici =medicoMapper.selectDelegatiByCFDelegante(codiceFiscale.toUpperCase());
			
			if (elencoMedici==null || elencoMedici.isEmpty())
			{
				log.info("getMediciDelegati - Nessun delegato trovato in piattaforma per il medico con cf "+codiceFiscale);
				//return Response.status(Status.NOT_FOUND).build();
			}
		
				
			return Response.ok(elencoMedici).build();
			
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
		
	}

	@Override
	public Response insertDelega(Payload2 paylod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {

		try {
			if (paylod == null || paylod.getIdMedicoDelegante() == null || paylod.getIdMedicoDelegante() == 0) {
				throw new WebApplicationException(
						Response.serverError().entity(new Message("id medico delegante obbligatorio")).build());
			}

			if (paylod == null || paylod.getIdMedicoDelegato() == null || paylod.getIdMedicoDelegato() == 0) {
				throw new WebApplicationException(
						Response.serverError().entity(new Message("id medico delegato obbligatorio")).build());
			}

			if (paylod == null || paylod.getDataInizioValidita() == null) {
				throw new WebApplicationException(
						Response.serverError().entity(new Message("data inizio validita obbligatoria")).build());
			}

			if (paylod == null || paylod.getDataFineValidita() == null) {
				throw new WebApplicationException(
						Response.serverError().entity(new Message("data fine validita obbligatoria")).build());
			}

			log.info("insertDelega - Inserisci deleghe per idMedicoDelegato " + paylod.getIdMedicoDelegato());

			UserLogged currentUser = getBeService().getCurrentUser(req);
			// String utenteOperazione = currentUser.getCfUtente();

			RMedicoMedico medico = new RMedicoMedico();
			medico.setIdMedicoDelegato(paylod.getIdMedicoDelegato().longValue());
			medico.setIdMedicoDelegante(paylod.getIdMedicoDelegante().longValue());
			medico.setDataCancellazione(null);
			medico.setValiditaInizio(paylod.getDataInizioValidita());
			medico.setValiditaFine(paylod.getDataFineValidita());
			medico.setUtenteOperazione(currentUser.getCfUtente());

//			medicoMapper.insertDelega(paylod.getIdMedicoDelegante(), paylod.getIdMedicoDelegato(),
//					paylod.getDataInizioValidita(), paylod.getDataFineValidita(), utenteOperazione);
			medicoMapper.insertDelega(medico);

			log.info("insertDelega - Delega inserita per idMedicoDelegato " + paylod.getIdMedicoDelegato());

			return Response.ok().build();

		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException) e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	@Override
	public Response updateDelega(Long idMedicoDelegato, Payload paylod, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest req) {
		try {

			if (idMedicoDelegato == null || idMedicoDelegato == 0) {
				throw new WebApplicationException(
						Response.serverError().entity(new Message("id medico delegato obbligatorio")).build());
			}

//			if (paylod == null || paylod.getDataInizioValidita() == null ) {
//				throw new WebApplicationException(Response.serverError().entity(new Message("data inizio validita obbligatoria")).build());
//			}

			if (paylod == null || paylod.getDataFineValidita() == null) {
				throw new WebApplicationException(
						Response.serverError().entity(new Message("data fine validita obbligatoria")).build());
			}

			UserLogged currentUser = getBeService().getCurrentUser(req);
			Long idMedicoDelegante = currentUser.getMedico().getIdMedico();

			log.info("updateDelega - Aggiorna deleghe per idMedicoDelegato " + idMedicoDelegato);

			medicoMapper.updateDelega(idMedicoDelegante, idMedicoDelegato, paylod.getDataFineValidita());

			log.info("updateDelega - Delega aggiornata per idMedicoDelegato " + idMedicoDelegato);

			return Response.ok().build();

		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException) e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	@Override
	public Response deleteDelega(Long idMedicoDelegato, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest req) {

		try {
			log.info("deleteDelega - Elimina deleghe by idMedicoDelegato " + idMedicoDelegato);

			UserLogged currentUser = getBeService().getCurrentUser(req);
			Long idMedicoDelegante = currentUser.getMedico().getIdMedico();

			if (idMedicoDelegato == null || idMedicoDelegato == 0)
				throw new WebApplicationException(
						Response.serverError().entity(new Message("id medico obbligatorio")).build());

			int ret = medicoMapper.deleteDelega(idMedicoDelegante, idMedicoDelegato);

			log.info("deleteDelega - Delega eliminata per idMedicoDelegato " + idMedicoDelegato);

			return Response.ok().build();

		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException) e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
	}

	@Override
	public Response getMediciDelegabili(String codiceFiscale, String cognome, String nome,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req) {
		try {
						
			log.info("getMediciDelegabili - Ricerca medici delegabili");
			if(!StringUtils.isEmpty(codiceFiscale))
				log.info("getMediciDelegabili - by cf: " + codiceFiscale);
			if(!StringUtils.isEmpty(nome))
				log.info("getMediciDelegabili - by nome: " + nome);
			if(!StringUtils.isEmpty(cognome))
				log.info("getMediciDelegabili - by cognome: " + cognome);
			
			
			List<Medico> elencoMedici =medicoMapper.selectMediciDelegaliByFiltro(codiceFiscale, cognome, nome );
			
			if (elencoMedici==null || elencoMedici.isEmpty())
			{
				log.info("getMediciDelegabili - Nessun medico trovato in piattaforma "  );
				return Response.status(Status.NOT_FOUND).build();
			}
		
			log.info("searchMediciDeleganti - Delegante trovato per cf "+codiceFiscale);
				
			return Response.ok(elencoMedici).build();
			
		} catch (Exception e) {
			if (e instanceof WebApplicationException)
				throw (WebApplicationException)e;
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.serverError().entity(new Message("Server Error")).build());
		}
		
	}
	

}
