appServices.factory('dashboardcovidService', [ "$http", "$q", function($http, $q) {

	var dashboardcovidService = {};

	dashboardcovidService.getCurrentUser = function() {
		return $http({method : 'GET',url : Constants.DASHBOARDCOVID_BASE_URL +"/currentUser"});
	};

	dashboardcovidService.logout = function() {
		return $http({method : 'GET',url : Constants.DASHBOARDCOVID_BASE_URL +"/localLogout"});
	};
	

	dashboardcovidService.loadPazienti = function() {
		var getPazienteUrl = Constants.DASHBOARDCOVID_BASE_URL +"/soggetto";
		
		console.log("request URL", getPazienteUrl);
		return $http({
			method : 'GET',
			url : getPazienteUrl
		});
	};
	
	dashboardcovidService.loadPaziente = function(idPaziente) {
		var getPazienteUrl = Constants.DASHBOARDCOVID_BASE_URL +"/soggetto/"+idPaziente;
		
		console.log("request URL", getPazienteUrl);
		return $http({
			method : 'GET',
			url : getPazienteUrl
		});
	};
	
	dashboardcovidService.savePaziente = function(paziente) {
		console.log("pazienteToSave",paziente );
		
		var savePazienteUrl = Constants.DASHBOARDCOVID_BASE_URL +"/soggetto";
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
	
	
	dashboardcovidService.saveDecorso = function(decorso) {
		console.log("decorsoToSave",decorso);
		
		var saveDecorsoUrl = Constants.DASHBOARDCOVID_BASE_URL +"/decorso";
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
	
	dashboardcovidService.loadComuni = function() {
		
		return $http({
			method : 'GET',
			cache: true,
			url : Constants.DASHBOARDCOVID_BASE_URL +"/decodifiche/comuni" 
		});
	};
	
	dashboardcovidService.loadReparti = function() {
		
		return $http({
			method : 'GET',
			url : Constants.DASHBOARDCOVID_BASE_URL +"/decodifiche/repartoricovero" 
		});
	};
	
	
	dashboardcovidService.loadEsitidimissioni = function() {
		
		return $http({
			method : 'GET',
			url : Constants.DASHBOARDCOVID_BASE_URL +"/decodifiche/esitodimissioni" 
		});
	};
	
	dashboardcovidService.loadLaboratori = function() {
		
		return $http({
			method : 'GET',
			url : Constants.DASHBOARDCOVID_BASE_URL +"/decodifiche/laboratorio" 
		});
	};
		
	dashboardcovidService.logout = function() {
		return $http({method : 'GET',url : Constants.DASHBOARDCOVID_BASE_URL +"/localLogout"});
	};
	
	
	
	dashboardcovidService.auraSearchByCf = function(codiceFiscale) {
		return $http({
			method : 'GET',
			url : Constants.DASHBOARDCOVID_BASE_URL +"/aura/" +  codiceFiscale
		});
	};
	
	dashboardcovidService.auraSearchByCognome = function(nome, cognome, dataNascita) {
		var mm = dataNascita.getMonth() + 1; 
		var dd = dataNascita.getDate();
		var yyyy = dataNascita.getFullYear();

		return $http({
			method : 'GET',
			url : Constants.DASHBOARDCOVID_BASE_URL +"/aura/"+nome+"/"+cognome+"/"+(dd>9 ? '' : '0') + dd + "/"+(mm>9 ? "" : "0") + mm + "/"+yyyy
		});
	};
	
	dashboardcovidService.auraSearchByIdSoggetto = function(idSoggetto) {
		return $http({
			method : 'GET',
			url : Constants.DASHBOARDCOVID_BASE_URL +"/aura/find/" +  idSoggetto
		});
	};
	
	dashboardcovidService.loadDisponibilita = function(transposed) {
		
		return $http({
			method : 'GET',
			cache: true,
			url : Constants.DASHBOARDCOVID_BASE_URL +"/postiletto/disponibilita" + (transposed?"/transposed":"")
		});
	};
	
	

	return dashboardcovidService;
	
}]);
