'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('gestionepazienti', [
  'ngRoute',
  'ngSanitize',
//  'gestionepazienti.config',
  'gestionepazienti.filters',
  'gestionepazienti.services',
  'gestionepazienti.directives',
  'gestionepazienti.controllers',
  'pascalprecht.translate',
  'ui.bootstrap',
  'LocalStorageModule'
]);

app.run(function ($rootScope) {
    $rootScope.Constants = Constants;
 });

app.config(['$routeProvider','$locationProvider', function($routeProvider,$locationProvider) {

	$locationProvider.hashPrefix('');
	//$routeProvider.when('/home', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'home',templateUrl: 'partials/common/home.html?'});
	
	$routeProvider.when('/pazienti', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'pazienti', templateUrl: 'partials/pazienti/pazienti.html?' , controller: 'PazientiCtrl'});
	$routeProvider.when('/pazienti/:listtype', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'pazientisearch', templateUrl: 'partials/pazienti/search.html?' , controller: 'PazientiCtrl'});
	$routeProvider.when('/pazienti/view/:idPaziente', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'pazienti', templateUrl: 'partials/pazienti/paziente.html?', controller: 'PazienteCtrl'});
	$routeProvider.when('/richiestetamponi', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'richiestetamponi', templateUrl: 'partials/tamponi/richieste_tamponi.html?' , controller: 'TamponiCtrl'});
	$routeProvider.when('/notifiche', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'notifiche', templateUrl: 'partials/notifiche/notifiche.html?' , controller: 'NotificheCtrl'});
	$routeProvider.when('/comunicazioni_mmg', { pageTitle: 'PAGE_TITLE_HOME', activetab: 'comunicazioni_mmg', templateUrl: 'partials/notifiche/comunicazioni_mmg.html?' , controller: 'ComunicazioniMMGCtrl'});
	//$routeProvider.when('/pazienti/list/:tipolistaDecorsi', {pageTitle: 'PAGE_TITLE_HOME', activetab: ':tipolistaDecorsi', templateUrl: 'partials/decorsi/trasferimenti_esterni.html?' , controller: 'DecorsiCtrl'});
	$routeProvider.when('/pazienti/list/:tipolistaDecorsi', {pageTitle: 'PAGE_TITLE_HOME', activetab:':tipolistaDecorsi', templateUrl: 
		function(params){return 'partials/decorsi/pazienti_' + params.tipolistaDecorsi +'.html?';}
	, controller: 'DecorsiCtrl'});

	
	$routeProvider.otherwise({pageTitle: 'PAGE_TITLE_HOME', redirectTo: '/pazienti'});
}]);


app.config(['$translateProvider', function ($translateProvider) {
	// add translation table
	$translateProvider
	.translations('en', translations_en)
	.translations('it', translations_it)
	.preferredLanguage('it');
	
	$translateProvider.useSanitizeValueStrategy();
}]);

//
//var appInitPromise = null;
//app.factory("appInit", function(gestionepazientiService, info, $rootScope) {
//    return {
//    	"getUser": function() {
//    		console.log("ui")
//    		if(appInitPromise == null){
//	    		appInitPromise = gestionepazientiService.getCurrentUser();
//	    		appInitPromise.success(function(result) {
//		    		$rootScope.currentUser = result;
//		    		console.debug("result", result);
//		    	});
//    		}
//	        return appInitPromise;
//    	}
//    };
//});

//app.config(['$httpProvider', function($httpProvider) {
//	$httpProvider.interceptors.push(function($q, idleTimer,info) {
//		return {
//			'request': function(config) {
//				if(config.url.includes("/userportal/api/proxy/") && info.getActiveTenantCode()!= null && info.getActiveTenantCode() != 'sandbox'){
//					console.log("resetTimer", info.getActiveTenantCode() );  
//					idleTimer.resetTimer();
//				}
//				return config;
//			},
//			'response': function(response) { return response;}
//		};
//	});
//
//}]);



app.run(['$rootScope', 'gestionepazientiService','toastService', '$timeout', 
	function($rootScope,gestionepazientiService,toastService,$timeout) {

    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
    	console.log("$routeChangeSuccess");
    	var path = current.$$route.originalPath;
    	if(!$rootScope.currentUser){
    		gestionepazientiService.getCurrentUser().then(function(result){

					let isAbilitato = function (cu) {
						let elencoProfilo = cu && cu.elencoProfilo;
						if(!elencoProfilo | !elencoProfilo.length){
							return false;
						}
						for (var i = 0; i < elencoProfilo.length; i++) {
							let prof = elencoProfilo[i];
							console.log(prof);
							if (prof.idProfilo == 6 || prof.idProfilo == 4 ){
								return true;
							}
						}
						return false;
					}

					let currentUserLodaed = result.data;
					let allowed = isAbilitato(currentUserLodaed);

					if(!allowed){
						location.assign('/gestionepazientiweb/accessdenied.html');
						$rootScope.$broadcast("currentUserNotAllowed");
						return;
					}
					
					$rootScope.currentUser = result.data;
					console.log("currenUser", $rootScope.currentUser);
					$rootScope.$broadcast("currentUserLoaded");

    			
    		}, function(error){
    			console.error("getCurrentUser Error", error)
    		});
    	}
    	
    	var toastTimer ;
    	$rootScope.toast  = toastService.getToast();
        if($rootScope.toast){
        	toastTimer = $timeout(function(){
        		$rootScope.toast = null;
        		toastService.clearToast();
        	},3000);
        }
    		
//        if (!path.startsWith("/info") && info && info.getInfo() && info.getInfo().user && !info.getInfo().user.loggedIn) {
//    		console.log('DENY : Redirecting to Login');
//    		event.preventDefault();
//    		//$location.path('home');
//    		window.location = YUCCA_HOME_PAGE;
//    	}
//    	else {
//    		console.log('ALLOW');
//    	}
    	if(current.$$route)
    		$rootScope.pageTitle = current.$$route.pageTitle;
    });
}]);
