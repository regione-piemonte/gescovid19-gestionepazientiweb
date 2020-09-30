'use strict';

/* Controllers */

var appControllers = angular.module('gestionepazienti.controllers', []);

appControllers.controller('GlobalCtrl', [ '$scope', '$translate', '$location', 'localStorageService', '$route', 'toastService', '$rootScope', 'gestionepazientiService', '$window','$uibModal','version',
                                          function($scope, $translate, $location, localStorageService,$route, toastService, $rootScope,gestionepazientiService, $window,$uibModal,version) {
	
	$scope.currentLang = function(){return $translate.use();};
	$scope.$route = $route;
	console.log("$route",$route);
//	if(!$rootScope.breadcrumbItems)
//		$rootScope.breadcrumbItems = new Array();
	var supportedLanguages = ['it', 'en'];
	
	$scope.currentDate = new Date();

	var langParam = $location.search().lang;
	if(typeof langParam != 'undefined' && supportedLanguages.indexOf(langParam)>=0){
		$translate.use(langParam);
	}
	else {
		var savedLang = localStorageService.get("currentLang");
		if(typeof savedLang != 'undefined' && supportedLanguages.indexOf(savedLang)>=0)
			$translate.use(savedLang);

	}
	
	$scope.closeToast = function(){
		$rootScope.toast = null;
		toastService.clearToast();
	};
	
	
	
	$scope.changeLanguage = function(langKey) {
		$translate.use(langKey);
		localStorageService.set("currentLang",langKey);
		refreshMainNews(langKey);
	};
	
	$scope.hasProfilo = function(idProfilo){
		var has = false;
		if($rootScope.currentUser && $rootScope.currentUser.elencoProfilo){
			for (var i = 0; i < $rootScope.currentUser.elencoProfilo.length; i++) {
				if($rootScope.currentUser.elencoProfilo[i].idProfilo==idProfilo){
					has=true;
					break;
				}
			}
		}
		return has;
	}
	
	$scope.logout = function(){
		gestionepazientiService.logout().then(function(result){
			console.log("logout", result);
			$rootScope.currentUser = null;
			window.location = result.data.message;
			//$location.path("/gestionepazientiweb/login.html");
		}, function(error){
			console.error("logout Error", error);
			$window.location.href = "login.html";

		});
	};
	
	
	$scope.showPostiDisponibiliModal = function(){
		console.log("showPostiDisponibiliModal");
		var postiDisponibiliModal = $uibModal.open({templateUrl: 'partials/decorsi/posti_disponibili.html','size':'auto-width',
			controller: 'PostiDisponibilitModalCtrl', backdrop: 'static',
			});
    		
		postiDisponibiliModal.result.then(function () {
    	   }, function () {});
    };
	
	
	$rootScope.adminOptions = {showDeletedItem : false};
	
	

	
	$scope.showReleasenotes = function(){
		if(Releasenotes[version]){
			$uibModal.open({
				animation : true,
				templateUrl : 'partials/common/releasenotes.html',
				controller : 'ReleasenotesCtrl',
				backdrop  : 'static',
				keyboard : false,
			});
		}
		
	};
	
	var showCurrentReleaseNotes = function(){
		console.log("sss",localStorageService.get("relasenotes_" + version + "_readed") );
		if(!localStorageService.get("relasenotes_" + version + "_readed") )
			$scope.showReleasenotes();		
	};
	
	showCurrentReleaseNotes();
}]);




appControllers.controller('HelpDialogCtrl', [ '$scope', '$uibModalInstance', 'help','$translate',
    function($scope, $uibModalInstance, help,$translate) {
	
		console.log("HelpDialogCtrl", help)
		$scope.help = help;
		$scope.currentLang = function(){return $translate.use();};
		$scope.yes = function () {
		    $uibModalInstance.close("yes");
		};
	
}]);


appControllers.controller('HtmlPopoverCtrl', [ '$scope', '$uibModalInstance', 'title', 'htmlContent', 'htmlFooter', function($scope, $uibModalInstance, title, htmlContent, htmlFooter) {
	console.log("HtmlPopoverCtrl", title, htmlContent, htmlFooter);
	$scope.title = title;
	$scope.htmlContent = htmlContent;
	$scope.htmlFooter = htmlFooter;
	$scope.cancel = function () {$uibModalInstance.dismiss('cancel');};
}]);

appControllers.controller('ConfirmDialogCtrl', [ '$scope', '$uibModalInstance', 'question',
    function($scope, $uibModalInstance, question) {
	
		$scope.question = question;
	
		$scope.yes = function () {
		    $uibModalInstance.close("yes");
		};
	
		$scope.no = function () {
		    $uibModalInstance.dismiss('cancel');
		};
}]);

appControllers.controller('TableOptionsModalCtrl', [ '$scope', '$uibModalInstance', 'pageSize', 'showCompactData',
    function($scope, $uibModalInstance, pageSize, showCompactData) {
	
		$scope.options = {};
		$scope.options.pageSize = pageSize;
		$scope.options.showCompactData = showCompactData;
	
		$scope.ok= function () {
		    $uibModalInstance.close($scope.options);
		};
	
		$scope.cancel = function () {
		    $uibModalInstance.dismiss('cancel');
		};
}]);

appControllers.controller('ReleasenotesCtrl', [ '$scope', '$uibModalInstance', 'version','localStorageService',
    function($scope, $modalInstance, version,localStorageService) { 
		console.log("ReleasenotesListCtrl ");
		
		$scope.releasenotes = Releasenotes[version];
		$scope.v = version;

		$scope.cancel  = function () {
			localStorageService.set("relasenotes_" + version+"_readed", true); 
			$modalInstance.dismiss('cancel');
		};
}]);

