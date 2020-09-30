package it.csi.gestionepazienti.gestionepazientiweb.util;

import javax.servlet.http.HttpServletRequest;

import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.dto.profilo.Profilo;

public class GenericUtils {
	
	public static int ID_PROFILO_UNITA_CRISI = 1;
	public static int ID_PROFILO_GESTORE_PAZIENTI = 4;
	public static int ID_PROFILO_SUPERUSER_PAZIENTI = 6;
	public static int ID_PROFILO_DIRETTORE_ASL= 5;
	public static int ID_PROFILO_SINDACO = 8;
	public static int ID_PROFILO_MEDICO_MMG = 15;
	
	public static int ID_PROFILO_MEDICO_USCA = 18;
	public static int ID_PROFILO_MEDICO_EMERGENZA = 19;
	public static int ID_PROFILO_MEDICO_CONT_ASS = 20;
	
	public static int ID_PROFILO_LAB_IMPORT = 27;
	
	public static String getIpAddress(HttpServletRequest req) {
		String ip = "0.0.0.0";
		try {
			// Raffa: 13-08-2020 si e' deciso che si salva tutta la stringa che arriva dal parametro
		    //ip = req.getHeader("X-Forwarded-For").split(",")[0];
			if(req.getHeader("X-Forwarded-For")!=null) {
				ip = req.getHeader("X-Forwarded-For");
			}else ip = req.getRemoteAddr() !=null ? req.getRemoteAddr(): "IP NULLO";
		} catch (Exception ignored){
			ip = req.getRemoteAddr();
		}
		return ip;
	}
	
	public static boolean currentUserHasProfilo(UserLogged user, int idProfilo)
	{
		if (user.getElencoProfilo()!=null)
		{
			for (Profilo profilo : user.getElencoProfilo()) {
				if (profilo.getIdProfilo().equals(idProfilo)) 
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
