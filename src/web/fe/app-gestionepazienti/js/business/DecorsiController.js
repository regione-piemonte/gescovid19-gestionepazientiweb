appControllers.controller('DecorsiCtrl', [ '$scope', 'gestionepazientiService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$routeParams',
    function($scope, gestionepazientiService,$translate, $location, $uibModal,$rootScope,sessionStorage,$routeParams) {
		console.log("Pazienti  Controller");
		//$rootScope.breadcrumbItems = [{"label":$translate.instant("PROJECTS"), "title": $translate.instant("PROJECTS"), link: "#/pazienti"}];
		var tipolistaDecorsi = $routeParams.tipolistaDecorsi;

		$scope.filterPaziente = {};
		$scope.loading  = false;
		$scope.pazienti = new Array();
		$scope.filtered = {currentPage:1};
		$scope.pageSize = 10;
		$scope.order = {};
		$scope.filter = {};

		
		$scope.pazienteNomeFilter = function(paziente) {
			var keyword = new RegExp($scope.filter.nomeFilter, 'i');
			return !$scope.filter.nomeFilter || (paziente.nome && keyword.test(paziente.nome ));
		};
		
		$scope.pazienteCognomeFilter = function(paziente) {
			var keyword = new RegExp($scope.filter.cognomeFilter, 'i');
			return !$scope.filter.cognomeFilter || (paziente.cognome && keyword.test(paziente.cognome ));
		};
		
		$scope.pazienteCodicefiscaleFilter = function(paziente) {
			var keyword = new RegExp($scope.filter.codicefiscaleFilter, 'i');
			return !$scope.filter.codicefiscaleFilter || (paziente.codiceFiscale && keyword.test(paziente.codiceFiscale ));
		};
	
		$scope.pazienteAsrFilter = function(paziente) {
			var keyword = new RegExp($scope.filter.asrFilter, 'i');
			return !$scope.filter.asrFilter || (paziente.asr.descrizione && keyword.test(paziente.asr.descrizione));
		};

		
		$scope.selectedPaziente = {idSoggetto:null};
		
		$scope.selectPaziente =  function(selectedPazienteId){
			$scope.selectedPaziente.idSoggetto = selectedPazienteId;
			for (var i = 0; i < $scope.pazienti.length; i++) {
                if ($scope.pazienti[i].idSoggetto != $scope.selectedPaziente.idSoggetto) {
                    $scope.pazienti[i].selected = false;
                }
            }
			
		};
		
		
		$scope.feedback = {};
		/*var loadPazienti = function(){
			
			$scope.pazienti = [
				{id_soggetto:1,
					codice_fiscale:'DDDMMM77A43L219X',
					id_asr:1,
					cognome:'Rossi',
					nome:'Mario',
					data_nascita_str:'1960/09/07',
					comune_residenza_istat:'006001',
					comune_domicilio_istat:'006001',
					indirizzo_domicilio:'Via Garibaldi 6',
					telefono_recapito:'333555555', nr_tamponi_richiesti:4
					}
			];
			
		}*/
		var loadPazienti = function(){			
			$scope.loading = true;
			$scope.feedback = {};
			gestionepazientiService.loadPazientiList(tipolistaDecorsi).then(function(result){
				if(result && result.data && result.data.length > 0){
					
					$scope.pazienti = result.data;
					console.log("loadPazienti", $scope.pazienti );
					var filterInSession = sessionStorage.get("pazienteListFilter");
					if(filterInSession){
						if(filterInSession["currentPage"])
							$scope.filtered.currentPage = filterInSession["currentPage"];
						if(filterInSession["nomeFilter"])
							$scope.nomeFilter = filterInSession["nomeFilter"];
						else
							$scope.nomeFilter = null;

						if(filterInSession["predicate"])
							$scope.order.predicate = filterInSession["predicate"];
						if(filterInSession["reverse"])
							$scope.order.reverse = filterInSession["reverse"];
					}
					else
						$scope.nomeFilter = null;
						$scope.loading = false;
						for (var i = 0; i < $scope.pazienti.length; i++) {		
							if($scope.pazienti[i].isMyPaziente) {
								console.log("haprogetti","true");
								$scope.showOnlyMyPazienti = true;
								break;
							}
						}
				}
				else{
//					$scope.pazienti = new Array();
//					$scope.feedback.type = 'info';
//					$scope.feedback.message = 'FEEDBACK_NO_PROJECTS_FOUND';
//					console.log("no data");
					$scope.loading = false;
				}
				Helpers.util.scrollTo();
					
			}, function(error){
				$scope.feedback.type = 'danger';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				if(error)
					$scope.feedback.detail = error;
				console.error("loadPaziente  Error", error)
			});
	
		}


		var saveFilterInSession = function(){
			var filterToSave = {};
			filterToSave.currentPage=$scope.filtered.currentPage;
			filterToSave.showOnlyMyPazienti=$scope.showOnlyMyPazienti;

			if($scope.nomeFilter) 
				filterToSave.nomeFilter=$scope.nomeFilter;
			if($scope.descFilter) 
				filterToSave.descFilter=$scope.descFilter;
			
			if($scope.order.predicate)
				filterToSave.predicate=$scope.order.predicate;
			if($scope.order.reverse)
				filterToSave.reverse=$scope.order.reverse;
			
			sessionStorage.set("pazienteListFilter",filterToSave);
			
		};
		
		$scope.showPaziente = function(p){
			saveFilterInSession();
			if(p)
				$location.path("pazienti/view/" + p.idSoggetto).search({'selectedTab': 'pazienteDetail'});
			else if( $scope.selectedPaziente.idSoggetto)
				$location.path("pazienti/view/" + $scope.selectedPaziente.idSoggetto);
		};

		$scope.showDetail = function(idPaziente){
			$location.path("pazienti/view/" + idPaziente).search({'selectedTab': 'pazienteDetail'});
		};
		loadPazienti();
		
		$scope.showNewDecorsoFromTrasferimentiModal = function(idPaziente){
			if(!idPaziente)
				idPaziente = $scope.selectedPaziente.idSoggetto
    		var newPazienteModal = $uibModal.open({templateUrl: 'partials/pazienti/nuovo_decorso.html',
    			controller: 'NuovoDecorsoModalCtrl', 
    			size: 'lg',
    			 resolve: {
	    	    	  idPaziente: function () {return idPaziente;},
	    	    	  azioneDecorso: function () {return 'inTrasferimento';},
	    	      	},
    			backdrop: 'static',});
	    		
    		newPazienteModal.result.then(function (paziente) {
    			Helpers.util.scrollTo();
    			$location.path('trasferimentiesterni/').search({'t': new Date().getTime()});
	    	   }, function () {});
	    };

	
		$scope.showSintomiFromDecorsoModal = function(idPaziente, idDecorso){
			if(!idPaziente)
				idPaziente = $scope.selectedPaziente.idSoggetto
    		var sintomiModal = $uibModal.open({templateUrl: 'partials/decorsi/apri_segnalazione.html',
    			controller: 'SintomiFromIdPazienteModalCtrl', 
    			size: 'lg',
    			 resolve: {
					  idPaziente: function () {return idPaziente;},
					  idDecorso: function () {return idDecorso;},
	    	      	},
    			backdrop: 'static',});
	    		
    		sintomiModal.result.then(function (paziente) {
    			Helpers.util.scrollTo();
	    	   }, function () {});
			};
			

}]);

