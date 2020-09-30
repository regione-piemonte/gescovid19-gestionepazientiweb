

appControllers.controller('PazienteCtrl', [ '$scope', 'gestionepazientiService','$translate', '$location', 'toastService', '$routeParams',  '$uibModal','$rootScope', '$window', '$timeout',
    function($scope, gestionepazientiService,$translate,$location, toastService, $routeParams, $uibModal,$rootScope,$window,$timeout) {
		console.log("Paziente Controller");
		$scope.pazienteTab = {selected:'pazienteDetail'};
		
		$scope.idPaziente = $routeParams.idPaziente;

		$scope.typeRel = {};
		$scope.filtered = {currentPage:1};
		$scope.pageSize = 10;


		$scope.loading  = false;

		$scope.roles = new Array();
		$scope.feedback = {};
		
		$scope.paziente = {};
		
		$scope.pazienteTab = {selected:"pazienteDetail"};
		
		if($location.search()['selectedTab'])
			$scope.pazienteTab = {selected:$location.search()['selectedTab']};

		
		$scope.showTabDetail = function(){
			$scope.pazienteTab.selected = "pazienteDetail";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/"+$scope.idPaziente+"?selectedTab=pazienteDetail");

		};
		$scope.showTabDecorsi = function(){
			$scope.pazienteTab.selected = "pazienteDecorsi";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/"+$scope.idPaziente+"?selectedTab=pazienteDecorsi");
		};
		$scope.showTabTamponi = function(){
			$scope.pazienteTab.selected = "pazienteTamponi";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/"+$scope.idPaziente+"?selectedTab=pazienteTamponi");
		};
		
		$scope.showTabDiari = function(){
			$scope.pazienteTab.selected = "pazienteDiari";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/"+$scope.idPaziente+"?selectedTab=pazienteDiari");
		};
				
		$scope.showTabInterventiUsca = function(){
			$scope.pazienteTab.selected = "pazienteInterventiUsca";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/"+$scope.idPaziente+"?selectedTab=pazienteInterventiUsca");
		};

		$scope.showTabContattiDa = function () {
			$scope.pazienteTab.selected = "pazienteContattiDa";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/" + $scope.idPaziente + "?selectedTab=pazienteContattiDa");
		};

		$scope.showTabContattiA = function () {
			$scope.pazienteTab.selected = "pazienteContattiA";
			$window.history.pushState("", "paziente detail", "#/pazienti/view/" + $scope.idPaziente + "?selectedTab=pazienteContattiA");
		};
		
		 $scope.showDeletePazienteModal = function(paziente){
		    	console.log('showDeletePazienteModal',paziente);
				var deletePazienteModal = $uibModal.open({
					animation : true,
					templateUrl : 'confirmDialog.html',
					controller : 'ConfirmDialogCtrl',
					backdrop  : 'static',
					resolve: { 
						question: function () {
							return {"title":"DELETE_PROJECT_CONFIRM_TITLE","message":"DELETE_PROJECT_CONFIRM_MESSAGE"};
						}
					}
				});

				deletePazienteModal.result.then(function() {
						console.log("confirm Delete");
						//CANCELLAZIONE LOGICA
						//gestionepazientiService.deletePaziente(idPaziente).then(function(result){
						  paziente.idPazienteStatus = Constants.PROJECT_STATUS_DELETED;
						  paziente.pazienteEndDate = Date.now();
						  gestionepazientiService.savePaziente(paziente).then(function(result){
							$scope.feedback.message = 'FEEDBACK_PROJECT_DELETED';
							$scope.feedback.type = 'success';
							$location.path('pazienti').search({'t': new Date().getTime()});
							Helpers.util.scrollTo();
							
						}, function(error){
							$scope.feedback.type = 'danger';
							$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
							if(error)
								$scope.feedback.detail = error;
							console.error("deletePaziente  Error", error)
							Helpers.util.scrollTo();
						});
					
						
					}, function() {
				});
				
		    };  
		
		
		
		  $scope.isPazienteToDelete = function(paziente){
		    	var toDelete = false;
				if( paziente.idPazienteStatus == Constants.PROJECT_STATUS_OPEN  ){
					if (paziente.datasetCollections == null || paziente.datasetCollections.length == 0) return true;
					else {
						for (var i = 0; i < paziente.datasetCollections.length; i++) {					
							if (paziente.datasetCollections[i].idCollectionStatus != Constants.COLLECTION_STATUS_DELETED)
								return false;
							else toDelete = true;
						}
					}
				}
				
				return toDelete;		
				
			};

		var loadPaziente = function(){
			if($routeParams.idPaziente){
				$scope.loading = true;
				gestionepazientiService.loadPaziente($routeParams.idPaziente).then(function(result){
					console.log("loadPaziente", result);
					$scope.loading  = false;
	
					if(result && result.data){
						$scope.paziente = result.data;
						if($scope.pazienteTab && $scope.pazienteTab.selected == 'pazienteDiari'){
							loadDiari();
						}
						else if($scope.pazienteTab && $scope.pazienteTab.selected == 'pazienteInterventiUsca'){
							loadInterventiUsca();
						}
					}
					else{
						$scope.paziente = null;
						$scope.feedback.type = 'warning';
						$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
					}
	
				}, function(error){
					console.error("loadPaziente  Error", error)
					$scope.loading  = false;
					if(error.status && error.status == 400){
						$scope.feedback.type = 'warning';
						$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
					}else if(error.status && error.status == 401){
						$scope.feedback.type = 'danger';
						$scope.feedback.message = error.data.message;
					} else {
						$scope.feedback.type = 'danger';
						$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
						if(error.data && error.data.message){
							$scope.feedback.detail = error.data.message;
						}
					}
				});
			}
		};
		
		loadPaziente();
		
		$scope.diari = new Array();
		var loadDiari = function(){
			$scope.loading = true;
			$scope.diari = new Array();
			gestionepazientiService.loadDiari($routeParams.idPaziente).then(function(result){
				console.log("loadDiari", result);
				$scope.loading  = false;

				if(result && result.data && result.data.length>0 && result.data[0].dettagli){
					$scope.diari = result.data[0].dettagli.sort(function(a,b){return new Date(a.data).getTime()<new Date(b.data).getTime()?1:-1});
				}

			}, function(error){
				console.error("loadDiari  Error", error)
				$scope.loading  = false;
				if(error.status && error.status == 400){
					$scope.feedback.type = 'warning';
					$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
				}else if(error.status && error.status == 401){
					$scope.feedback.type = 'danger';
					$scope.feedback.message = error.data.message;
				} else {
					$scope.feedback.type = 'danger';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
					if(error.data && error.data.message){
						$scope.feedback.detail = error.data.message;
					}
				}
			});
		};
		
		$scope.esplodiSintomi = function(sintomi){
			var result = "";
			if(sintomi && sintomi.length>0){
				result += sintomi[0].nome;
				for (var i = 1; i < sintomi.length; i++) {
					result += ", " + sintomi[i].nome;
					if(sintomi[i].valore){
						result += ' ' + sintomi[i].valore + ' ' + sintomi[i].unita_misura;
					}
				}
			}
			return result;
			
		};

			$scope.deleteDecorso = function (idDecorso) {
				var deleteDecorsoModal = $uibModal.open({
					animation: true,
					templateUrl: 'confirmDialog.html',
					controller: 'ConfirmDialogCtrl',
					backdrop: 'static',
					resolve: {
						question: function () {
							return { "title": "Conferma cancellazione", "message": "Sei sicuro di voler cancellare il decorso con id " + idDecorso + " ?" };
						}
					}
				});

				deleteDecorsoModal.result.then(function () {
					gestionepazientiService.deleteDecorso(idDecorso).then(function (result) {
						if (result && result.status == 200 && result.data == '1') {

							var index = $scope.paziente.elencoDecorso.findIndex(d => d.idDecorso == idDecorso);
							$scope.paziente.elencoDecorso.splice(index, 1);
						}
						else {
							$scope.feedback.type = 'danger';
							$scope.feedback.message = 'FEEDBACK_OPERAZIONE_NON_EFFETTUATA';
							$scope.feedback.detail = 'Impossibile cancellare decorso con id: ' + idDecorso;
						}
					}, function (error) {
						$scope.feedback.type = 'danger';
						$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
						if (error)
							$scope.feedback.detail = error.data && error.data.title;
					});
				})
			}

			$scope.deleteTampone = function (idTampone) {
				var deleteTamponeModal = $uibModal.open({
					animation: true,
					templateUrl: 'confirmDialog.html',
					controller: 'ConfirmDialogCtrl',
					backdrop: 'static',
					resolve: {
						question: function () {
							return { "title": "Conferma cancellazione", "message": "Sei sicuro di voler cancellare il tampone con id " + idTampone + " ?" };
						}
					}
				});

				deleteTamponeModal.result.then(function () {
					gestionepazientiService.deleteTampone(idTampone).then(function (result) {
						if (result && result.status == 200 && result.data == '1') {

							var index = $scope.paziente.elencoTampone.findIndex(t => t.idTampone == idTampone);
							$scope.paziente.elencoTampone.splice(index, 1);
						}
						else {
							$scope.feedback.type = 'danger';
							$scope.feedback.message = 'FEEDBACK_OPERAZIONE_NON_EFFETTUATA';
							$scope.feedback.detail = 'Impossibile cancellare tampone con id: ' + idDecorso;
						}

					}, function (error) {
						$scope.feedback.type = 'danger';
						$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
						if (error)
							$scope.feedback.detail = error.data && error.data.title;
					});
				})
			}

		$scope.interventiUsca = new Array();
		var loadInterventiUsca = function(){
			$scope.loading = true;
			$scope.interventiUsca = new Array();
			gestionepazientiService.loadInterventiUsca($routeParams.idPaziente).then(function(result){
				console.log("loadInterventiUsca", result);
				$scope.loading  = false;

				if(result && result.data && result.data.length>0){
					$scope.interventiUsca = result.data.sort(function(a,b){return new Date(a.data).getTime()<new Date(b.data).getTime()?1:-1});
				}

			}, function(error){
				console.error("loadInterventiUsca  Error", error)
				$scope.loading  = false;
				if(error.status && error.status == 400){
					$scope.feedback.type = 'warning';
					$scope.feedback.message = 'FEEDBACK_INTERVENTI_USCA_NOT_FOUND';
				}else if(error.status && error.status == 401){
					$scope.feedback.type = 'danger';
					$scope.feedback.message = error.data.message;
				} else {
					$scope.feedback.type = 'danger';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
					if(error.data && error.data.message){
						$scope.feedback.detail = error.data.message;
					}
				}
			});
		};
		
		$scope.getAutoreCambioStatoInterventoUsca = function(iu){
			var result = "";
			if(iu.stato &&  iu.cronologia && 
					(iu.stato.codice == Constants.STATI_SCHEDE_USCA_PRESO_IN_CARICO || 
					  		iu.stato.codice == Constants.STATI_SCHEDE_USCA_ESEGUITO ||
							iu.stato.codice == Constants.STATI_SCHEDE_USCA_RESPINTO 
						)){
				for (var i = 0; i < iu.cronologia.length; i++) {
					if(iu.cronologia[i].stato && iu.cronologia[i].stato.codice==iu.stato.codice){
						if(iu.cronologia[i].utente && iu.cronologia[i].utente.nome && iu.cronologia[i].utente.cognome)
							result += '<small class="cell-row">da ' + iu.cronologia[i].utente.nome + ' ' +  iu.cronologia[i].utente.cognome+'</small>'
						if(iu.cronologia[i].utente && iu.cronologia[i].utente.telefono )
							result += '<small class="cell-row">Tel:' + iu.cronologia[i].utente.telefono + '</small>'
					}
				}
			}
			return result;
		}

		$scope.showNewTamponeModal = function(){
    		var newTamponeModal = $uibModal.open({templateUrl: 'partials/pazienti/edit_tampone.html',
    			controller: 'NuovoTamponeModalCtrl', 
    			size:'lg',
    			 resolve: {
	    	    	  idPaziente: function () {return $scope.paziente.idSoggetto;},
	    	    	  idTampone: function(){return null;}

	    	      	},
    			backdrop: 'static',});
	    		
    		newTamponeModal.result.then(function () {
    			Helpers.util.scrollTo();
    			$location.path("pazienti/view/" + $scope.paziente.idSoggetto).search({'selectedTab': 'pazienteTamponi', 't': new Date().getTime()});
	    	   }, function () {});
	    };
	    
		$scope.showEditTamponeModal = function(idTampone){
    		var newTamponeModal = $uibModal.open({templateUrl: 'partials/pazienti/edit_tampone.html',
    			controller: 'NuovoTamponeModalCtrl', 
    			size:'lg',
    			 resolve: {
	    	    	  idPaziente: function () {return $scope.paziente.idSoggetto;},
	    	    	  idTampone: function(){return idTampone;}
	    	      	},
    			backdrop: 'static',});
	    		
    		newTamponeModal.result.then(function () {
    			Helpers.util.scrollTo();
    			$location.path("pazienti/view/" + $scope.paziente.idSoggetto).search({'selectedTab': 'pazienteTamponi', 't': new Date().getTime()});
	    	   }, function () {});
	    };
	    
		$scope.showNewDecorsoModal = function(azioneDecorso){
    		var newPazienteModal = $uibModal.open({templateUrl: 'partials/pazienti/nuovo_decorso.html',
    			controller: 'NuovoDecorsoModalCtrl',
    			size: 'lg',
    			 resolve: {
	    	    	  idPaziente: function () {return $routeParams.idPaziente;},
	    	    	  azioneDecorso: function () {return azioneDecorso;},

	    	      	},
    			backdrop: 'static',});
	    		
    		newPazienteModal.result.then(function (paziente) {
    			Helpers.util.scrollTo();
    			$location.path('pazienti/view/'+$routeParams.idPaziente).search({'t': new Date().getTime()});
	    	   }, function () {});
	    };

		$scope.showNewDiarioModal = function(){
    		var newPazienteModal = $uibModal.open({templateUrl: 'partials/pazienti/nuovo_diario.html',
    			controller: 'NuovoDiarioModalCtrl',
    			size: 'lg',
    			 resolve: {
	    	    	  paziente: function () {return $scope.paziente;}
	    	      	},
    			backdrop: 'static',});
	    		
    		newPazienteModal.result.then(function (paziente) {
    			Helpers.util.scrollTo();
    			$location.path('pazienti/view/'+$routeParams.idPaziente).search({'t': new Date().getTime()});
	    	   }, function () {});
	    };

	    $scope.openDettaglioInterventoUsca = function(iu){
    		var newDettagioInterventoUsca = $uibModal.open({templateUrl: 'partials/interventiusca/dettaglio_intervento_usca.html',
    			controller: 'DettaglioInterventoUscaModalCtrl', 
    			//size:'lg',
    			 resolve: {
	    	    	  paziente: function () {return $scope.paziente;},
	    	    	  iu: function(){return iu;}

	    	      	},
    			backdrop: 'static',});
	    		
//    		newDettagioInterventoUsca.result.then(function () {
//    			Helpers.util.scrollTo();
//    			$location.path("pazienti/view/" + $scope.paziente.idSoggetto).search({'selectedTab': 'pazienteTamponi', 't': new Date().getTime()});
//	    	   }, function () {});
	    };

		$scope.showEditPazienteModal = function(){
    		var newPazienteModal = $uibModal.open({templateUrl: 'partials/pazienti/edit.html',controller: 'NewPazienteModalCtrl', 
    			 resolve: {
	    	    	  idPaziente: function () {return $routeParams.idPaziente;},
	    	      	},
    			backdrop: 'static',});
	    		
    		newPazienteModal.result.then(function (paziente) {
    			Helpers.util.scrollTo();
    			$location.path('pazienti/view/'+$routeParams.idPaziente).search({'t': new Date().getTime()});
	    	   }, function () {});
	    };
	    
		$scope.showSintomiFromDecorsoModal = function(paziente, decorso, sintomo){
			console.log('Show sintomi');
			var newSegnalazioniModal = $uibModal.open({templateUrl: 'partials/decorsi/apri_segnalazione.html',
				controller: 'SintomiModalCtrl', 
				size: 'lg',
				 resolve: {
					paziente: function () {return paziente;},
					decorso: function () {return decorso;},
					sintomo: function () {return sintomo;},
				},
				backdrop: 'static',});
				
			newSegnalazioniModal.result.then(function (isPresoInCarico, idSoggetto) {
				//Helpers.util.scrollTo();
    			//$location.path('pazienti/view/'+$routeParams.idPaziente).search({'t': new Date().getTime()});
				
				var newSegnalazioniRispostaModal = $uibModal.open({templateUrl: 'partials/decorsi/risposta_segnalazione.html',
					controller: 'SintomiRispostaModalCtrl', 
					 resolve: {
						 isPresoInCarico: function () {return isPresoInCarico;},
						 decorso: decorso, 
						 paziente: paziente
					},
					backdrop: 'static',});
				
				newSegnalazioniRispostaModal.result.then(function (paziente) {
					Helpers.util.scrollTo();
					$location.path("pazienti/view/" + $scope.paziente.idSoggetto).search({'selectedTab': 'pazienteDecorsi', 't': new Date().getTime()});
				}, function () {});
				
				
	    	   }, function () {});

			
		};

		//		
//		$scope.showEditPazienteModal = function(idPaziente){
//    		var editPazienteModal = $uibModal.open({
//	    		  templateUrl: 'partials/paziente/edit.html',//'newPazienteModal.html',
//	    	      controller: 'EditPazienteModalCtrl',
//	    	      size: 'lg',
//	    	      backdrop: 'static',
//	    	      resolve: {
//	    	    	  idPaziente: function () {return idPaziente;},
//	    	      	}
//	        	});
//	    		
//    		editPazienteModal.result.then(function () {
//    			$location.path("paziente/view/" + idPaziente).search({'selectedTab': 'pazienteDetail', 't': new Date().getTime()});
//	    	}, function () {});
//	    };
//	    

	    $goToPazienteList = function(){
	    	delete $rootScope.breadcumpsItems; 
	    	$location.path("pazienti");
	    };
	    
	    $scope.debugCollection = function(){
	    	console.log("collection", $scope.collection);
	    };
			
			

			$scope.showNewNewContattoModal = function () {
				var newContattoModal = $uibModal.open({
					templateUrl: 'partials/pazienti/nuovo_contatto.html',
					controller: 'NuovoContattoModalCtrl',
					size: 'lg',
					resolve: {
						paziente: function () { return $scope.paziente; },
					},
					backdrop: 'static',
				});

				newContattoModal.result.then(function (paziente) {
					Helpers.util.scrollTo();
					$location.path('pazienti/view/' + $routeParams.idPaziente).search({ 't': new Date().getTime() });
				}, function () { });
			};

			$scope.showPaziente = function (idSoggetto) {
				$location.path("pazienti/view/" + idSoggetto).search({'selectedTab': 'pazienteDetail'});
			};

}]);



