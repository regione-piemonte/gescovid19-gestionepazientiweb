appServices.factory('gestionepazientiService', [ "$http", "$q", function($http, $q) {

	var gestionepazientiService = {};

	gestionepazientiService.getCurrentUser = function() {
		return $http({method : 'GET',url : Constants.GESTIONEPAZIENTI_BASE_URL +"/currentUser"});
	};

	gestionepazientiService.logout = function() {
		return $http({method : 'GET',url : Constants.GESTIONEPAZIENTI_BASE_URL +"/localLogout"});
	};
	

	gestionepazientiService.loadPazienti = function() {
		var getPazienteUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/soggetto";
		
		console.log("request URL", getPazienteUrl);
		return $http({
			method : 'GET',
			url : getPazienteUrl
		});
	};

	gestionepazientiService.searchPazienti = function(input) {
		var searchPazienteUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/soggetto/extra?";
		if(input.codiceFiscale)
			searchPazienteUrl += 'codiceFiscale='+input.codiceFiscale+'&';
		if(input.cognome)
			searchPazienteUrl += 'cognome='+input.cognome+'&';
		if(input.nome)
			searchPazienteUrl += 'nome='+input.nome+'&';
		if(input.cognomeexact)
			searchPazienteUrl += 'cognomeexact='+input.cognomeexact+'&';
		
		console.log("request URL", searchPazienteUrl);
		return $http({
			method : 'GET',
			url : searchPazienteUrl
		});
	};

	
	gestionepazientiService.loadPaziente = function(idPaziente) {
		var getPazienteUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/soggetto/"+idPaziente;
		
		console.log("request URL", getPazienteUrl);
		return $http({
			method : 'GET',
			url : getPazienteUrl
		});
	};
	
	gestionepazientiService.savePaziente = function(paziente) {
		console.log("pazienteToSave",paziente );
		
		var savePazienteUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/soggetto";
		var httpMethod = 'POST';
		if(paziente.idSoggetto){
			//savePazienteUrl += "/"+paziente.idSoggetto;
			httpMethod = 'PUT';
		}
		
		return $http({
			method : httpMethod,
			url : savePazienteUrl ,
			data: paziente
		});
	};
	
	
	gestionepazientiService.saveDecorso = function(decorso) {
		console.log("decorsoToSave",decorso);
		
		var saveDecorsoUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/decorso";
		var httpMethod = 'POST';
		if(decorso.idDecorso){
			saveDecorsoUrl += "/"+decorso.idDecorso;
			httpMethod = 'PUT';
		}
		
		return $http({
			method : httpMethod,
			url : saveDecorsoUrl ,
			data: decorso
		});
	};
	
	gestionepazientiService.deleteDecorso = function (idDecorso) {
		var deleteDecorso = Constants.GESTIONEPAZIENTI_BASE_URL + '/decorso/' + idDecorso;
		var httpMethod = 'DELETE';

		return $http({
			method: httpMethod,
			url: deleteDecorso
		});
	}
	gestionepazientiService.loadComuni = function() {
		
		return $http({
			method : 'GET',
			cache: true,
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/comuni" 
		});
	};
	
	gestionepazientiService.loadReparti = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/repartoricovero" 
		});
	};
	
	gestionepazientiService.loadAsl = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/asl" 
		});
	};
	
	gestionepazientiService.loadTipisoggetto = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/tiposoggetto" 
		});
	};
	
	
	
	gestionepazientiService.loadEsitidimissioni = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/esitodimissioni" 
		});
	};
	
	gestionepazientiService.loadLaboratori = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/laboratorio" 
		});
	};
	
	gestionepazientiService.loadTipologiaTest = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/testcovid" 
		});
	};
	
	gestionepazientiService.saveTampone = function(tampone) {
		console.log("tamponeToSave",tampone );
		
		var saveTamponeUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/tampone";		
		var httpMethod = 'POST';
		if(tampone.idTampone){
			//saveTamponeUrl += "/"+tampone.idTampone;
			httpMethod = 'PUT';
		}
		
		return $http({
			method : httpMethod,
			url : saveTamponeUrl ,
			data: tampone
		});
	};

	gestionepazientiService.deleteTampone = function (idTampone) {
		var deleteTampone = Constants.GESTIONEPAZIENTI_BASE_URL+'/tampone/'+idTampone;
		var httpMethod = 'DELETE';

		return $http({
			method: httpMethod,
			url: deleteTampone
		});
	}

	
	
	gestionepazientiService.logout = function() {
		return $http({method : 'GET',url : Constants.GESTIONEPAZIENTI_BASE_URL +"/localLogout"});
	};
	
	
	
	gestionepazientiService.auraSearchByCf = function(codiceFiscale) {
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/aura/" +  codiceFiscale
		});
	};
	
	gestionepazientiService.auraSearchByCognome = function(nome, cognome, dataNascita) {
		var mm = dataNascita.getMonth() + 1; 
		var dd = dataNascita.getDate();
		var yyyy = dataNascita.getFullYear();

		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/aura/"+nome+"/"+cognome+"/"+(dd>9 ? '' : '0') + dd + "/"+(mm>9 ? "" : "0") + mm + "/"+yyyy
		});
	};
	
	gestionepazientiService.auraSearchByIdSoggetto = function(idSoggetto) {
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/aura/find/" +  idSoggetto
		});
	};
	
	
	gestionepazientiService.loadRichiestetampone = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/tampone" 
		});
	};
	
	gestionepazientiService.loadEnti = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/postiletto/ente" 
		});
	};
	
	gestionepazientiService.loadStrutture = function(idEnte) {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/postiletto/ente/" + idEnte +"/struttura"
		});
	};
	
	gestionepazientiService.loadStruttureNonAcute = function(idEnte) {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/postiletto/struttura/nonacuta"
		});
	};
	
	gestionepazientiService.loadAree = function(idStruttura) {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/postiletto/struttura/" + idStruttura +"/area"
		});
	};
	
	gestionepazientiService.loadEventi = function() {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/decodifiche/tipoevento"
		});
	};
	
	gestionepazientiService.loadDisponibilita = function(transposed) {
		
		return $http({
			method : 'GET',
			url : Constants.GESTIONEPAZIENTI_BASE_URL +"/postiletto/disponibilita" + (transposed?"/transposed":"")
		});
	};
	
	gestionepazientiService.loadPazientiList = function(tipolistaDecorsi) {
		var getPazienteUrl = Constants.GESTIONEPAZIENTI_BASE_URL +"/soggetto/"+tipolistaDecorsi;
		
		console.log("request URL", getPazienteUrl);
		return $http({
			method : 'GET',
			url : getPazienteUrl
		});
	};
	


	gestionepazientiService.loadDiari = function(idPaziente) {
		var getDiariUrl = Constants.GESTIONEPAZIENTI_EXTERNAL_URL +"api/uscammg/diari?soggetto=" + idPaziente;
		
		console.log("request URL", getDiariUrl);
		return $http({
			method : 'GET',
			url : getDiariUrl
		});
	};
	
	gestionepazientiService.loadSintomiDiario = function() {
		var getSintomiDiariUrl = Constants.GESTIONEPAZIENTI_EXTERNAL_URL +"api/uscammg/sintomi";
		
		console.log("request URL", getSintomiDiariUrl);
		return $http({
			method : 'GET',
			url : getSintomiDiariUrl
		});
	};
	
	

	gestionepazientiService.saveDettaglioDiario = function(dettaglio) {
		console.log("saveDettaglioDiario", dettaglio);
		
		var saveDettaglioDiarioUrl = Constants.GESTIONEPAZIENTI_EXTERNAL_URL +"api/uscammg/diario-dettagli";
		var httpMethod = 'POST';
		
		return $http({
			method : httpMethod,
			url : saveDettaglioDiarioUrl ,
			data: dettaglio
		});
	};
	
	gestionepazientiService.loadInterventiUsca = function(idPaziente) {
		var getInterventiUscaUrl = Constants.GESTIONEPAZIENTI_EXTERNAL_URL +"api/uscammg/schede?soggetto=" + idPaziente;
		
		console.log("request URL", getInterventiUscaUrl);
		return $http({
			method : 'GET',
			url : getInterventiUscaUrl
		});
	};


	gestionepazientiService.loadNotifiche = function (currentUser, tipoUtenteDest, tipoUtenteMitt) {
		console.log("loadNotifiche - currentUser", currentUser, tipoUtenteDest, tipoUtenteMitt);

		var idEnte = currentUser && currentUser.elencoEnte && currentUser.elencoEnte.length && 
			currentUser.elencoEnte[0].idEnte;
		console.log("loadNotifiche - idEnte", idEnte);

		return $http({
		method: "GET",
		url:
			Constants.GESTIONEPAZIENTI_EXTERNAL_URL +
			`api/uscammg/notifiche?tipoUtenteDest=${tipoUtenteDest}&idEnte=${idEnte}`, //&tipoUtenteMitt=${tipoUtenteMitt} TOLTO!
		});
  };

	gestionepazientiService.putNotifica = function (idNotifica, testoRisposta) {
		console.log("putNotifica - currentUser", idNotifica, testoRisposta);

			return $http({
				method: "PUT",
				url:
					Constants.GESTIONEPAZIENTI_EXTERNAL_URL +
					`api/uscammg/notifiche/${idNotifica}/azione`,
				data: {
					"testo": testoRisposta
				}
			});
		
	
	};

	gestionepazientiService.postContatto = function (contatto) {
		console.log("postContatto ", contatto );

		return $http({
			method: "POST",
			url:
				Constants.GESTIONEPAZIENTI_BASE_URL +
				`/soggetto/contatti`,
			data:  contatto			
		});
	};



	gestionepazientiService.getTipiContatto = function () {
		console.log("getTipiContatto")
		return $http({
			method: "GET",
			url:
				Constants.GESTIONEPAZIENTI_BASE_URL +
				`/decodifiche/soggetto-contatto-tipi`,
		});
	};



	return gestionepazientiService;
	
}]);
