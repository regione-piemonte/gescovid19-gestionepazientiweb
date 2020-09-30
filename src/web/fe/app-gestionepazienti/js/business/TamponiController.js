appControllers.controller('TamponiCtrl', [ '$scope', 'gestionepazientiService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$filter',
    function($scope, gestionepazientiService,$translate, $location, $uibModal,$rootScope,sessionStorage,$filter) {
		console.log("Tamponi  Controller");
		//$rootScope.breadcrumbItems = [{"label":$translate.instant("PROJECTS"), "title": $translate.instant("PROJECTS"), link: "#/pazienti"}];
		$scope.filterTampone = {};
		$scope.loading  = false;
		$scope.tamponi = new Array();
		$scope.filtered = {currentPage:1};
		$scope.pageSize = 10;
		$scope.order = {};
		$scope.filter = {showOnlyNoInfo:false};
		
		$scope.noInfoFilter = function(tampone) {
			return (tampone.tamponeAutorizzato=="NI" && $scope.filter.showOnlyNoInfo) || !$scope.filter.showOnlyNoInfo;
		};

		$scope.pazienteNomeFilter = function(paziente) {
			var keyword = new RegExp($scope.filter.nomeFilter, 'i');
			return !$scope.filter.nomeFilter || (paziente.nome && keyword.test(paziente.nome ));
		};
		
		$scope.pazienteCognomeFilter = function(paziente) {
			var keyword = new RegExp($scope.filter.cognomeFilter, 'i');
			return !$scope.filter.cognomeFilter || (paziente.cognome && keyword.test(paziente.cognome ));
		};
		
		$scope.tamponeAutorizzatoFilter = function(tampone) {
			var keyword = new RegExp($scope.filter.autorizzatoFilter, 'i');
			return !$scope.filter.autorizzatoFilter || (tampone.tamponeAutorizzato && keyword.test($filter('tamponeautorizzato')(tampone.tamponeAutorizzato) ));
		};
		
		$scope.tamponeEsitoFilter = function(tampone) {
			var keyword = new RegExp($scope.filter.esitoFilter, 'i');
			return !$scope.filter.esitoFilter || (tampone.esitoTampone && keyword.test(tampone.esitoTampone));
		};
		
		$scope.feedback = {};

		var loadTamponi = function(){		
			console.log("loadTamponi")
			$scope.loading = true;
			$scope.feedback = {};
			gestionepazientiService.loadRichiestetampone().then(function(result){
				console.log("loadTamponi", result);
				if(result && result.data && result.data.length > 0){
					$scope.loading = false;

					$scope.tamponi = result.data;
					
			
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
				console.error("loadTampone  Error", error)
			});
	
		}



		loadTamponi();
		

		$scope.showEditTamponeModal = function(idSoggetto, idTampone){
    		var newTamponeModal = $uibModal.open({templateUrl: 'partials/pazienti/edit_tampone.html',
    			controller: 'NuovoTamponeModalCtrl', 
    			size:'lg',
    			 resolve: {
	    	    	  idPaziente: function () {return idSoggetto;},
	    	    	  idTampone: function(){return idTampone;}
	    	      	},
    			backdrop: 'static',});
	    		
    		newTamponeModal.result.then(function () {
    			Helpers.util.scrollTo();
    			$location.path("richiestetamponi").search({'t': new Date().getTime()});
	    	   }, function () {});
	    };

	    
	    
	
}]);