appControllers.controller('PostiDisponibilitModalCtrl', [ '$scope', '$uibModalInstance', 'gestionepazientiService', 'toastService', '$uibModal',
    function($scope, $uibModalInstance, gestionepazientiService, toastService,$uibModal) {

	$scope.tipi_aree = ['TER_INT','TER_SEMI_INT','NIV','PNEUMO', 'INFETT','MED_INT', 'IN_ATTESA', 'ALTRO'];
	var loadDisponibilitaTransponded= function(){		
		console.log("loadDisponibilita")
		$scope.loading = true;
		$scope.feedback = {};
		$scope.vista = {situazione:"aggregati"};

		gestionepazientiService.loadDisponibilita(true).then(function(result){
			console.log("loadDisponibilita", result);
			if(result && result.data && result.data.length > 0){
				$scope.loading = false;

				//$scope.disponibilita = new Array();
				
				if(result.data){
					var dMap = {}
					for(var i=0;i<result.data.length;i++){
						var row = result.data[i];
						if(!dMap[row.ente.nome])
							dMap[row.ente.nome] =  {totaleDisponibili:0, totaleOccupati:0, totPostiTarget:row.ente.totPostiTarget};
						if(!dMap[row.ente.nome][row.struttura.nome])
							dMap[row.ente.nome][row.struttura.nome] =  {totaleDisponibili:0, totaleOccupati:0};
						
						for(var j=0;j<row.disponibilitaArea.length;j++){
							var da =row.disponibilitaArea[j];

							
							dMap[row.ente.nome][row.struttura.nome][da.area.idDArea] = da.disponibilita;

							dMap[row.ente.nome][row.struttura.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
							dMap[row.ente.nome][row.struttura.nome].totaleOccupati += da.disponibilita.postiOccupati;
							var percent = parseInt(100*da.disponibilita.postiOccupati/da.disponibilita.postiDisponibili);
							if(percent>80)
								da.disponibilita.style = "color: #ff4141;font-weight:bold;";
							else if(percent>70)
								da.disponibilita.style = "color: #ff9900;font-weight:bold;";
							else if(percent>60)
								da.disponibilita.style = "color: #f0db2e;font-weight:bold;";

							dMap[row.ente.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
							dMap[row.ente.nome].totaleOccupati += da.disponibilita.postiOccupati;
							
						}

						
					}
					for (var k in dMap) {
						if(dMap.hasOwnProperty(k)){
							//console.log(dMap[k]);
							var disponibili = dMap[k].totaleDisponibili;
							var occupati = dMap[k].totaleOccupati;
							if(disponibili>0){
								var percent = parseInt(100*occupati/disponibili);
								dMap[k].fullSize = percent;
								dMap[k].color = "#9ade00";
								if(percent>80)
									dMap[k].color = "#ff4141";
								else if(percent>70)
									dMap[k].color = "#ff9900";
								else if(percent>60)
									dMap[k].color = "#f0db2e";
								else
									dMap[k].color = "#9ade00";
							}
						}
					}

					
					$scope.disponibilita2 = dMap;
					
				}
				console.log("$scope.disponibilita2", $scope.disponibilita2);
			}
			else{
				$scope.loading = false;
			}
			Helpers.util.scrollTo();
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadDisponibilita  Error", error)
		});

	}

	loadDisponibilitaTransponded();
	

		
	$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);






