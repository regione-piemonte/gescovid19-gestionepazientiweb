'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('dashboardcovid', [
  'ngRoute',
  'ngSanitize',
  'dashboardcovid.filters',
  'dashboardcovid.services',
  'dashboardcovid.directives',
  'dashboardcovid.controllers',
  'pascalprecht.translate',
  'ui.bootstrap',
  'LocalStorageModule',
  'nvd3'
]);

app.run(function ($rootScope) {
    $rootScope.Constants = Constants;
 });

app.config(['$routeProvider','$locationProvider', function($routeProvider,$locationProvider) {

	$locationProvider.hashPrefix('');
	//$routeProvider.when('/home', {pageTitle: 'PAGE_TITLE_HOME', activetab: 'home',templateUrl: 'partials/common/home.html?'});
	$routeProvider.when('/situazione', {pageTitle: 'PAGE_TITLE_SITUAZIONE', activetab: 'situazione', templateUrl: 'partials/situazione/situazione.html?' , controller: 'SituazioneCtrl'});
	$routeProvider.when('/movimentazione', {pageTitle: 'PAGE_TITLE_MOVIMENTAZINE', activetab: 'movimentazione', templateUrl: 'partials/movimentazione/movimentazione.html?' , controller: 'MovimentazioneCtrl'});
	$routeProvider.when('/dashboard/postiletto', {pageTitle: 'PAGE_TITLE_SITUAZIONE', activetab: 'dashboard', templateUrl: 'partials/situazione/DashboardPostiletto.html?' , controller: 'DashboardPostilettoCtrl'});
	$routeProvider.when('/dashboard/postiletto/ente/:idEnte', {pageTitle: 'PAGE_TITLE_SITUAZIONE', activetab: 'dashboard', templateUrl: 'partials/situazione/DashboardPostilettoEnte.html?' , controller: 'DashboardPostilettoEnterCtrl'});
	
	$routeProvider.otherwise({pageTitle: 'PAGE_TITLE_HOME', redirectTo: '/situazione'});
}]);


app.config(['$translateProvider', function ($translateProvider) {
	// add translation table
	$translateProvider
	.translations('en', translations_en)
	.translations('it', translations_it)
	.preferredLanguage('it');
	
	$translateProvider.useSanitizeValueStrategy();
}]);




app.run(['$rootScope', 'dashboardcovidService','toastService', '$timeout',
	function($rootScope,dashboardcovidService,toastService,$timeout) {

    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
    	console.log("$routeChangeSuccess");
    	var path = current.$$route.originalPath;
    	if(!$rootScope.currentUser){
    		dashboardcovidService.getCurrentUser().then(function(result){
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
