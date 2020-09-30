appControllers.controller('MovimentazioneCtrl', [ '$scope', 'dashboardcovidService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$filter',
    function($scope, dashboardcovidService,$translate, $location, $uibModal,$rootScope,sessionStorage,$filter) {
		console.log("Movimentazione  Controller");
		//$rootScope.breadcrumbItems = [{"label":$translate.instant("PROJECTS"), "title": $translate.instant("PROJECTS"), link: "#/pazienti"}];
		$scope.filterDisponibilita = {};
		$scope.loading  = false;
		$scope.tamponi = new Array();
		$scope.filtered = {currentPage:1};
		$scope.pageSize = 10;
		$scope.order = {};
		$scope.filter = {showOnlyNoInfo:false};
//		
//		$scope.noInfoFilter = function(tampone) {
//			return (tampone.tamponeAutorizzato=="NI" && $scope.filter.showOnlyNoInfo) || !$scope.filter.showOnlyNoInfo;
//		};

		$scope.disponibilitaEnteFilter = function(disponibilita) {
			var keyword = new RegExp($scope.filter.ente, 'i');
			return !$scope.filter.ente || (disponibilita.ente && disponibilita.ente.nome && keyword.test(disponibilita.ente.nome ));
		};

		$scope.disponibilitaAreaFilter = function(disponibilita) {
			var keyword = new RegExp($scope.filter.area, 'i');
			return !$scope.filter.area || (disponibilita.area && disponibilita.area.nome && keyword.test(disponibilita.area.nome ));
		};
		
		$scope.disponibilitaStrutturaFilter = function(disponibilita) {
			var keyword = new RegExp($scope.filter.struttura, 'i');
			return !$scope.filter.struttura || (disponibilita.struttura && disponibilita.struttura.nome && keyword.test(disponibilita.struttura.nome ));
		};
		
		
		$scope.feedback = {};
	
		var loadDisponibilita = function(){		
			console.log("loadDisponibilita")
			$scope.loading = true;
			$scope.feedback = {};
			dashboardcovidService.loadDisponibilita().then(function(result){
				console.log("loadDisponibilita", result);
				if(result && result.data && result.data.length > 0){
					$scope.loading = false;

					$scope.disponibilita = new Array();
					
					if(result.data){
						for(var i=0;i<result.data.length;i++){
							if(!result.data[i].flagValido || result.data[i].flagValido=="SI" ){
								if(!result.data[i].postiDisponibili){
									result.data[i].postiDisponibili = parseInt(Math.random() * (30) + 10);
									result.data[i].postiOccupati = parseInt(Math.random() * (result.data[i].postiDisponibili-result.data[i].postiDisponibili/10) +result.data[i].postiDisponibili/10);
									
								}
								
								
								
								var disponibili = result.data[i].postiDisponibili;
								var occupati = result.data[i].postiOccupati;
								if(disponibili>0){
									var percent = parseInt(100*occupati/disponibili);
									result.data[i].fullSize = percent;
									result.data[i].color = "#9ade00";
									if(percent>80)
										result.data[i].color = "#ff4141";
									else if(percent>70)
									result.data[i].color = "#ff9900";
									else if(percent>60)
										result.data[i].color = "#f0db2e";
									else
										result.data[i].color = "#9ade00";
								}
								if(result.data[i].area && result.data[i].area.nome)
									result.data[i].area.nome = result.data[i].area.nome.split("-")[0];
								$scope.disponibilita.push(result.data[i]);
								
							}
						}
							
					}
					console.log("$scope.disponibilita", $scope.disponibilita);
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



		loadDisponibilita();
		


	    
	    
	
}]);

