package it.csi.gestionepazienti.gestionepazientiweb.business.be;

public class DecodeAutorizzazioneTampone {

	
	public static String decodeAutorizzazioneTampone(String code)
	{
		if (code==null)
			return null;
		
		String decode = code;
		switch (code) {
		case "P":
			decode = "Proposto";
			break;
		case "NI":
			decode = "NO Fornire maggiori informazioni Contattare UC: 0114326756";
			break;		
		default:
			break;
		}
		return decode;
		
	}
}
