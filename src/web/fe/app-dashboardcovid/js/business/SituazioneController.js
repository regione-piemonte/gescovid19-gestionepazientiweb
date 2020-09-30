appControllers.controller('SituazioneCtrl', [ '$scope', 'dashboardcovidService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$filter',
    function($scope, dashboardcovidService,$translate, $location, $uibModal,$rootScope,sessionStorage,$filter) {
		console.log("Situazione  Controller");
		//$rootScope.breadcrumbItems = [{"label":$translate.instant("PROJECTS"), "title": $translate.instant("PROJECTS"), link: "#/pazienti"}];
		$scope.filterDisponibilita = {};
		$scope.loading  = false;
		$scope.tamponi = new Array();
		$scope.filtered = {currentPage:1};
		$scope.pageSize = 10;
		$scope.order = {};
		$scope.filter = {showOnlyNoInfo:false};
		$scope.vista = {situazione:"aggregati"};
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
//								if(!result.data[i].postiDisponibili){
//									result.data[i].postiDisponibili = parseInt(Math.random() * (30) + 10);
//									result.data[i].postiOccupati = parseInt(Math.random() * (result.data[i].postiDisponibili-result.data[i].postiDisponibili/10) +result.data[i].postiDisponibili/10);
//									
//								}
								
								
								
								var disponibili = result.data[i].postiDisponibili;
								var occupati = result.data[i].postiOccupati;
								if(disponibili>0){
									var percent = parseInt(100*occupati/disponibili);
									result.data[i].fullSize = percent;
									result.data[i].color = "#9ade00";
									if(percent>80){
										result.data[i].color = "#ff4141";
										result.data[i].style='color: #ff4141;font-weight:bold;'
									}
									else if(percent>70){
										result.data[i].color = "#ff9900";
										result.data[i].style='color: #ff9900;font-weight:bold;'										
									}
									else if(percent>60){
										result.data[i].color = "#f0db2e";
										result.data[i].style='color: #f0db2e;font-weight:bold;'										
									}
									else{
										result.data[i].color = "#9ade00";
										//result.data[i].style='color: #9ade00;font-weight:bold;'																				
									}
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
		
		$scope.tipi_aree = ['TER_INT','TER_SEMI_INT','NIV','PNEUMO', 'INFETT','MED_INT', 'IN_ATTESA', 'ALTRO'];
		var loadDisponibilitaTransponded= function(){		
			console.log("loadDisponibilita")
			$scope.loading = true;
			$scope.feedback = {};
			dashboardcovidService.loadDisponibilita(true).then(function(result){
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
//								if(!da.disponibilita.postiDisponibili){
//									da.disponibilita.postiDisponibili = parseInt(Math.random() * (30) + 10);
//									da.disponibilita.postiOccupati = parseInt(Math.random() * (da.disponibilita.postiDisponibili-da.disponibilita.postiDisponibili/10) +da.disponibilita.postiDisponibili/10);
//									
//								}
								dMap[row.ente.nome][row.struttura.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
								dMap[row.ente.nome][row.struttura.nome].totaleOccupati += da.disponibilita.postiOccupati;
								var percent = parseInt(100*da.disponibilita.postiOccupati/da.disponibilita.postiDisponibili);
								if(percent>80)
									da.disponibilita.style = "color: #ff4141;font-weight:bold;";
								else if(percent>70)
									da.disponibilita.style = "color: #ff9900;font-weight:bold;";
								else if(percent>60)
									da.disponibilita.style = "color: #f0db2e;font-weight:bold;";
//								else
//									da.disponibilita.color = "#9ade00";
								dMap[row.ente.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
								dMap[row.ente.nome].totaleOccupati += da.disponibilita.postiOccupati;
								
							}

							
							//console.log("row.disponibilitaArea",row.disponibilitaArea)
//								{
//										,
//										postiDisponibili: null
//										postiOccupati	
//								};
								
							
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
		
		


	    
	    
	
}]);

