package it.csi.gestionepazienti.gestionepazientiweb.filter.gestionepazientiapi;

import it.csi.gestionepazienti.gestionepazientiweb.filter.UserIdAdapterFilter;

/**
 * Inserisce in sessione:
 * <ul> 
 *  <li>l'identit&agrave; digitale relativa all'utente autenticato.
 *  <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter extends UserIdAdapterFilter  {
	
	@Override
	protected String getDefaultRunningApplication() {
		return "gestione pazienti";
	}
	
	@Override
	protected String getContextRoot() {
		return "/gestionepazientiapiwebsrv";
	}
}
