package it.csi.gestionepazienti.gestionepazientiweb.business.service;

import javax.servlet.http.HttpServletRequest;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;

public interface BackendService {

	public String getMessage();

	public UserLogged getCurrentUser(HttpServletRequest req);

	public String localLogout(HttpServletRequest req);
}
