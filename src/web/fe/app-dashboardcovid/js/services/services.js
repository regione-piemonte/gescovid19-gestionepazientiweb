'use strict';

/* Services */

var appServices = angular.module('dashboardcovid.services', [  ]);

appServices.value('version', '0.0.9_003');

appServices.factory('readFilePreview', function($q) {
	return {
		readTextFile: function (file, previewSize, encoding) {
			var deferread = $q.defer();
			if (window.File && window.FileReader && window.FileList && window.Blob) {
				var reader = new FileReader();
				console.log("file", file);
				if ((file !== undefined) && (file !== null)) {
					reader.onload = function (event) {

						deferread.resolve(event.target.result);
					};
					var firstBytes = file.slice(0, previewSize + 1);
					reader.readAsText(firstBytes, encoding);
				}else{
					console.log("reject", file);
					deferread.reject("You need to pass a file.");
				}
			}else{
				deferread.reject("Your browser don't support File api.");
			}

			return deferread.promise;
		},
		readImageFile: function (file) {
			var deferread = $q.defer();
			if (window.File && window.FileReader && window.FileList && window.Blob) {
				var reader = new FileReader();
				console.log("file", file);
				if ((file !== undefined) && (file !== null)) {
					reader.onload = function (event) {
						deferread.resolve(event.target.result);
					};
					reader.readAsDataURL(file);
				}else{
					console.log("reject", file);
					deferread.reject("You need to pass a file.");
				}
			}else{
				deferread.reject("Your browser don't support File api.");
			}

			return deferread.promise;
		}
		
	};
});


appServices.factory('devService', function($q) {
	return {
		fakeHttpCall : function(isSuccessful, result) {
		
			var deferred = $q.defer();
		
			setTimeout(function() {
			    if (isSuccessful === true) {
			    	if(typeof result == 'undefined')
			    		result = "Successfully resolved the fake $http call";
			        deferred.resolve(result);
			    }
			    else {
			    	if(typeof result == 'undefined')
			    		result = "Oh no! Something went terribly wrong in you fake $http call";
			        deferred.reject(result);
			    }
			}, 200 );
		
		   return deferred.promise;
		}
	};
});


app.factory('idleTimer',  function($rootScope, $timeout) {
	var idleTimer = null;
	var idleTimerService = {};
	
	idleTimerService.startTimer = function () {
        console.debug('Starting timer');
        this.idleTimer = $timeout(this.timerExpiring, 840000); // 14 minutes
     };
    
    idleTimerService.stopTimer = function () {
        if (this.idleTimer) {
            console.debug('stopTimer timer');

            $timeout.cancel(this.idleTimer);
        }
    };
    
    idleTimerService.resetTimer = function () {
        this.stopTimer();
        this.startTimer();
    };
    
    idleTimerService.timerExpiring = function () {
        //this.stopTimer();
        $rootScope.$broadcast('sessionExpiring');
        console.debug('Timer expiring ..');
    };
 
    //startTimer();
 
    return idleTimerService;
});


app.factory('toastService',function(){
	var toast = null;
		return {	
			clearToast: function(){
				toast = null;
			},
			setToast: function(type, message){
				toast = {type: type, message: message};
			},	
			getToast: function(){
				return toast;
			}
	  }
});


app.factory('sessionStorage',function(){
	var session = {};
		return {	
			set: function(key, value){
				session [key] = value;
			},	
			get: function(key){
				return session[key];
			}
	  }
});