appControllers.controller('NuovoTamponeModalCtrl', [ '$scope', '$uibModalInstance', 'gestionepazientiService', 'toastService', '$rootScope', 'idPaziente','idTampone',
    function($scope, $uibModalInstance, gestionepazientiService, toastService,$rootScope, idPaziente,idTampone) {
	
	console.log("idPazienteTampone",idPaziente, idTampone);
	$scope.idTampone = idTampone;
	$scope.debug = function(){console.log("tampone", $scope.tampone);};
	
	var loadPaziente = function(){
		if(idPaziente){
			$scope.loading = true;
			gestionepazientiService.loadPaziente(idPaziente).then(function(result){
				console.log("loadPazientetampone",result);
				$scope.loading  = false;

				if(result && result.data){
					$scope.paziente = result.data;
					if($scope.idTampone){
						for (var i = 0; i < $scope.paziente.elencoTampone.length; i++) {
							if($scope.paziente.elencoTampone[i].idTampone==$scope.idTampone){
								$scope.tampone = $scope.paziente.elencoTampone[i];
								//$scope.tampone.tamponeAutorizzato= true;
								if($scope.tampone.idLaboratorio == 6 ) //altro laboratorio
									$scope.tampone.idLaboratorio = null;
								break;
							}
						}
						
					}
					else
						$scope.tampone = {};
				}
				else{
					$scope.paziente = null;
					$scope.feedback.type = 'warning';
					$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
				}

			}, function(error){
				console.error("loadPaziente  Error", error)
				$scope.loading  = false;
				if(error.status && error.status == 400){
					$scope.feedback.type = 'warning';
					$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
				}else if(error.status && error.status == 401){
					$scope.feedback.type = 'danger';
					$scope.feedback.message = error.data.message;
				} else {
					$scope.feedback.type = 'danger';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
					if(error.data && error.data.message){
						$scope.feedback.detail = error.data.message;
					}
				}
			});
		}
	};
	
	loadPaziente();
	
	var loadTipologiaTest = function(){
		console.log("loadTipologiaTest");
		$scope.feedback = {};
		gestionepazientiService.loadTipologiaTest().then(function(result){
			console.log("loadTipologiaTest result", result);
			if(result && result.data){
				$scope.tipologieTest = result.data;
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadTipologiaTest  Error", error)
		});

	}
	
	loadTipologiaTest();
	
	var loadLaboratori = function(){
		console.log("loadLaboratori");
		$scope.feedback = {};
		gestionepazientiService.loadLaboratori().then(function(result){
			console.log("loadLaboratori result", result);
			if(result && result.data){
				$scope.laboratori = [];
				for(var i=0;i<result.data.length;i++){
					if(result.data[i].idLaboratorio!=Constants.ID_LABORATORIO_LAB_SYNLAB || $rootScope.currentUser.idAsr==Constants.ASR_AL){
						result.data[i].label = result.data[i].descrizione;
						if(result.data[i].idLaboratorio != Constants.ID_ALTRO_LABORATORIO){
							if(result.data[i].capacitaGiornaliera && result.data[i].capacitaGiornaliera>0)
								result.data[i].label +=' - Disponibilità lavorazione tamponi: ' + result.data[i].capacitaGiornaliera;
							//	result.data[i].label +=' - tamponi assegnati: '+ result.data[i].tamponiDaLavorare; 
							
						}
						$scope.laboratori.push(result.data[i]);
					}

				}
				console.log("laboratori ", $scope.laboratori)
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadLaboratori  Error", error)
		});

	}
	
	loadLaboratori();
	
	$scope.selectLaboratorio = function(){
//		$scope.feedback.laboratorio = {};
//		var laboratorio;
//		if($scope.tampone.idLaboratorio){
//			for (var i = 0; i < $scope.laboratori.length; i++) {
//				if($scope.laboratori[i].idLaboratorio == $scope.tampone.idLaboratorio){
//					laboratorio = $scope.laboratori[i];
//					break;
//				}
//			}
//		}
//		if(laboratorio && laboratorio.capacitaGiornaliera && laboratorio.capacitaGiornaliera>0){
//			var percentuleCodaLavoro = parseInt(laboratorio.tamponiDaLavorare*100/laboratorio.capacitaGiornaliera);
//			console.log("percentuleCodaLavoro", laboratorio.capacitaGiornaliera , laboratorio.tamponiDaLavorare, percentuleCodaLavoro);
//			if(percentuleCodaLavoro){
//				if(percentuleCodaLavoro>94){
//					$scope.feedback.laboratorio.style='danger';
//					$scope.feedback.laboratorio.icon='fa fa-exclamation';
//					//$scope.feedback.laboratorio.message='Il numero di tamponi da lavorare del laboratorio selezionato supera la sua capacità giornaliera';
//					$scope.feedback.laboratorio.message='Il numero di tamponi da lavorare del laboratorio selezionato ha quasi raggiunto la sua capacità giornaliera';
//				}
//				else if(percentuleCodaLavoro>79){
//					$scope.feedback.laboratorio.style='warning';
//					$scope.feedback.laboratorio.icon='fa fa-exclamation-triangle';
//					$scope.feedback.laboratorio.message='Il numero di tamponi da lavorare del laboratorio selezionato ha quasi raggiunto la sua capacità giornaliera';
//				}
//			}
//			
//		}
	}
	
	var validateTampone = function(){
		var result = new Array();
		
		if(!$scope.tampone.idLaboratorio){
			result.push("Laboratorio obbligatorio");
		}
		if(!$scope.tampone.medicoRichiedente){
			result.push("Medico richiedente obbligatorio");
		}
		if(!$scope.tampone.contattoRichiedente){
			result.push("Contatto richiedente obbligatorio");
		}

		
		return result;
	};
	
	$scope.saveTampone= function(closeModal){
		submitted = true;
			$scope.tampone.idSoggetto = idPaziente;
			var validateMessageDetail  =validateTampone();
			if(validateMessageDetail.length==0){

//				if($scope.tampone.tamponeAutorizzato == true) $scope.tampone.tamponeAutorizzato = 'P';
//				else $scope.tampone.tamponeAutorizzato = null;
				
				$scope.tampone.dataInserimentoRichiesta = new Date();
				$scope.loading = true;
				
				gestionepazientiService.saveTampone($scope.tampone).then(function(result){
					$scope.loading = false;
					if(closeModal){
						toastService.setToast("success","FEEDBACK_NEW_TAMPONE_SAVED");
						$uibModalInstance.close(result.data);
					}
					else{
						$scope.feedback.message = 'FEEDBACK_NEW_TAMPONE_SAVED';
						$scope.feedback.type = 'success';
					}
						
				}, function(error){
					$scope.loading = false;
					$scope.feedback.type = 'danger';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
					if(error)
						$scope.feedback.detail = error;
					console.error("saveTampone  Error", error)
				});
			}
			else{
				$scope.feedback.type = 'warning';
				$scope.feedback.message = 'FEEDBACK_NEW_TAMPONE_INVALID';
				$scope.feedback.details= validateMessageDetail;

			}
		}
	
	$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);
//ricoveroPostTrasferimento
appControllers.controller('NuovoDecorsoModalCtrl', [ '$scope', '$uibModalInstance', 'gestionepazientiService', 'toastService', 'idPaziente','$rootScope','$uibModal','$translate', 'azioneDecorso',
    function($scope, $uibModalInstance, gestionepazientiService, toastService, idPaziente,$rootScope,$uibModal,$translate, azioneDecorso) {
	console.log("NuovoDecorsoModalCtrl", idPaziente);
	
	$scope.azioneDecorso = azioneDecorso;
	$scope.idEsiti = {};
	$scope.idEsiti.ID_DECEDUTO = Constants.ID_DECEDUTO; 
	$scope.idEsiti.ID_GUARITO = Constants.ID_GUARITO;
	$scope.idEsiti.ID_USCITO = Constants.ID_USCITO;

	
	$scope.decorso = {idSoggetto:idPaziente};

	var tipiDecorso = Constants.TIPI_DECORSO;
	
	//var naturaStrutture = 'RSA', 'Ricettiva', 'Caserma'
		//					'ISOLAMENTO_DOMICILIARE','STRUTTURA', 'POST_RICOVERO', 'ASSEGNAZIONE_DOMICILIO' };
	$scope.tipoDecorso  = '';
	$scope.feedbackAslDomicilio = {feedback:{}};
	var aslDomicilioOk = true;
	
	$scope.eventi = new Array();	


	$scope.selectTipoEvento = function(){
		$scope.feedbackAslDomicilio = {feedback:{}};
		$scope.tipoDecorso = tipiDecorso["idTipoEvento_"+$scope.decorso.idTipoEvento];
		$scope.strutture = new Array();
		if($scope.tipoDecorso == 'STRUTTURA'){
			for (var i = 0; i < allStrutture.length; i++) {
				if(allStrutture[i].natura!='Ricettiva' && allStrutture[i].natura!='RSA' && allStrutture[i].natura!='Caserma'  )
					$scope.strutture.push(allStrutture[i]);
			}
		}
		if($scope.tipoDecorso == 'ASSEGNAZIONE_DOMICILIO' || $scope.tipoDecorso == 'DISPOSTA_QUARANTENA_EXTRA_DOMIC'  ){
//			for (var i = 0; i < allStrutture.length; i++) {
//				if(allStrutture[i].natura=='Ricettiva' || allStrutture[i].natura=='RSA' || allStrutture[i].natura=='Caserma' )
//					$scope.strutture.push(allStrutture[i]);
//			}
			loadStruttureNonAcute();
		}
		if($scope.tipoDecorso == 'ISOLAMENTO_DOMICILIARE' || $scope.tipoDecorso == 'DISPOSTA_QUARANTENA_DOMIC' ){  // era POST_RICOVERO
			aslDomicilioOk = false;	
			if(!$scope.paziente.aslDomicilio){
				$scope.feedbackAslDomicilio.feedback.type = 'warning';
				$scope.feedbackAslDomicilio.feedback.message = 'Asl di domicilio non valorizzata';
				$scope.feedbackAslDomicilio.feedback.detail = $translate.instant('FEEDBACK_DECORSO_POST_RICOVERO_ASL_NON_VALIDA');
				aslDomicilioOk = false;	
			}
			else if($scope.paziente.aslDomicilio =='EXTRA-REGIONE'){
				$scope.feedbackAslDomicilio.feedback.detail = $translate.instant('FEEDBACK_DECORSO_POST_RICOVERO_ASL_EXTRA_REGIONE');
				aslDomicilioOk  =true;
			}
			else{
				for (var i = 0; i < allAsl.length; i++) {
					if($scope.paziente.aslDomicilio==allAsl[i].descAslEstesa){
						if(allAsl[i].descRegione!='PIEMONTE'){
							$scope.feedbackAslDomicilio.feedback.type = 'warning';
							$scope.feedbackAslDomicilio.feedback.message = 'Asl di domicilio fuori regione';
							$scope.feedbackAslDomicilio.feedback.detail = $translate.instant('FEEDBACK_DECORSO_POST_RICOVERO_ASL_NON_VALIDA');
							aslDomicilioOk = false;
							break;
						}
						else{
							aslDomicilioOk = true;
							break;
						}
					}
				}
				if(!aslDomicilioOk){
					$scope.feedbackAslDomicilio.feedback.type = 'warning';
					$scope.feedbackAslDomicilio.feedback.message = 'Asl di domicilio non riconosciuta';
					$scope.feedbackAslDomicilio.feedback.detail = $translate.instant('FEEDBACK_DECORSO_POST_RICOVERO_ASL_NON_VALIDA');
				}
				
			}
		}
	};
	
	


	
	var loadPaziente = function(){
		if(idPaziente){
			$scope.loading = true;
			gestionepazientiService.loadPaziente(idPaziente).then(function(result){
				console.log("loadPaziente", result);
				$scope.loading  = false;

				if(result && result.data){
					$scope.paziente = result.data;
					
					if($scope.paziente.elencoDecorso && $scope.paziente.elencoDecorso.length>0){
						var maxId = $scope.paziente.elencoDecorso[0].idDecorso;
						for (var i = 0; i < $scope.paziente.elencoDecorso.length; i++) {
							if(maxId<=$scope.paziente.elencoDecorso[i].idDecorso){
								$scope.decorso.sintomi = $scope.paziente.elencoDecorso[i].sintomi;
								if($scope.decorso.sintomi=='SI')	
									$scope.decorso.dataInizioSint = new Date($scope.paziente.elencoDecorso[i].dataInizioSint);
								maxId= $scope.paziente.elencoDecorso[i].idDecorso;
							}
						}
						
					}
				}
				else{
					$scope.paziente = null;
					$scope.feedback.type = 'warning';
					$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
				}

			}, function(error){
				console.error("loadPaziente  Error", error)
				$scope.loading  = false;
				$scope.feedback.type = 'danger';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			});
		}
	};
	
	loadPaziente();
	
	
	$scope.copiaIndirizzoDaDomicilio = function(){
		$scope.decorso.indirizzoDecorso = $scope.paziente.indirizzoDomicilio;
		$scope.decorso.decorsoPresso = $scope.paziente.nome + " " + $scope.paziente.cognome;
		$scope.decorso.comuneRicoveroIstatCompleto = $scope.paziente.comuneDomicilio;
	};
	
	$scope.getComuni = function(){
		console.log("loadComuni");
		$scope.feedback = {};
		gestionepazientiService.loadComuni().then(function(result){
			console.log("loadComuni result", result);
			if(result && result.data){
				$scope.comuni = result.data;
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadComuni  Error", error)
		});

	}
	
	$scope.getComuni();
	
	var loadReparti = function(){
		console.log("loadReparti");
		$scope.feedback = {};
		gestionepazientiService.loadReparti().then(function(result){
			console.log("loadReparti result", result);
			if(result && result.data){
				$scope.reparti = result.data;
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadComuni  Error", error)
		});

	}
	
	loadReparti();
	
	var allAsl;
	var loadAsl = function(){
		console.log("loadAsl");
		$scope.feedback = {};
		gestionepazientiService.loadAsl().then(function(result){
			console.log("loadAsl result", result);
			if(result && result.data){
				allAsl = result.data;
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadAsl  Error", error)
		});

	};
	loadAsl();
	
	$scope.showPostiDisponibiliModal = function(){
		var postiDisponibiliModal = $uibModal.open({templateUrl: 'partials/decorsi/posti_disponibili.html','size':'auto-width',
			controller: 'PostiDisponibilitModalCtrl', backdrop: 'static',
			});
    		
		postiDisponibiliModal.result.then(function () {
    	   }, function () {});
    };
	/*var loadEsitidimissioni = function(){
		console.log("loadEsitidimissioni");
		$scope.feedback = {};
		gestionepazientiService.loadEsitidimissioni().then(function(result){
			console.log("loadEsitidimissioni result", result);
			if(result && result.data){
				$scope.esitidimissioni = result.data;
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadEsitidimissioni  Error", error)
		});

	}
	
	loadEsitidimissioni();*/
	
	$scope.utenteAbilitato = false;
	var allStrutture;
    var loadStrutture = function(){
		console.log("loadStrutture");
		$scope.feedbackNonAbilitato = {};
			if($rootScope.currentUser.elencoStruttura.length>0 ){
				allStrutture = $rootScope.currentUser.elencoStruttura;
				$scope.utenteAbilitato = true;
				if($scope.azioneDecorso == 'inTrasferimento'){
					$scope.decorso.idTipoEvento = Constants.ID_RICOVERATO; // ricoverato
					$scope.selectTipoEvento();		
				}
			}
			else{
				$scope.utenteAbilitato = false;
				console.log("no strutture");
				$scope.feedbackNonAbilitato.type = 'warning';
				$scope.feedbackNonAbilitato.message = 'FEEDBACK_UTENTE_NON_ABILITATO_A_DECORSI';
			}

	}
	
	loadStrutture();
	
	$scope.loadAree= function(idStruttura){
		console.log("loadAree",idStruttura);
		$scope.feedback = {};
		gestionepazientiService.loadAree(idStruttura).then(function(result){
			console.log("loadAree result", result);
			if(result && result.data){
				$scope.aree = new Array();
				for (var i = 0; i < result.data.length; i++) {
					result.data[i].nomeshort = result.data[i].nome.split("-")[0];
					$scope.aree.push(result.data[i]);
					
				}
				console.log("area",$scope.aree);
				if($scope.decorso.idTipoEvento == Constants.ID_ASSEGNAZIONE_DOMICILIO
					|| $scope.decorso.idTipoEvento == Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC){
					$scope.decorso.idArea = $scope.aree[0].idArea;
				}
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadAree  Error", error)
		});

	}
	
	var loadEventi = function(){
		console.log("loadEventi");
		$scope.feedback = {};
		gestionepazientiService.loadEventi().then(function(result){
			console.log("loadEventi result", result);
			if(result && result.data){
				console.log("$scope.eventiAll",result.data);
				$scope.eventi = new Array();	
				if($scope.azioneDecorso == 'inQuarantena'){
					for(var i=0;i<result.data.length;i++){
						if ( result.data[i].idTipoEvento == Constants.ID_ISOLAMENTO_DOMICILIARE || 
							 //result.data[i].idTipoEvento == Constants.ID_ISOLAMENTO_FIDUCIARIO || 
							 result.data[i].idTipoEvento == Constants.ID_DISPOSTA_QUARANTENA_DOMIC || 
							 result.data[i].idTipoEvento == Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC || 
							 result.data[i].idTipoEvento == Constants.ID_ASSEGNAZIONE_DOMICILIO)
							$scope.eventi.push(result.data[i]);	
					}
				}
				else{
					for(var i=0;i<result.data.length;i++){
						if ( result.data[i].idTipoEvento != Constants.ID_ISOLAMENTO_DOMICILIARE && 
							 result.data[i].idTipoEvento != Constants.ID_ISOLAMENTO_FIDUCIARIO && 
							 result.data[i].idTipoEvento != Constants.ID_DISPOSTA_QUARANTENA_DOMIC && 
							 result.data[i].idTipoEvento != Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC && 
							 result.data[i].idTipoEvento != Constants.ID_ASSEGNAZIONE_DOMICILIO &&
							 result.data[i].idTipoEvento != Constants.ID_POST_RICOVERO &&
							 result.data[i].idTipoEvento != Constants.ID_SEGNALAZIONE &&
							 result.data[i].idTipoEvento != Constants.ID_PRESO_IN_CARICO &&
							 result.data[i].idTipoEvento != Constants.ID_INFO_DA_INTEGRARE
							)
							$scope.eventi.push(result.data[i]);	
					}
				}
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");
			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadEventi  Error", error)
		});

	}
	
	loadEventi();
	
	
	var loadStruttureNonAcute = function(){
		console.log("loadStruttureNonAcute");
		$scope.feedback = {};
		gestionepazientiService.loadStruttureNonAcute().then(function(result){
			console.log("loadStruttureNonAcute result", result);
			if(result && result.data){
				$scope.strutture = result.data;
			}
			else{
				$scope.feedback.type = 'error';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				console.log("no data");

			}
				
		}, function(error){
			$scope.feedback.type = 'danger';
			$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
			if(error)
				$scope.feedback.detail = error;
			console.error("loadComuni  Error", error)
		});

	}
	
	
//	var validateDecorso = function(){
//		var result = new Array();
//		if($scope.decorso.comuneRicoveroIstat==0)
//			$scope.decorso.comuneRicoveroIstat = null;
//		if(!$scope.decorso.comuneRicoveroIstat && !$scope.decorso.ospedaleRicovero && !$scope.decorso.idRepRicovero){
//			result.push("Inserire il comune di ricovero o in alternativa l'ospedale e il reparto di ricovero");
//		}
//		else if($scope.decorso.comuneRicoveroIstat && ($scope.decorso.ospedaleRicovero || $scope.decorso.idRepRicovero)){
//			result.push("Se รจ inserito il comune di ricovero non bisogna inserire ospedale e reparto di ricovero");
//		}
//		
//		return result;
//	};
	

	
	var validateDecorso = function(){
		var result = new Array();
		if(!$scope.decorso.idTipoEvento){
			result.push("Inserire l'esito");
		}
		if(!$scope.decorso.dataDimissioni){
			result.push("Inserire la data evento");
		}
		if( $scope.decorso.idTipoEvento != Constants.ID_DECEDUTO && $scope.decorso.idTipoEvento != Constants.ID_GUARITO && $scope.decorso.idTipoEvento != Constants.ID_USCITO){
			
			//if($scope.azioneDecorso == 'inQuarantena' && !$scope.decorso.dataPrevFineEvento){
			//	result.push("Inserire la data fine quarantena");
			//}
			if(($scope.tipoDecorso == 'ISOLAMENTO_DOMICILIARE' || $scope.tipoDecorso == 'DISPOSTA_QUARANTENA_DOMIC')
				 && !$scope.decorso.comuneRicoveroIstatCompleto){
				result.push("Inserire il comune di isolamento");
			}
			if($scope.tipoDecorso == 'STRUTTURA' && ! $scope.decorso.idArea){
				result.push("Inserire la struttura e l'area");
			}
			if(($scope.tipoDecorso == 'ASSEGNAZIONE_DOMICILIO' || $scope.tipoDecorso == 'DISPOSTA_QUARANTENA_EXTRA_DOMIC')
					&& ! $scope.decorso.idArea){
				result.push("Inserire la struttura");
			}
			if(!$scope.decorso.sintomi){
				result.push("Valorizzare i sintomi");
			}
			if($scope.decorso.sintomi == 'SI' && !$scope.decorso.dataInizioSint){
				result.push("Valorizzare la data di inizio sintomatologia");
			}
			if($scope.decorso.sintomi == 'SI' && 
				($scope.decorso.dataInizioSint> new Date().getTime() || $scope.decorso.dataInizioSint < new Date(2020, 0, 1).getTime())) {
				result.push("La data di inizio sintomatologia deve essere compresa fra il 01/01/2020 e oggi");				
			}
			if($scope.tipoDecorso == 'ISOLAMENTO_DOMICILIARE' && !aslDomicilioOk ){ // era POST_RICOVERO
				result.push("Per il post recovero è necessario conoscere l'asl di domicilio del paziente, inserirla tramite la funzionalità di modifica paziente");
			}
			if($scope.tipoDecorso == 'DISPOSTA_QUARANTENA_DOMIC' && !aslDomicilioOk ){
				result.push("E' necessario conoscere l'asl di domicilio del paziente, inserirla tramite la funzionalità di modifica paziente");
			}
			//if(!$scope.decorso.dataEvento){
			//	result.push("Valorizzare la data evento");
			//}
		}
		
		return result;
	};
	
	$scope.save= function(closeModal){
		submitted = true;
		$scope.feedback = {};
		$scope.feedbackAslDomicilio = {feedback:{}};
		if($scope.decorso.comuneRicoveroIstatCompleto)
			$scope.decorso.comuneRicoveroIstat = $scope.decorso.comuneRicoveroIstatCompleto.istatComune;
		else
			$scope.decorso.comuneRicoveroIstat = null;
		var validateMessageDetail  =validateDecorso();
		
		if($scope.decorso.isolamentoDomiciliare) {
			$scope.decorso.ente  = null;
			$scope.decorso.struttura  = null;
			$scope.decorso.area  = null;
			$scope.decorso.idTipoEvento = Constants.ID_ISOLAMENTO_DOMICILIARE;
		}
		if(!$scope.decorso.isolamentoDomiciliare) {
			$scope.comuneRicoveroIstatCompleto  = null;

		}
		if(validateMessageDetail.length==0){
			$scope.loading = true;			
			$scope.decorso.idSoggetto = idPaziente;
			console.log("decorsoToSave",$scope.decorso);
			gestionepazientiService.saveDecorso($scope.decorso).then(function(result){
				$scope.loading = false;
				var feedbackMessage = $translate.instant('FEEDBACK_NEW_DECORSO_SAVED');
				if(result && result.data && result.data.message)
					feedbackMessage = result.data.message;
				if(closeModal){
					toastService.setToast("success",feedbackMessage, true);
					$uibModalInstance.close(result.data);
				}
				else{
					$scope.feedback.message = feedbackMessage;
					$scope.feedback.type = 'success';
				}
					
			}, function(error){
				$scope.loading = false;
				$scope.feedback.type = 'danger';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				if(error)
					$scope.feedback.detail = error;
				console.error("savePaziente  Error", error)
			});
		}else{
			$scope.feedback.type = 'warning';
			$scope.feedback.message = 'FEEDBACK_NEW_DECORSO_INVALID';
			$scope.feedback.details= validateMessageDetail;

		}
			
	}
	
	$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);

appControllers.controller('SintomiModalCtrl', [ '$scope', '$uibModalInstance', 'toastService','gestionepazientiService','paziente', 'decorso','sintomo', 
	function($scope, $uibModalInstance, toastService,gestionepazientiService,paziente, decorso, sintomo) {
		$scope.paziente = paziente;
		$scope.decorso = decorso;
		$scope.sintomo = sintomo;
		$scope.isLastDecorso = (decorso.idDecorso == $scope.paziente.elencoDecorso[0].idDecorso);
		$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};

		$scope.save= function(isPresoInCarico){
			/*
			submitted = true;
			$scope.feedback = {};
			$scope.feedbackAslDomicilio = {feedback:{}};
			if (isPresoInCarico) {
				decorsoPresaInCarico = {
					idSoggetto : paziente.idSoggetto,
					idTipoEvento : Constants.ID_PRESO_IN_CARICO,
				};
			} else {
				decorsoPresaInCarico = {
					idSoggetto : paziente.idSoggetto,
					idTipoEvento : Constants.ID_INFO_DA_INTEGRARE,
				};
			}
			$scope.loading = true;			
			console.log("decorsoToSave",decorsoPresaInCarico);
			gestionepazientiService.saveDecorso(decorsoPresaInCarico).then(function(result){
				$scope.loading = false;
				if(isPresoInCarico){
					toastService.setToast("success","FEEDBACK_PRESO_IN_CARICO");
					$uibModalInstance.close(result.data);
				}
				else{
					toastService.setToast("success","FEEDBACK_INFO_DA_INTEGRARE");
					$uibModalInstance.close(result.data);
				}
					
			}, function(error){
				$scope.loading = false;
				$scope.feedback.type = 'danger';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				if(error)
					$scope.feedback.detail = error;
				console.error("presaInCarica  Error", error)
			}); */
			$uibModalInstance.close(isPresoInCarico, paziente.idSoggetto);
				
		}
}]);

appControllers.controller('SintomiFromIdPazienteModalCtrl', [ '$scope', '$uibModalInstance', 'gestionepazientiService','idPaziente', 'idDecorso',
	function($scope, $uibModalInstance,gestionepazientiService, idPaziente, idDecorso) {
		$scope.idPaziente = idPaziente;
		$scope.idDecorso = idDecorso;
		var loadPaziente = function(){
			if(idPaziente){
				$scope.loading = true;
				gestionepazientiService.loadPaziente(idPaziente).then(function(result){
					console.log("loadPaziente", result);
					$scope.loading  = false;	
					if(result && result.data){
						$scope.paziente =  result.data;
						for (var i = 0; i < $scope.paziente.elencoDecorso.length; i++) {
							if ($scope.paziente.elencoDecorso[i].idDecorso == $scope.idDecorso) {
								$scope.decorso = result.data.elencoDecorso[i];
								$scope.sintomo = result.data.elencoDecorso[i].sintomo;	
							}
						}
					}
					else{
						$scope.paziente = {};
						$scope.feedback.type = 'warning';
						$scope.feedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
					}
	
				}, function(error){
					console.error("loadPaziente  Error", error);
					$scope.loading  = false;
					$scope.feedback.type = 'danger';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				});
			}
			else
				$scope.paziente = {};
		};
		loadPaziente();

		//$scope.decorso = decorso;
		//$scope.sintomo = sintomo;
		$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);


appControllers.controller('SintomiRispostaModalCtrl', [ '$scope', '$uibModalInstance', 'toastService','gestionepazientiService','isPresoInCarico', 'paziente','decorso',
	function($scope, $uibModalInstance, toastService,gestionepazientiService,isPresoInCarico, paziente, decorso) {
		$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
		$scope.paziente = paziente;
		$scope.decorso = decorso;

		$scope.isPresoInCarico = isPresoInCarico;
		$scope.decorsoPresaInCarico = {idSoggetto : paziente.idSoggetto};
		$scope.save= function(isPresoInCarico){
			submitted = true;
			if (isPresoInCarico) {
				$scope.decorsoPresaInCarico.idTipoEvento = Constants.ID_PRESO_IN_CARICO;
			} else {
				$scope.decorsoPresaInCarico.idTipoEvento = Constants.ID_INFO_DA_INTEGRARE;
			}
			$scope.loading = true;			
			console.log("decorsoToSave",$scope.decorsoPresaInCarico);
			gestionepazientiService.saveDecorso($scope.decorsoPresaInCarico).then(function(result){
				$scope.loading = false;
				if(isPresoInCarico){
					toastService.setToast("success","FEEDBACK_PRESO_IN_CARICO");
					$uibModalInstance.close(result.data);
				}
				else{
					toastService.setToast("success","FEEDBACK_INFO_DA_INTEGRARE");
					$uibModalInstance.close(result.data);
				}
					
			}, function(error){
				$scope.loading = false;
				$scope.feedback.type = 'danger';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				if(error)
					$scope.feedback.detail = error;
				console.error("presaInCarica  Error", error)
			});
				
		}
}]);


appControllers.controller('NuovoDiarioModalCtrl', [ '$scope', '$uibModalInstance', 'toastService','gestionepazientiService','paziente', 
	function($scope, $uibModalInstance, toastService,gestionepazientiService, paziente) {
		$scope.paziente = paziente;
		$scope.dettaglio = {soggetto:paziente.idSoggetto};
		var nowGiorno = new Date();
		nowGiorno.setHours(0);
		nowGiorno.setMinutes(0);
		nowGiorno.setSeconds(0);
		nowGiorno.setMilliseconds(0);
		var nowOra= new Date();
		nowOra.setSeconds(0);
		nowOra.setMilliseconds(0);

		$scope.data = {giorno: nowGiorno, ora: nowOra};
		$scope.sintomi = {};
		
		$scope.loading = false;
		var allSintomi = {};
		$scope.allSintomi_1;
		$scope.allSintomi_2;
		var loadSintomiDiario = function(){
			console.log("loadSintomiDiario");
			$scope.feedback = {};
			$scope.loading = true;
			gestionepazientiService.loadSintomiDiario().then(function(result){
				console.log("loadSintomiDiario result", result);
				$scope.loading = false;

				if(result && result.data){
					for (var i = 0; i < result.data.length; i++) {
						allSintomi[result.data[i].codice]=result.data[i];
					}
					result.data = result.data.sort(function(a,b){return a.nome<b.nome?-1:1});
					$scope.allSintomi_2 = result.data.splice(parseInt(result.data.length/2));
					$scope.allSintomi_1 = result.data;
				}
				else{
					$scope.feedback.type = 'error';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
					console.log("no data");

				}
					
			}, function(error){
				$scope.loading = false;
				$scope.feedback.type = 'danger';
				$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
				if(error)
					$scope.feedback.detail = error;
				console.error("loadSintomiDiario  Error", error)
			});

		};
		loadSintomiDiario();

		$scope.debug = function(){
			console.log("data", $scope.data);
			console.log("sintomi", $scope.sintomi);
			console.log("dettaglio", $scope.dettaglio);
		}
		
		var validateDettaglio = function(){
			var result = new Array();
			
			if(!$scope.data){
				result.push("Data obbligatoria");
			}
			if(!$scope.dettaglio.sintomi || $scope.dettaglio.sintomi.length==0){
				result.push("Inserire almeno un sintomo");
			}

			
			return result;
		};
		
		$scope.saveDettaglio= function(){
			$scope.dettaglio.data = $scope.data.giorno;
			if($scope.data.ora){
				$scope.dettaglio.data.setHours($scope.data.ora.getHours());
				$scope.dettaglio.data.setMinutes($scope.data.ora.getMinutes());
			}
			$scope.dettaglio.sintomi = new Array();
			for (var k in $scope.sintomi) {
				if($scope.sintomi.hasOwnProperty(k)){
					if($scope.sintomi[k].checked){
						var s =  $scope.sintomi[k];
						var nuovoSintomo = angular.copy(allSintomi[k]);
						nuovoSintomo.valore = s.valore;
						$scope.dettaglio.sintomi.push(nuovoSintomo)	
					}
				}
			}
			console.log("dettaglio", $scope.dettaglio);
			var validateMessageDetail  =validateDettaglio();
			if(validateMessageDetail.length==0){
				$scope.loading = true;
				gestionepazientiService.saveDettaglioDiario($scope.dettaglio).then(function(result){
					$scope.loading = false;
					toastService.setToast("success","FEEDBACK_NEW_DIARIO_SAVED");
					$uibModalInstance.close(result.data);
				}, function(error){
					$scope.loading = false;
					$scope.feedback.type = 'danger';
					$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
					if(error)
						$scope.feedback.detail = error;
					console.error("saveDettaglio  Error", error)
				});
			}
			else{
				$scope.feedback.type = 'warning';
				$scope.feedback.message = 'FEEDBACK_NEW_DIARIO_INVALID';
				$scope.feedback.details= validateMessageDetail;

			}
		};
		
		$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};


}]);

appControllers.controller('DettaglioInterventoUscaModalCtrl', [ '$scope', '$uibModalInstance', 'toastService','gestionepazientiService','paziente', 'iu',
	function($scope, $uibModalInstance, toastService,gestionepazientiService, paziente, iu) {
		$scope.paziente = paziente;
		$scope.iu = iu;
		$scope.interventoUscaTab = {selected:'richiesta'};
		
		$scope.medicoIntervento  = null;
		if(iu.stato &&  iu.cronologia && 
					(iu.stato.codice == Constants.STATI_SCHEDE_USCA_PRESO_IN_CARICO || 
					  		iu.stato.codice == Constants.STATI_SCHEDE_USCA_ESEGUITO ||
							iu.stato.codice == Constants.STATI_SCHEDE_USCA_RESPINTO 
						)){
			for (var i = 0; i < iu.cronologia.length; i++) {
				if(iu.cronologia[i].stato && iu.cronologia[i].stato.codice==iu.stato.codice){
					$scope.medicoIntervento = iu.cronologia[i].utente;
				}
			}
		}
		
		$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);

appControllers.controller('NuovoContattoModalCtrl', ['$scope', '$uibModal','$uibModalInstance', 'gestionepazientiService','$rootScope','paziente',
	function ($scope, $uibModal, $uibModalInstance, gestionepazientiService,$rootScope,paziente ) {
		$scope.paziente = paziente;
		$scope.idTipoContatto = null;
		$scope.soggettoA = null;
		$scope.noteContatto = '';
		$scope.loadingPazienti = false;
		$scope.soggettoDaSearchPressed = false;
		
		$scope.ngModelSoggettoASelected = function(value) {
			if (arguments.length) {
				//setter
				//console.log("ngModelSoggettoASelected.value SET: ", value);
				$scope.soggettoA = value;
			} else {
				// getter
				//console.log("ngModelSoggettoASelected.value GET: ", value);
			  	return $scope.soggettoA && ($scope.soggettoA.nome + ' ' + $scope.soggettoA.cognome + ' (' + $scope.soggettoA.codiceFiscale + ')');
			}
		  };

		  $scope.modelSoggettoAOptions = {
			debounce: {
			  default: 500,
			  blur: 250
			},
			getterSetter: true
		  };
		


		var getTipiContatto = function () {
			$scope.loading = true;
			$scope.feedback = {};
			gestionepazientiService.getTipiContatto().then(
				function (result) {
					console.log("getTipiContatto", result);
					$scope.loading = false;
					if (result && result.data && result.data.length > 0) {
						$scope.tipiContatto = result.data;
					}
					//Helpers.util.scrollTo();
				},
				function (error) {
					$scope.loading = false;
					$scope.feedback.type = "danger";
					$scope.feedback.message = "FEEDBACK_SERVER_ERROR";
					if (error) {
						$scope.feedback.detail = error;
					}
					console.error("loadNotifiche  Error", error);
				}
			);
		};

		$scope.getPazienti = function(optionalSelected, cf, cognome, nome) {
			$scope.loading = true;
			$scope.loadingPazienti = true;
			$scope.feedback = {};
			$scope.soggetti = [];
			$scope.soggettoDaSearchPressed = true;

			var search = {
				nome: nome,
				cognome: cognome,
				//cognomeexact: cognome,
				codiceFiscale: cf,
			}

			console.log("getPazienti - search: ", search);


			gestionepazientiService.searchPazienti(search).then(
				function (result) {
					console.log("searchPazienti", result);
					$scope.loading = false;
					$scope.loadingPazienti = false;
					if (result && result.data && result.data.length) {
						$scope.soggetti = result.data;
						if(optionalSelected && optionalSelected.idSoggetto){
							var soggSelezionato = $scope.soggetti.find(s => s.idSoggetto = optionalSelected.idSoggetto);
							$scope.soggettoA = soggSelezionato;
							//$scope.ngModelSoggettoASelected(optionalSelected);
						}
						
					}
					//Helpers.util.scrollTo();
				},
				function (error) {
					$scope.loading = false;
					$scope.loadingPazienti = false;
					$scope.feedback.type = "warning";
					$scope.feedback.message = "Impossibile trovare pazienti";
					if (error) {
						$scope.feedback.detail = "Controllare di aver inserito i parametri di ricerca Codice fiscale o Cognome";
					}
					console.error("searchPazienti  Error", error);
				}
			);
		}

		

		getTipiContatto();
		//$scope.getPazienti();


		$scope.selectTipoContatto = function (tipoContatto) {
			$scope.idTipoContatto = tipoContatto;		
		};

		$scope.selectPazienteA = function (sa) {
			console.log('idSoggettoA: ',sa);
			$scope.soggettoA = sa;
		};

		//$scope.filterSoggetti = function(value, index, listSoggetti) {
		//	console.log('filterSoggetti: ', index,value,listSoggetti);
		//	return listaSoggetti;
		//};

		$scope.comparatorSogg = function(actual, filter) {
			//console.log('comparatorSogg (false)', actual, filter);

			if(!filter || !filter.split){
				return false;
			}

			if(!actual || !actual.idTipoSoggetto || typeof actual == 'string' || typeof actual == 'number' || typeof actual == 'boolean') {
				return false;
			}

			var words = filter.split(' ');

			for(var i = 0; i < words.length; i++){
				var word = words[i]

				//var ok = JSON.stringify(actual).toLowerCase().includes(word.toLowerCase());

				var ok = (actual.nome             && actual.nome.toLowerCase().includes(word.toLowerCase())) 
						 || (actual.cognome       && actual.cognome.toLowerCase().includes(word.toLowerCase()))
						 || (actual.codiceFiscale && actual.codiceFiscale.toLowerCase().includes(word.toLowerCase()));

				if(!ok){
					return false;
				}
			}

			return true;
		}
		

		$scope.salvaContatto = function(){

			var contatto = 
			{
				'contattoTipoId': $scope.idTipoContatto,
				'idSoggettoDa': $scope.paziente.idSoggetto,
				'idSoggettoA': $scope.soggettoA && $scope.soggettoA.idSoggetto,
				'noteContatto': $scope.noteContatto,
				//'idDecorso': 53046,
				'utenteOperazione': $rootScope.currentUser.cfUtente,
			};

			gestionepazientiService.postContatto(contatto).then(
				function (result) {
					console.log("postContatto", result);
					$scope.loading = false;
					
					//Helpers.util.scrollTo();
					$uibModalInstance.close('ok');
				},
				function (error) {
					$scope.loading = false;
					console.error("postContatto  Error", error);
					var e = error && error.data;

					if(!e || !e.status || e.status == 500) {
						$scope.feedback.type = "danger";
						$scope.feedback.message = "FEEDBACK_SERVER_ERROR";
						if (error) {
							$scope.feedback.detail = error;
						}
					} else if(e.status == 400) {
						$scope.feedback.type = "warning";
						$scope.feedback.message = "Parametro errato";
						if (error) {
							$scope.feedback.detail = e.title;
						}
					}
				}
			);
		};

		$scope.cancel = function () { $uibModalInstance.dismiss('cancel'); };

		$scope.showInserisciSoggettoModal = function () {
			var newPazienteModal = $uibModal.open({
				templateUrl: 'partials/pazienti/edit.html', controller: 'NewPazienteModalCtrl', backdrop: 'static',
				resolve: {
					idPaziente: function () { return null; },
				}
			});

			newPazienteModal.result.then(function (paziente) {
				console.log("reaload lista pazienti!", paziente);
				$scope.getPazienti(paziente, paziente.codiceFiscale, paziente.cognome, paziente.nome);
			}, function () { 
                console.log("closed modal err!");
			});
		};
	}]);