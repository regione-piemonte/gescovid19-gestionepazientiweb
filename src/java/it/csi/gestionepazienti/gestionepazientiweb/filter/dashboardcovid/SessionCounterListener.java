package it.csi.gestionepazienti.gestionepazientiweb.filter.dashboardcovid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import it.csi.gestionepazienti.gestionepazientiweb.business.SpringApplicationContextHelper;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.Audit;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.filter.gestionepazienti.IrideIdAdapterFilter;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.AuditMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.extend.UtentiMapper;

public class SessionCounterListener implements HttpSessionListener {
 
	public Utenti getCurrentUser(HttpSession sess) {
		return (Utenti) sess.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);

	}

	
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	Utenti ut = getCurrentUser(sessionEvent.getSession());
    	if (ut!=null) {
			AuditMapper auditMapper = (AuditMapper) SpringApplicationContextHelper.getBean("AuditMapper");
			Audit audit = new Audit();
			audit.setIp("0.0.0.0");
			audit.setAction("logout");
			audit.setDescription("Logout su dashboard");
			audit.setTable("utenti");
			audit.setUser(ut.getCfUtente());
			auditMapper.insert(audit);
    	}
    }	
  
    

}