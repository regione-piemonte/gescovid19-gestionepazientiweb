package it.csi.gestionepazienti.gestionepazientiapi.batch.aslinadempienti;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.csi.gestionepazienti.gestionepazientiapi.batch.BaseBatch;

@Component
public class AslInadempientiBatch extends BaseBatch  {

	private static final Logger log = Logger.getLogger(AslInadempientiBatch.class);
	
	@Override
	public void doBatchBase() {
		log.info("AslInadempientiBatch - da implementare");
	}

}
