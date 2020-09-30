package it.csi.gestionepazienti.gestionepazientiapi.batch.referti;

public enum RefertiEnum {
//	SISP(4),
//	MMG(6),
//	EXT(7),
	
	SISP(3),
	MMG(5),
	EXT(6), // screen
	
	PRIORITA_MEDIA(2),
	PRIORITA_ALTA(1),
	
	INFORMATIVA (1),
	//COMUNICAZIONE (3),
	
	ISOLAMENTO_FID (15),
	
	//ISOLAMENTO (1),
	
	ASR_NON_PRES(21), 
	
	SCREENING(5);
	
	private final Integer value;
	
	RefertiEnum(final Integer newValue) {
        value = newValue;
    }
	
	public Integer getValue() { return value; }
}
