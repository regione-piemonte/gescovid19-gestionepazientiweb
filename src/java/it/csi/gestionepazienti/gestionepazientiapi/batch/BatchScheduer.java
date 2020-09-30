package it.csi.gestionepazienti.gestionepazientiapi.batch;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.csi.gestionepazienti.gestionepazientiapi.batch.aslinadempienti.AslInadempientiBatch;
import it.csi.gestionepazienti.gestionepazientiapi.batch.referti.RefertiBatch;

/**
 * Esecutore dei Bach
 */
@Component
public class BatchScheduer {
	
	@Autowired
	RefertiBatch refertiBatch;
	
	@Autowired
	AslInadempientiBatch aslInadempientiBatch;
	
	
	@Scheduled (cron = "0 */5 * * * *") 
	public void scheduleReferti(){
		refertiBatch.doBatch();
	}	
	
	//@Scheduled (cron = "0 */5 * * * *") //TODO ogni martedi alle 12.01
	public void scheduleAslInadempienti(){
		aslInadempientiBatch.doBatch();
	}
	
}