appControllers.controller('DashboardPostilettoCtrl', [ '$scope', 'dashboardcovidService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$filter','$timeout',
    function($scope, dashboardcovidService,$translate, $location, $uibModal,$rootScope,sessionStorage,$filter,$timeout) {
		console.log("Situazione  Controller");

		$scope.feedback = {};
	
		$scope.showEnte =function(p){
			console.log("showEnte", p);
			$location.path("dashboard/postiletto/ente/" + p.idEnte)
		};
		
		
		$scope.tipi_aree_attive = ['TER_INT','TER_SEMI_INT','PNEUMO', 'INFETT','MED_INT'];
		var disponibilita = new Array();
		var loadDisponibilitaTransponded= function(){		
			console.log("loadDisponibilita")
			$scope.loading = true;
			$scope.feedback = {};
			dashboardcovidService.loadDisponibilita(true).then(function(result){
				console.log("loadDisponibilita", result);
				if(result && result.data && result.data.length > 0){
					$scope.loading = false;

					if(result.data){
						disponibilita = result.data;
						var dMap = {}
						var sunburstDataTree = {}
						for(var i=0;i<result.data.length;i++){
							var row = result.data[i];
							if(!dMap[row.ente.nome]){
								dMap[row.ente.nome] =  {idEnte: row.ente.idEnte, totaleDisponibili:0, totaleOccupati:0, totPostiTarget:row.ente.totPostiTarget};
								sunburstDataTree[row.ente.nome] = {};
							}
							if(!dMap[row.ente.nome][row.struttura.nome]){
								dMap[row.ente.nome][row.struttura.nome] =  {totaleDisponibili:0, totaleOccupati:0};
								sunburstDataTree[row.ente.nome][row.struttura.nome]=new Array();
							}
							
							for(var j=0;j<row.disponibilitaArea.length;j++){
								var da =row.disponibilitaArea[j];

								
								dMap[row.ente.nome][row.struttura.nome][da.area.idDArea] = da.disponibilita;
								sunburstDataTree[row.ente.nome][row.struttura.nome].push({"name":da.area.idDArea, "size":da.disponibilita.postiDisponibili});
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

						}
						
						console.log("sunburstDataTree",sunburstDataTree);
						//var sunburstData = [{"name":"Totale", "children":new Array()}];
						//var sunburstData  =  prepareSunburst(sunburstDataTree);
						//console.log("sunburstData", sunburstData);
						//$scope.sunburstData =[sunburstData];
						
						
						var pieData = new Array();

						for (var k in dMap) {
							if(dMap.hasOwnProperty(k)){
								//console.log(dMap[k]);
								var disponibili = dMap[k].totaleDisponibili;
								var occupati = dMap[k].totaleOccupati;
		
								var percent = 0;
								var colors = getColoriSoglie();
								if(disponibili>0){
									var percent = parseInt(100*occupati/disponibili);
									colors = getColoriSoglie(percent);
									dMap[k].fullSize = percent;							
									dMap[k].color = colors[0];
								}
								pieData.push({idEnte: dMap[k].idEnte, name:k, percent: percent, postiTarget: dMap[k].totPostiTarget, 
									totaleDisponibili: dMap[k].totaleDisponibili,totaleOccupati: dMap[k].totaleOccupati,
									data:[
										{key:"Posti disponibili", y:100-percent, color:colors[1]},
										{key:"Posti Occupati", y: percent, color:colors[0]}
									]});
							}
						}
						
						$scope.horizontalBarData = preprepareHorizontalData();
						console.log("horizontalBarData ", $scope.horizontalBarData );
						
						$scope.disponibilita2 = dMap;
						$scope.pieData = pieData;
						prepareMapData();
						console.log("$scope.pieData", pieData);
						
						



					}
					console.log("$scope.disponibilita", $scope.disponibilita2);
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
		
//		var prepareSunburst = function(sunburstTree){
//			tree = {name: "Totale", children: new Array()};
//			
//			for (var t in sunburstTree) {
//				if(sunburstTree.hasOwnProperty(t)){
//					var node = {name: t, children: new Array()};
//					for (var t1 in sunburstTree[t]) {
//						if(sunburstTree[t].hasOwnProperty(t1)){
//							var node1 = {name: t1, children: sunburstTree[t][t1]};
//							node.children.push(node1);
//						}
//					}
//					tree.children.push(node);
//				}
//			}
//			return tree;
//			
//		}
		
		$scope.horizontalChart = {selected: 'all'};
		
		$scope.showHorizontalChart = function(area){
			console.log("showHorizontalChart", area);
			$scope.horizontalChart.selected = area;
			$scope.horizontalBarData = preprepareHorizontalData(area);
		}
		
		var preprepareHorizontalData = function(area){
			var dMap = {}
			for(var i=0;i<disponibilita.length;i++){
				var row = disponibilita[i];
				if(!dMap[row.ente.nome])
					dMap[row.ente.nome] =  {totaleDisponibili:0, totaleOccupati:0, totPostiTarget:row.ente.totPostiTarget};
				if(!dMap[row.ente.nome][row.struttura.nome]){
					dMap[row.ente.nome][row.struttura.nome] =  {totaleDisponibili:0, totaleOccupati:0};
				}
				
				for(var j=0;j<row.disponibilitaArea.length;j++){
					var da =row.disponibilitaArea[j];
					if(!area || area==da.area.idDArea){
						dMap[row.ente.nome][row.struttura.nome][da.area.idDArea] = da.disponibilita;
						dMap[row.ente.nome][row.struttura.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
						dMap[row.ente.nome][row.struttura.nome].totaleOccupati += da.disponibilita.postiOccupati;
						dMap[row.ente.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
						dMap[row.ente.nome].totaleOccupati += da.disponibilita.postiOccupati;
					}
				}
			}
			return prepareHorizontalBar(dMap);
		};
		
		var prepareHorizontalBar = function(dMap){
	           var data  = new Array();
	           var maxLabel = "";
	           for (var k in dMap) {
	        	   if(dMap.hasOwnProperty(k)){
	        		   var label = $filter('string_ellipse')(k,15);
	        		   var realvalue = dMap[k].totaleDisponibili-dMap[k].totaleOccupati; 
	        		   data.push({"label":label, "value":realvalue>0?realvalue:0, "labelfull":k, "realvalue":realvalue});
						if(label.length>maxLabel.length)
							maxLabel = label;
	        		   
	        	   }
	           }
	           
	           var fakeText = d3.select("#ente-label-fake").insert("svg").append("text").text(maxLabel);
			   $scope.horizontalBarOptions.chart.margin.left =  fakeText.node().getComputedTextLength()+10;
			   console.log("maxLabel",maxLabel, fakeText.node().getComputedTextLength());
			   d3.select("#ente-label-fake svg").remove();
	           	
	           return [{"key": "Totale","color": "#496684", 
	        	   "values":data.sort(function compare(a,b) {return a.value < b.value?1:-1;})}];

		};
		
		
		var getColoriSoglie = function(percent){
			var res = ["#ccc","#ddd"];
			if(percent){
				if(percent>80)
					res = ["#ff4141","#ffa8a8"];
				else if(percent>70)
					res = ["#ff9900","#ffd08a"];
				else if(percent>60)
					res =  ["#f0db2e","#f7eb8a"];
				else
					res =  ["#9ade00","#d1f08a"];
			}
			return res;
		}

		var getColoriSoglieMap  = function(percent){			
			var res = "#ccc"
			if(percent>80){
				var c = d3.scale.linear().domain([1,20]).range(["white", "#ff4141","black"]);
				res = c(percent-80);
			}
//			else if(percent>70){
//				var c = d3.scale.linear().domain([1,10]).range(["white", "#ff9900","black"]);
//				res = c(percent-70);
//			}
			else if(percent>60){
				var c = d3.scale.linear().domain([1,20]).range(["#f0db2e", "#ff9900","black"]);
				res = c(percent-60);				
			}
			else{
				var c = d3.scale.linear().domain([1,60]).range(["#e3f6b8", "#9ade00","#000000"]);
				res = c(60-percent);
			}
			return res;
		};
		
		loadDisponibilitaTransponded();
		
		$scope.pieOptions = {
            chart: {
                type: 'pieChart',
                height: 100,
                x: function(d){return d.key;},
                y: function(d){return d.y;},
                margin:{top:0, left:0, bottom: 0, right:0},
                duration: 5,
                showLegend: false,
                showLabels: false,
                pie:{"donut":true,"donutRatio":"0.6","cornerRadius":"3"},
                valueFormat: function(d){return  parseInt(d) + "%";}
            }
        };
		
		$scope.sunburstOptions ={
			chart: {
				type: 'sunburstChart',
                height: 450,
                //color: function(d){console.log(d); return "#ff4141"},
                duration: 250,
                sunburst: {
                	"donut":true,"donutRatio":"0.6","cornerRadius":"3",
                	 color: function(d,s){console.log(d,s); return "#ff4141"},
                }
			}
		};
		

		$scope.horizontalBarOptions = {
	            chart: {
	                type: 'multiBarHorizontalChart',
	                height: 450,
	                margin : {},
	                x: function(d){return d.label;},
	                y: function(d){return parseInt(d.value);},
	                valueFormat: function(d){return  parseInt(d);},
	                showValues: true,
	                duration: 500,
	                showLegend: false,
	                showControls: false,
	                xAxis: {
	                    showMaxMin: false
	                },
	                showYAxis: false,
	            }
	        };
		
		
		
		
		// map
		var mapDataAsr = new Array();
		var prepareMapData = function(){
			for (var i = 0; i < $scope.pieData.length; i++) {
				var p = $scope.pieData[i];
				mapDataAsr.push(
						{key:p.idEnte, 
							totaleDisponibili: p.totaleDisponibili, 
							totaleOccupati: p.totaleOccupati, 
							percent:p.percent, 
							color:getColoriSoglieMap(p.percent)});
			}
			createMap("asr", geojson_asr.url, mapLayerAsr);
			createMap("ao", geojson_ao.url, mapLayerAo);
			//createMap(mapLayerAsr);
			
		}

		var geoprojection = "mercator";

		var rgb2Hex =  function(rgb){
			if(rgb.indexOf("(")>=0)
				rgb = rgb.match(/\(([^)]+)\)/)[1];
			var component = rgb.split(",");
			return "#" + ((1 << 24) + (parseInt(component[0]) << 16) + (parseInt(component[1]) << 8) + parseInt(component[2])).toString(16).slice(1);
		};
		
		var hex2Color = function(hex){
			if(hex.charAt(0)=="#") 
				hex = hex.substring(1,7);
			var r = parseInt((hex).substring(0,2),16);
			var g = parseInt((hex).substring(2,4),16);
			var b = parseInt((hex).substring(4,6),16);
			return {r:r, g:g, b:b, opacity: 16};
		}

		var darker = function(colorHex, k) {
			var darker = 0.7;
			var  color = hex2Color(colorHex);
			k = k == null ? darker : Math.pow(darker, k);
			return rgb2Hex("(" + color.r * k + "," + color.g * k + "," + color.b * k + ")");
		}

		var projectionScale, projectionCenter, projectionTranslate;
		var fitGeojson = function(geojson,width,height, geoprojection){
			var c = d3.geo.centroid(geojson);
			// Create a unit projection.
			projection = d3.geo.mercator().scale(1).center(c).translate([0,0]);
			// Create a path generator.
			var path = d3.geo.path().projection(projection);
			// Compute the bounds of a feature of interest, then derive scale & translate.
			var b = path.bounds(geojson),
			s = .95 / Math.max((b[1][0] - b[0][0]) / width, (b[1][1] - b[0][1]) / height),
			t = [(width - s * (b[1][0] + b[0][0])) / 2, (height - s * (b[1][1] + b[0][1])) / 2];

			// Update the projection to use computed scale & translate.
			console.log("offset s c t ",s,c,t);
			projectionScale=s;
			projectionCenter = c;
			projectionTranslate =t;
			return d3.geo.path().projection(d3.geo.mercator().scale(s).center(c).translate(t));
		};
		
		var pointProjection = function(coordinates){
			var projection =  d3.geo.mercator().scale(projectionScale).center(projectionCenter).translate(projectionTranslate);
			return projection(coordinates);
		}
		
		

		
		$scope.info = {"show": true};
		var updateInfo = function(show, content, value){
			$timeout(function(){
				$scope.info.show= show;
				$scope.info.content = content;
				$scope.info.value = value;
			},100);
		}
		
		var highlightFeature = function(d) {
			var color = darker(d.properties.color, 0.3);
			d3.select(this).style('fill', color);
			updateInfo(true, d.properties.label + " - " + d.properties.DENOMINAZI,d.properties.value);
		};

		var resetHighlight = function(d) {
    	  	mapLayer.selectAll('path').style('fill', function(d){return d.properties.color;});
    	  	updateInfo(false, "");
		};

		function fillFn(d){
			if(d.properties.color)
				return d.properties.color;//getValueColor(d);
			else
				return "#ddd";
		}
		
        var borderColor = "#fff";
        var noDataColor = "#eee";
		
		var loadGeojson = function(type, geojsonUrl, mapLayer){
			console.log("geojsonUrl", geojsonUrl);
			d3.json(geojsonUrl, function(error, mapData) {
				console.log("mapData", error, mapData);
				for (var k in mapData.features) {
					mapData.features[k].properties.selected=false;
				}

				var path = fitGeojson(mapData, width, height, geoprojection);

				// Update color scale domain based on data
				//color.domain([0, d3.max(features, nameLength)]);
				if(mapDataAsr.length>0){
					for(var j=0; j<mapDataAsr.length; j++){
					  var d = mapDataAsr[j];
					  for(var k=0; k<mapData.features.length;k++){
						  if(d.key == mapData.features[k].properties["CODICE_ASL"]){
							mapData.features[k].properties.label = ""; 
							mapData.features[k].properties.value = d.percent;
							mapData.features[k].properties.color = d.color;
							//geojson_data.features[k].properties.color = d.color;
						}
					  }
					}
				}



				//mapLayer.append("rect").attr('width', width).attr('height', height);
				// Draw each province as a path
				mapLayer.selectAll('path')
					.data(mapData.features)
					.enter().append('path')
					.attr('d', path)
					.attr('vector-effect', 'non-scaling-stroke')
					.style('fill', fillFn)
					.style('stroke', borderColor)
					.on('mouseover', highlightFeature)
					.on('mouseout', resetHighlight)
				//	.on('click', onAreaClick);
				if(type=='ao'){
					 var coordinates = pointProjection([7.673724,45.038883]);
					 mapLayer.append('svg:circle')
					        .attr('cx', coordinates[0])
					        .attr('cy', coordinates[1])
					        .attr('r', 15)
					        .attr('stroke', '#f40000')
					        .attr("stroke-width",2)
					        .attr("fill","#ffa8a8")
					        .attr("fill-opacity","1");
				}
				//scope.isLoading = false;
			});
		};
		
		
		
		var geojson_asr = {
	    		url:"data/asr.geojson",
	    		render: {line : {weight : 1,opacity : 1,dashcolor : '#0e232e',dasharray : 1,},
						areas : {fillopacity : .7},
	    		},
			};
		
		var mapLayerAsr,mapLayerAo, width, height;
		var createMap = function(type, geojsonUrl, mapLayer){
	         $timeout(function(){
	        	 console.log("createMap", type, geojsonUrl, mapLayer)
//	            	console.log("elem",elem)
//	            	if(!width)
//	            		width = elem[0].offsetWidth;
//	            	if(!height){
//	            		height= elem[0].offsetHeight;
//	            		if(height<1)
//	            			height = 300;
//	            	} 
	            	width  = 300;
	            	height = 300;
	    			var svg = d3.select('#'+type+'Map svg').attr('width', width).attr('height', height);
	    			var g = svg.append('g');
	    			mapLayer = g.append('g').classed('map-layer', true);
	    			loadGeojson(type, geojsonUrl, mapLayer);
	            });
		}
		
		//createMap("asr", geojson_asr.url, mapLayerAsr);
		
		var geojson_ao = {
	    		url:"data/torino_circoscrizioni_geo.geojson",
	    		key: "code",
	    		render: {
	    			line : {
	    				weight : 1,
	    				opacity : 1,
	    				dashcolor : '#0e232e',
	    				dasharray : 1,
	    			},
					areas : {
						fillopacity : .7,    			
					},
	    		},
			}	
		//createMap("ao", geojson_ao.url, mapLayerAo);

		
}]);



appControllers.controller('DashboardPostilettoEnterCtrl', [ '$scope', 'dashboardcovidService','$translate', '$location', '$uibModal', '$rootScope', 'sessionStorage','$filter','$routeParams',
    function($scope, dashboardcovidService,$translate, $location, $uibModal,$rootScope,sessionStorage,$filter,$routeParams) {
		console.log("DashboardPostilettoEnterCtrl  Controller");
		var idEnte = $routeParams.idEnte;
		console.log("DashboardPostilettoEnterCtrl idEnte", $scope.idEnte);
		$scope.tipi_aree_attive = ['TER_INT','TER_SEMI_INT','PNEUMO', 'INFETT','MED_INT'];

		var disponibilita = new Array();
		$scope.ente = {};
		var loadDisponibilita  = function(){
			dashboardcovidService.loadDisponibilita(true).then(function(result){
				console.log("loadDisponibilita", result);
				if(result && result.data && result.data.length > 0){
					$scope.loading = false;
					
					if(result.data){
						for(var i=0;i<result.data.length;i++){
							var row = result.data[i];
							if(row.ente.idEnte == idEnte){
								disponibilita.push(row);
								if(!$scope.ente.nome){
									$scope.ente = row.ente;
									$scope.ente.totaleDisponibili = 0;
									$scope.ente.totaleOccupati = 0;
									$scope.ente.strutture = {};
								}
								if(!$scope.ente.strutture[row.struttura.nome]){
									$scope.ente.strutture[row.struttura.nome] =  {totaleDisponibili:0, totaleOccupati:0};
								}
								for(var j=0;j<row.disponibilitaArea.length;j++){
									var da =row.disponibilitaArea[j];
									if(!$scope.ente[da.area.idDArea]){
										$scope.ente[da.area.idDArea] = {postiDisponibili:0, postiOccupati:0};
									}
									$scope.ente.strutture[row.struttura.nome][da.area.idDArea] = da.disponibilita;

									$scope.ente[da.area.idDArea].postiDisponibili += da.disponibilita.postiDisponibili;
									$scope.ente[da.area.idDArea].postiOccupati += da.disponibilita.postiOccupati;

									
									$scope.ente.strutture[row.struttura.nome].totaleDisponibili += da.disponibilita.postiDisponibili;
									$scope.ente.strutture[row.struttura.nome].totaleOccupati += da.disponibilita.postiOccupati;
									var percent = parseInt(100*da.disponibilita.postiOccupati/da.disponibilita.postiDisponibili);
									if(percent>80)
										da.disponibilita.style = "color: #ff4141;font-weight:bold;";
									else if(percent>70)
										da.disponibilita.style = "color: #ff9900;font-weight:bold;";
									else if(percent>60)
										da.disponibilita.style = "color: #f0db2e;font-weight:bold;";
									$scope.ente.totaleDisponibili += da.disponibilita.postiDisponibili;
									$scope.ente.totaleOccupati += da.disponibilita.postiOccupati;
								}
								
								
							}
							
							
							
						}
						
						console.log("$scope.ente",$scope.ente);
						preparePiedata();

						
					}
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
		
		var getColoriSoglie = function(percent){
			var res = ["#ccc","#ddd"];
			if(percent){
				if(percent>80)
					res = ["#ff4141","#ffa8a8"];
				else if(percent>70)
					res = ["#ff9900","#ffd08a"];
				else if(percent>60)
					res =  ["#f0db2e","#f7eb8a"];
				else
					res =  ["#9ade00","#d1f08a"];
			}
			return res;
		}
		$scope.pieData = {};
		
		var preparePiedata = function(struttura, area){
			$scope.pieData = {};
			var current = $scope.ente;
			if(struttura && area){
//				var disponibili = dMap[k].totaleDisponibili;
//				var occupati = dMap[k].totaleOccupati;
				
			}
			var disponibili = current.totaleDisponibili;
			var occupati = current.totaleOccupati;
			var percent = 0;
			var colors = getColoriSoglie();
			if(disponibili>0){
				var percent = parseInt(100*occupati/disponibili);
				colors = getColoriSoglie(percent);
				//dMap[k].fullSize = percent;							
				//dMap[k].color = colors[0];
				$scope.pieData ={name:"ASL", percent: percent, data:[
					{key:"Posti disponibili", y:100-percent, color:colors[1]},
					{key:"Posti Occupati", y: percent, color:colors[0]}
					]};
			}
			
			console.log("$scope.pieData",$scope.pieData);
		}
		
		$scope.pieOptions = {
            chart: {
                type: 'pieChart',
                height: 200,
                width: 200,
                x: function(d){return d.key;},
                y: function(d){return d.y;},
                margin:{top:0, left:0, bottom: 0, right:0},
                duration: 5,
                showLegend: false,
                showLabels: false,
                pie:{"donut":true,"donutRatio":"0.5","cornerRadius":"4"},
                valueFormat: function(d){return  parseInt(d) + "%";}
            }
        };
		
		function getRandomInt(min, max) {
		    min = Math.ceil(min);
		    max = Math.floor(max);
		    return Math.floor(Math.random() * (max - min + 1)) + min;
		}
		
		$scope.lineData = [
			{key:'Occupati',strokeWidth: 3,color: '#496684',interpolate: 'basis',values:[]},
			{key:'Attivati',strokeWidth: 1,color: '#f41111',interpolate: 'step-before',values:[]},
			{key:'Target', strokeWidth: 2,classed: 'dashed', color: '#aaaaaa', values:[]},
		];

		var aumentoposti = 2;
		var attivati = 10;
		var target = 30;
		for(var i=0; i<20; i++){
			aumentoposti  += getRandomInt(-1,4);
			time=1584130957989 + i*100000000;
			$scope.lineData[0].values.push({'x': time,'y':aumentoposti});
			if(i==5)
				attivati = 18;
			else if(i==9)
				attivati = 22;
			else if(i==15)
				target = 45;
			else if(i==17)
				attivati = 40;
			$scope.lineData[1].values.push({'x': time,'y':attivati});
			$scope.lineData[2].values.push({'x': time,'y':target});
			

		}
		
//		$scope.lineData = [
//			{key:'Occupati',interpolate: 'linear',values:[{"x": 1584530957989,"y":12},{"x": 11,"y":14},{"x": 12,"y":13},{"x": 13,"y":18},{"x": 14,"y":19}]},
//			{key:'Attivati',values:[{"x": 1584530957989,"y":18},{"x": 13,"y":20},{"x": 14,"y":21}]},
//			{key:'Target', strokeWidth: 2,classed: 'dashed', color: '#cccccc', values:[{"x": 10,"y":21},{"x": 11,"y":21},{"x": 12,"y":21},{"x": 13,"y":21},{"x": 14,"y":21}]},
//		];
		
		console.log("lineData", $scope.lineData);
		
		$scope.lineOptions ={
	            chart: {
	                type: 'lineChart',
	                height: 250,
	                interpolate: 'basis',
	                margin:{top: 20, right: 20, bottom: 40, left: 55},
	                x: function(d){ return d.x; },
	                y: function(d){ return d.y; },
	                useInteractiveGuideline: true,
	                legend:{align: false},
	                xAxis: {
	                	showMaxMin: false,
	                	tickFormat: function(d){
	                    	var date = new Date(d);
	                        return (date.getUTCDate()+1) +"/"+ date.getUTCMonth() ;
	                    },
	                },
	                yAxis: {
	                	showMaxMin: false,
	                    tickFormat: function(d){
	                        return parseInt(d);
	                    },
	                    axisLabelDistance: -10,
	                },
	            },
	        };
}]);


