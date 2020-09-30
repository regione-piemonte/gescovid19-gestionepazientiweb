package it.csi.gestionepazienti.gestionepazientiapi.batch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public abstract class BaseBatch implements Batch {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Esecuzione della logica del batch
	 */
	protected abstract void doBatchBase();
	
	
	
	@Override
	public final void doBatch() {
		String hostName = getHostName();
		logger.info("BaseBatch: hostName: "+hostName);
		if(isServer1(hostName)) {
		//if(true) {
			logger.info("BaseBatch: batch enabled on this host: "+hostName);
			doBatchBase();
		} else {
			logger.info("BaseBatch: batch disabled on this host: "+hostName);
		}

	}

	private static boolean isServer1(String hostname) {
		return hostname.matches("xishan|yamunanagar|localhost");
	}
	
	
	private static String getHostName() {
		String hostname = "Unknown";

		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException ex) {
			Logger log = Logger.getLogger(Batch.class);
			log.error("BaseBatch: hostName can not be resolved");
		}
		return hostname;
	}
	


}
