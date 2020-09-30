'use strict';

/* Directives */
var appDirectives = angular.module('dashboardcovid.directives', []);

appDirectives.directive('currentYear', [ function() {
	return function(scope, elm, attrs) {
		elm.text(new Date().getFullYear());
	};
} ]);

appDirectives.directive('appVersion', [ 'version', function(version) {
	return function(scope, elm, attrs) {
		elm.text(version);
	};
} ]);

appDirectives.directive('mainNavbar', function() {
	return {
		restrict : 'E',
		templateUrl : 'partials/common/main-navbar.html?',
	};
});


appDirectives.directive('mainFooter', function() {
	return {
		restrict : 'E',
		templateUrl : 'partials/common/main-footer.html?',
	};
});

appDirectives.directive('usecase', function ($rootScope, $timeout) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
    	  //console.log("attrs.projectId",attrs.projectId, $rootScope.currentUser);
		  element.css('display', "none");
    	  
    	  var refresh = function(){
    		  $timeout(function(){
    			  var projectKey = "project_"+attrs.projectId;
//    			  if(!$rootScope.currentUser || !$rootScope.currentUser.superadmin){
//    				  if(!$rootScope.currentUser 
//    						  || !attrs.projectId || !attrs.usecase
//    						  || !Helpers.util.has($rootScope.currentUser, "roles."+projectKey + "." + attrs.usecase) 
//    						  || !$rootScope.currentUser.roles[projectKey][attrs.usecase])
//    					  element.css('display', "none");
//
//    			  }
    			  if($rootScope.currentUser){
    				  if((attrs.projectId && attrs.usecase
    						  && Helpers.util.has($rootScope.currentUser, "roles."+projectKey + "." + attrs.usecase) 
    						  && $rootScope.currentUser.roles[projectKey][attrs.usecase]) || $rootScope.currentUser.superadmin)
    					  element.css('display', "");
    			  }

    		  });    		
    	  };
    	  
    	 refresh();
    	 scope.$on('csurrentUserLoaded', function(){refresh()});
      }
    }
  }
);

appDirectives.directive('logo', function() {
	return {
		restrict : 'E',
		template: '<div class="logo"><span class="gestione">Monitoraggio Posti Letto</span><br><span class="pazienti">Piattaforma Covid-19 Piemonte</span></div>'
	};
});

appDirectives.directive('loading', function() {
	return {
		restrict : 'E',
		scope:{size: '@'},
		template: '<div class="loading loading-{{size}}"><i class="fa fa-circle-o-notch fa-spin fa-fw"></i><span class="sr-only">Loading...</span></div>'
	};
});

appDirectives.directive('usedLang', function updateLanguage( $rootScope ) {
    return {
        link: function( scope, element ) {
          var listener = function( event, translationResp ) {
            var defaultLang = "it",
                currentlang = translationResp.language;

            element.attr("lang", currentlang || defaultLang );
          };

          $rootScope.$on('$translateChangeSuccess', listener);
        }
     };
});

appDirectives.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});


appDirectives.directive('scrollOnClick', function() {
	return {
	    restrict: 'A',
	    link: function(scope, $elm, attrs) {
	    	var idToScroll = attrs.href;
	    	console.log("idToScroll",idToScroll);
	    	$elm.on('click', function() {
	    		var $target;
	    		if (idToScroll && idToScroll!='javascript:void(0)') {
	    			$target = $(idToScroll);
	    		} else {
	    			$target = $elm;
	    		}
	    		console.debug("target ",$target);
	    		$("body").animate({scrollTop: $target.offset().top}, "slow");
	    	});
	    }
	 };
});

appDirectives.directive('ngConfirmClick', function(){
	return {
		priority: -1,
		restrict: 'A',
		link: function(scope, element, attrs){
			element.bind('click', function(e){
				var message = attrs.ngConfirmClick;
				if(message && !confirm(message)){
					e.stopImmediatePropagation();
					e.preventDefault();
				}
			});
		}
	};
 });


appDirectives.directive('alertPanel', [function(){
	return {
	  restrict: 'E',
	  template: '<div class="alert alert-{{content.type}}" ng-if="content.message || message">'+
	  			'  <div class="alert-close-link" href ng-click="hide()" ng-if="closable!=\'false\'"> {{closable}} &times</div>'+
	  			'  <strong>{{content.message|translate}}{{content.hint}}</strong>'+
				'  <div> <span ng-if="content.code">Code: {{content.code}} - </span> <span ng-if="content.detail" title="{{content.detail}}">{{content.detail|hide_server_error}}</span></div>'+
				'  <div ng-if="content.details" ng-repeat="d in content.details track by $index">&hybull; {{d|translate}}</div>'+
				'</div>',
	  scope: {
		content: '=?',
		closable: '@',
		type: '@',
	    code: '@',
	    message: '@',
	    detail: '@',
	    details: '='
	  },
	  link: function(scope, element, attrs){
		  //scope.content = scope.content;
		  if(!scope.content){
			  scope.content = {type: scope.type,
					   code: scope.code,
					    message: scope.message,
					    detail: scope.detail,
					    details: scope.details,
					  };
		  }
          scope.hide = function(){
        	scope.content.message=null;  
          };
      }
	};
}]);

appDirectives.directive('advancedTypehead', [function(){
	return {
	  restrict: 'E',
	  template: '<div class="form-group inline-form-group">'+
			'<label for="{{identifier}}" translate>label</label>'+
			'<input type="text" class="form-control" id="{{identifier}}" >'+
		'</div>',
	  scope: {
		content: '=?',
		identifier: '@',
		label: '@',
	  },
	  link: function(scope, element, attrs){

		  //scope.details = scope.details;
          scope.hide = function(){
        	scope.content.message=null;  
          };
      }
	};
}]);


appDirectives.directive('helpButton', ['$modal',function($modal){
	return {
	  restrict: 'E',
	  template: '<a href class="help-button  {{css}}" ng-click="openHelp()"><i class="fa fa-question-circle"></i></a>',
	  scope: {
		helptitle: '@',
		section: '@',
		page: '@',
		css: '@',
		size: '@',
	  },
	  link: function(scope, element, attrs){
		  if(typeof scope.size == 'undefined' || scope.size == null)
			  scope.size = 'md';
		  scope.openHelp = function(){
			  $modal.open({
				animation : true,
				size: scope.size,
				templateUrl : 'helpDialog.html',
				controller : 'HelpDialogCtrl',
				backdrop  : 'static',
				resolve: { 
					help: function () {return {"title": scope.helptitle, "section":scope.section,"page":scope.page};}
				}
			});
		  }
		  
      }
	};
}]);

