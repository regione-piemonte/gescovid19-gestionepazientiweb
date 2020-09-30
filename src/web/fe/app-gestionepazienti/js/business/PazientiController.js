appControllers.controller('PazientiCtrl', [ '$scope', 'gestionepazientiService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$routeParams',
    function($scope, gestionepazientiService,$translate, $location, $uibModal,$rootScope,sessionStorage,$routeParams) {
		console.log("Pazienti  Controller");

		var isSearch = $routeParams.listtype == 'search'
			
		console.log("$routeParams",$routeParams);
		
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

		$scope.pazienteDomicilioComuneFilter = function (paziente) {
			var keyword = new RegExp($scope.filter.domicilioComuneFilter, 'i');
			
			return !$scope.filter.domicilioComuneFilter || (paziente.comuneDomicilio && paziente.comuneDomicilio.nomeComune && keyword.test(paziente.comuneDomicilio.nomeComune));
			
			};
		$scope.pazienteTipoSoggettoFilter = function (paziente) {
			if (!$scope.filter.tipoSoggettoFilter || $scope.filter.tipoSoggettoFilter == -2){
				return true;
			}
			if(!paziente.idTipoSoggetto ){
				paziente.idTipoSoggetto= -1;
			}
			return !$scope.filter.tipoSoggettoFilter || (paziente.idTipoSoggetto == $scope.filter.tipoSoggettoFilter );

			
			};

		$scope.filter.tipiDecorso= {'ASSEGNAZIONE_DOMICILIO':false,
		'ISOLAMENTO_DOMICILIARE':false,
		'ISOLAMENTO_FIDUCIARIO':false,
		'DISPOSTA_QUARANTENA_DOMIC':false,
		'DISPOSTA_QUARANTENA_EXTRA_DOMIC':false,
		'POST_RICOVERO':false,
		'GUARITO': false,
		'DECEDUTO': false}

		$scope.tipoDecorsoFilter = function(paziente) {
			var tipoDecorso = tipiDecorso["idTipoEvento_"+paziente.ultimoIdTipoEvento];
			if(tipoDecorso == 'DISPOSTA_QUARANTENA_DOMIC'){
				tipoDecorso = 'ISOLAMENTO_DOMICILIARE';
			} else if(tipoDecorso == 'DISPOSTA_QUARANTENA_EXTRA_DOMIC') {
				tipoDecorso = 'ASSEGNAZIONE_DOMICILIO'
			}

			var allNotSelected = true;
			for (var k in $scope.filter.tipiDecorso) {
				if($scope.filter.tipiDecorso.hasOwnProperty(k)){
					if($scope.filter.tipiDecorso[k])
						allNotSelected = false;
				}
			}
			var filtroAttivo = $scope.filter.tipiDecorso[tipoDecorso];
			return (filtroAttivo || allNotSelected);
		};

		$scope.tipoDecorsoSelected = function(){
			var tipoDecorsoSelected;
			for (var k in $scope.filter.tipiDecorso) {
				if($scope.filter.tipiDecorso.hasOwnProperty(k)){
					if($scope.filter.tipiDecorso[k]){
						tipoDecorsoSelected = k;
					}
				}
			}
			return tipoDecorsoSelected;
		}

		$scope.idTipoEventoSelected = function(){
			var tipoDecorsoSelected = $scope.tipoDecorsoSelected();
			for(var t in tipiDecorso){
				if(tipiDecorso.hasOwnProperty(t)){
					if(tipiDecorso[t] == tipoDecorsoSelected){
						return t.replace("idTipoEvento_", "");
					}
				}
			}
		}

		$scope.selectTipoDecorso = function(tipoDecorso){
			console.log("tipo", tipoDecorso);
			for (var k in $scope.filter.tipiDecorso) {
				if($scope.filter.tipiDecorso.hasOwnProperty(k)){
					if(tipoDecorso!=k)
						$scope.filter.tipiDecorso[k]= false;
				}
			}
			console.log("$scope.filter.",$scope.filter);
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
		var tipiDecorso = Constants.TIPI_DECORSO;
		
//		$scope.decorsoIcon = function(idTipoEvento){
//			var res = "";
//			if(idTipoEvento){
//				var tipoDecorso = tipiDecorso["idTipoEvento_"+idTipoEvento]
//				if(tipoDecorso == 'ISOLAMENTO_DOMICILIARE'){
//					res = 'fa-lg fa fa-home icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso; 
//				}
//				else if(tipoDecorso == 'POST_RICOVERO'){
//					res = 'fa-lg fa fa-home  icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso; 
//				}
//				else if(tipoDecorso == 'ASSEGNAZIONE_DOMICILIO'){
//					res = 'fa-lg fa fa-building icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso; 
//				}
//				//else 
//				//	res = 'fa-lg fa fa-building icon-tipo-decorso icon-tipo-decorso-ASSEGNAZIONE_DOMICILIO'; 
//			}
//			return res;
//		};
		$scope.decorsoIcon = function(idTipoEvento){
			var res = "";
			if(idTipoEvento){
				var tipoDecorso = tipiDecorso["idTipoEvento_"+idTipoEvento]
				if(tipoDecorso == 'ISOLAMENTO_DOMICILIARE'){
					res = '<i class="fa-lg fa fa-home icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
				else if(tipoDecorso == 'ISOLAMENTO_FIDUCIARIO'){
					res = '<i class="fa-lg fa fa-home  icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
				else if(tipoDecorso == 'DISPOSTA_QUARANTENA_DOMIC'){
					res = '<i class="fa-lg fa fa-home  icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
				else if(tipoDecorso == 'DISPOSTA_QUARANTENA_EXTRA_DOMIC'){
					res = '<i class="fa-lg fa fa-home  icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
				else if(tipoDecorso == 'POST_RICOVERO'){
					res = '<i class="fa-lg fa fa-home  icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
				else if(tipoDecorso == 'ASSEGNAZIONE_DOMICILIO'){
					res = '<i class="fa-lg fa fa-building icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
				else if(tipoDecorso == 'STRUTTURA'){
					res = '<div class="icon-hospital"><i class="fa-lg fa fa-building-o icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>\
					<small><i class="fa fa-plus icon-hospital-cross"></i></small>	</div>'; 
				}
				else if(tipoDecorso == 'DECEDUTO'){
					res = '<div class="icon-deceduto">D</div>'; 
				}
				else if(tipoDecorso == 'GUARITO'){
					res = '<div class="icon-guarito">G</div>'; 
				}
				else if (tipoDecorso == 'SEGNALAZIONE'){
					res = '<i class="fa-lg fa fa-exclamation-circle icon-tipo-decorso icon-tipo-decorso-'+tipoDecorso+'"></i>'; 
				}
			}
			return res;
		};

			$scope.tipisoggetto;

      var loadTipisoggetto = function () {
        console.log("loadTipisoggetto");
        $scope.feedback = {};
        gestionepazientiService.loadTipisoggetto().then(
          function (result) {
            console.log("loadTipisoggetto result", result);
            if (result && result.data) {
              $scope.tipisoggetto = result.data;
              $scope.tipisoggetto.unshift(
                { idTipoSoggetto: -1, descTipoSoggetto: "cittadino" },
                { idTipoSoggetto: -2, descTipoSoggetto: "Tipo paziente" }
              );
              $scope.filter.tipoSoggettoFilter = -2;
            } else {
              $scope.feedback.type = "error";
              $scope.feedback.message = "FEEDBACK_SERVER_ERROR";
              console.log("no data");
            }
          },
          function (error) {
            $scope.feedback.type = "danger";
            $scope.feedback.message = "FEEDBACK_SERVER_ERROR";
            if (error) $scope.feedback.detail = error;
            console.error("loadTipisoggetto  Error", error);
          }
        );
      };

      loadTipisoggetto();

		var loadPazienti = function(){			
			$scope.loading = true;
			$scope.feedback = {};
			gestionepazientiService.loadPazienti().then(function(result){
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
			if($scope.statusFilter) 
				filterToSave.statusFilter=$scope.statusFilter;
			if($scope.typeFilter) 
				filterToSave.typeFilter=$scope.typeFilter;
			if($scope.orgFilter) 
				filterToSave.orgFilter=$scope.orgFilter;
			
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
			else
				$location.path("pazienti/view/" + $scope.selectedPaziente.idSoggetto);
		};

		$scope.showDetail = function(idPaziente){
			$location.path("pazienti/view/" + idPaziente).search({'selectedTab': 'pazienteDetail'});
		};
		
		if(!isSearch)
			loadPazienti();
		
		$scope.search = {input:{}};
		
		//var cfPattern = /^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$/;

		$scope.subimtsearch = function(){
			$scope.feedback = {};
			console.log("subimtsearch");
			if(!$scope.search.input.codiceFiscale && !$scope.search.input.cognome){
				$scope.feedback.type = 'warning';
				$scope.feedback.message = 'FEEDBACK_SEARCH_PARAM_REQUIRED';
			}
			else if($scope.search.input.codiceFiscale && !Helpers.util.isValidCF($scope.search.input.codiceFiscale) /* $scope.search.input.codiceFiscale.search(cfPattern) == -1 */){
				$scope.feedback.type = 'warning';
				$scope.feedback.message = 'FEEDBACK_SEARCH_PARAM_CF';
			}
			else{
				$scope.search.submitted = true;

				gestionepazientiService.searchPazienti($scope.search.input).then(function(result){
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
						$scope.pazienti = new Array();
						$scope.feedback.type = 'info';
						$scope.feedback.message = 'FEEDBACK_SEARCH_NO_PAZIENTI';
						console.log("no data");
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
		};
		
		$scope.newsearch = function(){
			$scope.feedback = {};
			$scope.pazienti = null;
			$scope.search.input = {};
			$scope.search.submitted = false;
		}
		
		$scope.showNewPazienteModal = function(){
    		var newPazienteModal = $uibModal.open({templateUrl: 'partials/pazienti/edit.html',controller: 'NewPazienteModalCtrl', backdrop: 'static',
    			resolve: {
	    	    	  idPaziente: function () {return null;},
	    	      	}});
	    		
    		newPazienteModal.result.then(function (paziente) {
    			Helpers.util.scrollTo();
    			$location.path('pazienti').search({'t': new Date().getTime()});
	    	   }, function () {});
	    };
	    
//		$scope.showEditPazienteModal = function(idPaziente){
//    		var newPazienteModal = $uibModal.open({
//	    		  templateUrl: 'partials/paziente/edit.html',//'newPazienteModal.html',
//	    	      controller: 'EditPazienteModalCtrl',
//	    	      size: 'lg',
//	    	      backdrop: 'static',
//	    	      resolve: {
//	    	    	  idPaziente: function () {return idPaziente;},
//	    	      	}
//	        	});
//	    		
//    		newPazienteModal.result.then(function () {
//    			$location.path('pazienti').search({'t': new Date().getTime()});
//	    	   }, function () {});
//	    };
	    
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
					  console.log("pazienteToDelete",paziente);
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
	
}]);

appControllers.controller('NewPazienteModalCtrl', [ '$scope', '$uibModalInstance', 'gestionepazientiService', 'toastService', 'idPaziente', '$uibModal','devService', '$location',
    function($scope, $uibModalInstance, gestionepazientiService, toastService,idPaziente,$uibModal,devService,  $location) {

	var cfPattern = /^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$/;
	var mobileItaliaPattern = /^(\((00|\+)39\)|(00|\+)39)?(38[890]|34[7-90]|36[680]|33[3-90]|32[89])\d{7}$/;
	var precMobilePattern = /^([1-9][1-90][1-90])\d{7}$/;
	var mobilePattern = /^((\+|00)([1-9][1-9]))?([1-9])\d{7,9}$/;
	var digitPattern  = /^\d+$/;





	$scope.paziente = {};
	var loadPaziente = function(){
		if(idPaziente){
			$scope.loading = true;
			gestionepazientiService.loadPaziente(idPaziente).then(function(result){
				console.log("loadPaziente", result);
				$scope.loading  = false;

				if(result && result.data){
					preparaPazienteCaricato(result.data);
					$scope.paziente.idSoggetto =  result.data.idSoggetto;
					$scope.modifica = {enable:true};
					$scope.ricercaaura.eseguita = true;

//					if(result.data.dataNascitaStr){
//						var dateToken = result.data.dataNascitaStr.split("/");
//						if(dateToken.length != 3){ // 1938-06-29 00:00:00
//							dateToken = result.data.dataNascitaStr.split(" ")[0].split("-");
//						}
//						
//						var dateFormatted  = "";
//						if(dateToken[0].length==4) // YYYY MM DD
//							dateFormatted = dateToken[1] +"/" + dateToken[2] + "/" + dateToken[0];
//						else  // DD MM YYYY
//							dateFormatted = dateToken[1] +"/" + dateToken[0] + "/" + dateToken[2];
//						
//						result.data.dataNascitaStr = new Date(dateFormatted);	
//					}
//					if(result.data.comuneResidenzaIstat)
//						result.data.comuneResidenzaIstatCompleto = result.data.comuneResidenza;
//					if(result.data.comuneDomicilioIstat)
//						result.data.comuneDomicilioIstatCompleto = result.data.comuneDomicilio;
//					
//					$scope.paziente = result.data;
					
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
	
	var preparaPazienteCaricato  = function(paziente){
//		if(paziente.dataNascitaStr){
//			var dateToken = paziente.dataNascitaStr.split("/");
//			if(dateToken.length != 3){ // 1938-06-29 00:00:00
//				dateToken = paziente.dataNascitaStr.split(" ")[0].split("-");
//			}
//			
//			var dateFormatted  = "";
//			if(dateToken[0].length==4) // YYYY MM DD
//				dateFormatted = dateToken[1] +"/" + dateToken[2] + "/" + dateToken[0];
//			else  // DD MM YYYY
//				dateFormatted = dateToken[1] +"/" + dateToken[0] + "/" + dateToken[2];
//			
//			paziente.dataNascitaStr = new Date(dateFormatted);	
//		}
		if(paziente.dataNascita)
			paziente.dataNascita = new Date(paziente.dataNascita);	
		if(paziente.comuneResidenzaIstat)
			paziente.comuneResidenzaIstatCompleto = paziente.comuneResidenza;
		if(paziente.comuneDomicilioIstat)
			paziente.comuneDomicilioIstatCompleto = paziente.comuneDomicilio;
		if(!$scope.paziente)
			$scope.paziente = {};
		if(paziente.tipoSoggetto)
			$scope.paziente.idTipoSoggetto = paziente.tipoSoggetto.idTipoSoggetto;
		$scope.paziente.codiceFiscale = paziente.codiceFiscale;
		$scope.paziente.idAsr = paziente.idAsr;
		$scope.paziente.cognome = paziente.cognome;
		$scope.paziente.nome = paziente.nome;
		$scope.paziente.dataNascita = paziente.dataNascita;
		$scope.paziente.comuneResidenzaIstat = paziente.comuneResidenzaIstat;
		$scope.paziente.comuneDomicilioIstat = paziente.comuneDomicilioIstat;
		$scope.paziente.indirizzoDomicilio = paziente.indirizzoDomicilio;
		$scope.paziente.telefonoRecapito = paziente.telefonoRecapito;
		$scope.paziente.email = paziente.email;
		$scope.paziente.asr = paziente.asr;
		$scope.paziente.comuneResidenzaIstatCompleto = paziente.comuneResidenzaIstatCompleto;
		$scope.paziente.comuneDomicilioIstatCompleto = paziente.comuneDomicilioIstatCompleto;
		$scope.paziente.numeroCampioni = paziente.numeroCampioni;
		$scope.paziente.numeroDecorsi = paziente.numeroDecorsi;
		$scope.paziente.aslDomicilio = paziente.aslDomicilio;
		$scope.paziente.aslResidenza = paziente.aslResidenza;
		$scope.paziente.idAura = paziente.idAura;
		
		
	};
	
	
	var getComuneDaIstat = function(istat){
		var comune = null;
		if($scope.comuni){
			for(var i=0; i<$scope.comuni.length; i++){
				if($scope.comuni[i].istatComune == istat){
					comune = $scope.comuni[i];
					break;
				}
			}
		}
		return comune;
			
	};

	loadPaziente();
	$scope.cfAuraFeedback = {};
	$scope.cfAuraloading  = false;
	$scope.ricercaaura = {};
	$scope.modifica = {enable:false};
	
	$scope.auraSearchByCF = function(){
		$scope.cfAuraFeedback = {};
		$scope.cognomeAuraFeedback = {};
		if(!$scope.paziente.codiceFiscale || !Helpers.util.isValidCF($scope.paziente.codiceFiscale)){
			$scope.cfAuraFeedback.type = 'warning';
			$scope.cfAuraFeedback.message = 'FEEDBACK_AURA_SEARCH_CF_INVALID_WARNING';
		}
		else{

			$scope.cfAuraloading  = true;
			$scope.ricercaaura = {};

			gestionepazientiService.auraSearchByCf($scope.paziente.codiceFiscale).then(function(result){
				console.log("auraSearchByCf", result);
				$scope.cfAuraloading  = false;
				$scope.ricercaaura.eseguita = true;

	
				if(result && result.data && result.data.length>0){
					elaboraRisultatoAura(result.data);
				}
				else{
					$scope.ricercaaura.esito = 'ko';
					$scope.cfAuraFeedback.type = 'warning';
					$scope.cfAuraFeedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
				}
	
			}, function(error){
				console.error("loadPaziente  Error", error);
				$scope.ricercaaura.eseguita = true;
				$scope.cfAuraloading  = false;
				$scope.cfAuraFeedback.type = 'warning';
				$scope.cfAuraFeedback.message = 'FEEDBACK_AURA_SERVER_ERROR';
				$scope.cfAuraFeedback.detail = 'Riprovare fra qualche minuto, o inserire i dati';
			});
		}
	
	};
	
	$scope.cognomeAuraFeedback = {};
	$scope.cognomeAuraloading  = false;
	$scope.auraSearchByCognome = function(){
		$scope.cfAuraFeedback = {};
		$scope.cognomeAuraFeedback = {};
		if(!$scope.paziente.cognome || !$scope.paziente.nome || !$scope.paziente.dataNascita){
			$scope.cognomeAuraFeedback.type = 'warning';
			$scope.cognomeAuraFeedback.message = 'FEEDBACK_AURA_SEARCH_COGNOME_INVALID_WARNING';
		}
		else{

			$scope.cognomeAuraloading  = true;
			$scope.ricercaaura = {};
			gestionepazientiService.auraSearchByCognome($scope.paziente.nome, $scope.paziente.cognome, $scope.paziente.dataNascita).then(function(result){
				console.log("auraSearchByCognome", result);
				$scope.ricercaaura.eseguita = true;

				$scope.cognomeAuraloading  = false;
	
				if(result && result.data && result.data.length>0){
					elaboraRisultatoAura(result.data);
				}
				else{
					$scope.ricercaaura.esito = 'ko';
					$scope.cognomeAuraFeedback.type = 'warning';
					$scope.cognomeAuraFeedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
				}
	
			}, function(error){
				console.error("loadPaziente  Error", error);
				$scope.ricercaaura.eseguita = true;
				$scope.cognomeAuraloading  = false;
				$scope.cognomeAuraFeedback.type = 'warning';
				$scope.cognomeAuraFeedback.message = 'FEEDBACK_AURA_SERVER_ERROR';
				$scope.cognomeAuraFeedback.detail = 'Riprovare fra qualche minuto, o inserire i dati';
			});
		}
	
	};
	
	$scope.debug = function(){
		console.log("$scope.paziente", $scope.paziente);
	};
	
	
	var elaboraRisultatoAura = function(soggettiAura){
		//soggettiAura.push(soggettiAura[0]);
		if( soggettiAura.length==1 ){
			caricaDettaglioPazienteDaAura(soggettiAura[0].profiloAnagrafico);
		}
		else{
    		var newSoggettiAuraModal = $uibModal.open({
    			templateUrl: 'partials/pazienti/soggettiAura.html',
    			controller: 'SoggettiAuraModalCtrl', 
    			backdrop: 'static',
    			size:'lg',
    			resolve: {
	    	    	  soggettiAura: function () {return soggettiAura;},
	    	     },});
	    		
    		newSoggettiAuraModal.result.then(function (soggettoAura) {
    			caricaDettaglioPazienteDaAura(soggettoAura.profiloAnagrafico);
	    	   }, function () {});

		}
	};
	
	var caricaDettaglioPazienteDaAura = function(profiloAnagraficoAura){
		$scope.cfAuraloading  = true;
		gestionepazientiService.auraSearchByIdSoggetto(profiloAnagraficoAura).then(function(result){
			console.log("caricaDettaglioPazienteDaAura", result);
			$scope.cfAuraloading  = false;

			if(result && result.data ){
				preparaPazienteCaricato(result.data);
				$scope.paziente.idAura=profiloAnagraficoAura;
			}
			else{
				$scope.ricercaaura.esito = 'ko';
				$scope.cfAuraFeedback.type = 'warning';
				$scope.cfAuraFeedback.message = 'FEEDBACK_PAZIENTE_NOT_FOUND';
			}

		}, function(error){
			console.error("loadPaziente  Error", error);
			$scope.ricercaaura.eseguita = true;
			
			$scope.cfAuraloading  = false;
			$scope.cfAuraFeedback.type = 'danger';
			$scope.cfAuraFeedback.message = 'FEEDBACK_SERVER_ERROR';
		});
	}
	

	var validatePaziente = function(){
		var result = new Array();
		
		if(!$scope.paziente.cognome){
			result.push("Cognome paziente obbligatorio");
		}
		if(!$scope.paziente.nome){
			result.push("Nome paziente obbligatorio");
		}
		if(!$scope.paziente.dataNascita){
			result.push("Data di nascita paziente obbligatoria");
		}
		else if($scope.paziente.dataNascita.getTime() > new Date().getTime()){
			result.push("Data di nascita paziente futura");
		}
		if($scope.paziente.codiceFiscale){
			if (!Helpers.util.isValidCF($scope.paziente.codiceFiscale))
				result.push("Codice fiscale non formattato correttamente");
		}
		//if(!$scope.paziente.telefonoRecapito){
		//	result.push("Telefono paziente obbligatorio");
		//}
		// else if($scope.paziente.telefonoRecapito.search(mobilePattern) == -1)
		if($scope.paziente.telefonoRecapito && $scope.paziente.telefonoRecapito.search(digitPattern) == -1)
			result.push("Telefono cellulare non formattato correttamente");
//		else if(!Number.isInteger($scope.paziente.telefonoRecapito)){
//			result.push("Telefono paziente non valido. Usare solo numeri.");
//		}
		
		return result;
	};
	
	$scope.savePaziente= function(closeModal){
		submitted = true;
			$scope.feedback = {};
			
			
			$scope.paziente.comuneResidenzaIstatCompleto
			if($scope.paziente.comuneResidenzaIstatCompleto)
				$scope.paziente.comuneResidenzaIstat = $scope.paziente.comuneResidenzaIstatCompleto.istatComune;
			else
				$scope.paziente.comuneResidenzaIstat = null;

			$scope.paziente.comuneDomicilioIstatCompleto
			if($scope.paziente.comuneDomicilioIstatCompleto)
				$scope.paziente.comuneDomicilioIstat = $scope.paziente.comuneDomicilioIstatCompleto.istatComune;
			else
				$scope.paziente.comuneDomicilioIstat = null;
			if($scope.paziente.idTipoSoggetto==-1)
				$scope.paziente.idTipoSoggetto=null;
			var validateMessageDetail  =validatePaziente();
			if(validateMessageDetail.length==0){
				$scope.loading = true;
				
				gestionepazientiService.savePaziente($scope.paziente).then(function(result){
					if(closeModal){
						if(idPaziente)
							toastService.setToast("success","FEEDBACK_EDIT_PAZIENTE_SAVED");
						else
							toastService.setToast("success","FEEDBACK_NEW_PAZIENTE_SAVED");
						$uibModalInstance.close(result.data);
						$scope.loading = false;
					}
					else{
						if(idPaziente)
							$scope.feedback.message = 'FEEDBACK_EDIT_PAZIENTE_SAVED';
						else
							$scope.feedback.message = 'FEEDBACK_NEW_PAZIENTE_SAVED';
						$scope.feedback.type = 'success';
					}
						
				}, function(error){
					$scope.loading = false;
					if(error && error.status==409){
						var omonimi = error.data;
						if(error.data && error.data.length>1){
							$scope.feedback.type = 'danger';
							$scope.feedback.message = 'FEEDBACK_PAZIENTE_DUPLICATO_MULTI';
						}
						else{
							
							var duplicateSoggettoModal = $uibModal.open({
								animation : true,
								templateUrl : 'confirmDialog.html',
								controller : 'ConfirmDialogCtrl',
								backdrop  : 'static',
								resolve: { 
									question: function () {
										return {"title":"FEEDBACK_PAZIENTE_DUPLICATO_TITLE","message":"FEEDBACK_PAZIENTE_DUPLICATO_MESSAGE"};
									}
								}
							});
	
							duplicateSoggettoModal.result.then(function() {
									$uibModalInstance.dismiss('cancel');
									$location.path("pazienti/view/" + omonimi[0].idSoggetto).search({'selectedTab': 'pazienteDetail'});
								}, function() {});
							
						}						
					}else if(error && error.status==410){
						$scope.feedback.type = 'danger';
						$scope.feedback.message = 'FEEDBACK_PAZIENTE_CF_PRESENTE';
					}
					else{
						$scope.feedback.type = 'danger';
						$scope.feedback.message = 'FEEDBACK_SERVER_ERROR';
						if(error)
							$scope.feedback.detail = error;
					}
					console.error("savePaziente  Error", error)
				});
			}else{
				$scope.feedback.type = 'warning';
				$scope.feedback.message = 'FEEDBACK_NEW_PAZIENTE_INVALID';
				$scope.feedback.details= validateMessageDetail;

			}
				
		}
	
	var loadComuni = function(){
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
	
	loadComuni();
	
	$scope.allAsl  = new Array();
	
	var loadAsl = function(){
		console.log("loadAsl");
		$scope.feedback = {};
		gestionepazientiService.loadAsl().then(function(result){
			console.log("loadAsl result", result);
			if(result && result.data){
				for (var i = 0; i < result.data.length; i++) {
					if(result.data[i].descRegione=='PIEMONTE')
						$scope.allAsl.push(result.data[i].descAslEstesa);
				}
				$scope.allAsl.push('EXTRA-REGIONE');
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
	
	$scope.tipisoggetto;
	
	
	var loadTipisoggetto = function(){
		console.log("loadTipisoggetto");
		$scope.feedback = {};
		gestionepazientiService.loadTipisoggetto().then(function(result){
			console.log("loadTipisoggetto result", result);
			if(result && result.data){
				$scope.tipisoggetto = result.data;
				$scope.tipisoggetto.unshift({idTipoSoggetto: -1, descTipoSoggetto: 'cittadino'})
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
			console.error("loadTipisoggetto  Error", error)
		});

	}
	
	loadTipisoggetto();

	$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);



appControllers.controller('SoggettiAuraModalCtrl', [ '$scope', '$uibModalInstance', 'soggettiAura', 
	function($scope, $uibModalInstance, soggettiAura) {
		$scope.soggettiAura = soggettiAura;
		$scope.selected = {};
		$scope.selectSoggetto = function(soggettoIndex){
			if(soggettoIndex)
				$scope.selected.indexSoggetto = soggettoIndex;
			$uibModalInstance.close(soggettiAura[$scope.selected.indexSoggetto]);
		};
		
		$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);



