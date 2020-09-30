var Constants = Constants || {};

Constants.GESTIONEPAZIENTI_BASE_URL = "/gestionepazientiweb/api";
Constants.GESTIONEPAZIENTI_EXTERNAL_URL = "/gestionepazientiweb/external/";
if (location.hostname === "localhost" || location.hostname === "127.0.0.1"){
	//Constants.GESTIONEPAZIENTI_BASE_URL = "https://int-rupcovid.sdp.csi.it/gestionepazientiweb/api";
	Constants.GESTIONEPAZIENTI_BASE_URL = "http://localhost:8080/gestionepazientiweb/api";
	Constants.GESTIONEPAZIENTI_EXTERNAL_URL = "http://localhost:8080/gestionepazientiweb/external/";
		
}


Constants.VERY_FUTURE_DATE_MILLIS = 4102444800; // 01/01/2100

Constants.ID_ISOLAMENTO_DOMICILIARE = 1; 
Constants.ID_RICOVERATO = 2; 
Constants.ID_TRASFERITO_INTERNO = 3;
Constants.ID_TRASFERITO_ESTERNO = 4;
Constants.ID_DECEDUTO = 5;
Constants.ID_GUARITO = 6;
Constants.ID_ATTESA_TEST = 8;
Constants.ID_POST_RICOVERO = 9;
Constants.ID_ASSEGNAZIONE_DOMICILIO = 10;
Constants.ID_SEGNALAZIONE = 11;
Constants.ID_PRESO_IN_CARICO = 12;
Constants.ID_USCITO = 13;
Constants.ID_INFO_DA_INTEGRARE = 14;
Constants.ID_ISOLAMENTO_FIDUCIARIO = 15;
Constants.ID_DISPOSTA_QUARANTENA_DOMIC = 16;
Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC = 17;

Constants.ID_ALTRO_LABORATORIO = 6;
	
Constants.ID_LABORATORIO_LAB_SYNLAB = 100;
Constants.ASR_AL=6;
 


Constants.TIPI_DECORSO = { 'idTipoEvento_1':'ISOLAMENTO_DOMICILIARE',
		'idTipoEvento_2':'STRUTTURA',
		'idTipoEvento_3':'STRUTTURA',
		'idTipoEvento_4':'STRUTTURA',
		'idTipoEvento_5':'DECEDUTO',
		'idTipoEvento_6':'GUARITO',
		'idTipoEvento_8':'STRUTTURA',
		'idTipoEvento_9':'POST_RICOVERO',
		'idTipoEvento_10':'ASSEGNAZIONE_DOMICILIO',
		'idTipoEvento_11':'SEGNALAZIONE',
		'idTipoEvento_15':'ISOLAMENTO_FIDUCIARIO',
		'idTipoEvento_16':'DISPOSTA_QUARANTENA_DOMIC',
		'idTipoEvento_17':'DISPOSTA_QUARANTENA_EXTRA_DOMIC',
};

Constants.STATI_SCHEDE_USCA_BOZZA = 'B';
Constants.STATI_SCHEDE_USCA_INVIATO = 'I';
Constants.STATI_SCHEDE_USCA_PRESO_IN_CARICO = 'P';
Constants.STATI_SCHEDE_USCA_ESEGUITO = 'E';
Constants.STATI_SCHEDE_USCA_RESPINTO = 'R';

