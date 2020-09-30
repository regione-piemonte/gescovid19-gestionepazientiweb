appControllers.controller("ComunicazioniMMGCtrl", [
  "$scope",
  "gestionepazientiService",
  "$translate",
  "$location",
  "$uibModal",
  "$rootScope",
  "sessionStorage",
  "$filter",
  function (
    $scope,
    gestionepazientiService,
    $translate,
    $location,
    $uibModal,
    $rootScope,
    sessionStorage,
    $filter
  ) {
    console.log("ComunicazioniMMGController");

    $scope.loading = false;
    $scope.notifiche = new Array();
    $scope.filter = {
      daVisionare: false,
      eventoNotifica: {
        ISOLAMENTO: false,
        AGGRAVAMENTO: false
      }
    };


    $scope.filtered = {currentPage:1};
    $scope.pageSize = 10;
    $scope.order = {};


    $scope.feedback = {};

    var doLoadNotifiche = function () {
      gestionepazientiService.loadNotifiche($rootScope.currentUser, 'SISP', 'MMG').then(
        function (result) {
          $scope.loading = false;
          if (result && result.data && result.data.length > 0) {
            $scope.notifiche = result.data;
          }
          Helpers.util.scrollTo();
        },
        function (error) {
          $scope.loading = false;
          $scope.feedback.type = "danger";
          $scope.feedback.message = "FEEDBACK_SERVER_ERROR";
          if (error) {
            $scope.feedback.detail = error;
          }
          console.error("loadNotifiche  Error", error);
        }
      );
    };

    var loadNotifiche = function () {
      $scope.loading = true;
      $scope.feedback = {};
      if (!$rootScope.currentUser) {
        $rootScope.$on("currentUserLoaded", function () {
          doLoadNotifiche();
        });
      }
      else {
        doLoadNotifiche();
      }
    };

    loadNotifiche();

    $scope.daVisionareFilter = function (notifica) {
      if(!$scope.filter.daVisionare){
        return true;
      }

      return !notifica.dataPresaVisione;
    };

    $scope.eventoNotificaFilter = function (notifica) {

      for(var f in $scope.filter.eventoNotifica){
        if ($scope.filter.eventoNotifica.hasOwnProperty(f)) {
          var filterActive = $scope.filter.eventoNotifica[f];
          if(filterActive && notifica.tipoEvento != f){
              return false;
          }
        }
      }
      return notifica && notifica.tipoEvento != 'ISOLAMENTO';
    };

    $scope.selectEventoNotifica = function(eventoNotifica /* 'ISOLAMENTO', 'AGGRAVAMENTO' */) {
      for(var k in $scope.filter.eventoNotifica){
        if ($scope.filter.eventoNotifica.hasOwnProperty(k)) {
          if(eventoNotifica != k){
            $scope.filter.eventoNotifica[k] = false;
          }
        }
      }

    }

   
    $scope.showPresaVisioneModal = function(n){
        var newPresaInCaricoModal = $uibModal.open({templateUrl: 'partials/notifiche/notifiche_presa_visione.html',
          controller: 'PresaVisioneModalCtrl', 
          size:'lg',
           resolve: {
                notifica: function () {return n;},
                },
          backdrop: 'static',});
          
        newPresaInCaricoModal.result.then(function () {
          Helpers.util.scrollTo();
      }, function () {});
    }


    $scope.showPaziente = function(idSoggetto){
      $location.path("pazienti/view/" + idSoggetto);
          //.search({'selectedTab': 'pazienteDetail'});
    }

	}
]);
/*
appControllers.controller("PresaVisioneModalCtrl", [
  "$scope",
  "$uibModalInstance",
  "gestionepazientiService",
  "toastService",
  "$rootScope",
  "notifica",
  function (
    $scope,
    $uibModalInstance,
    gestionepazientiService,
    toastService,
    $rootScope,
    notifica
  ) {
    $scope.notifica = notifica;
    $scope.testoRisposta = '';

    $scope.prendiVisione = function () {
      console.log("testo risposta: ",$scope.testoRisposta);
      // TODO chiamare la put con la risposta.

      //Se salvataggio è ok fare questo:
      $scope.notifica.testoRisposta = $scope.testoRisposta
      

      $uibModalInstance.close('ok');
    };

    $scope.cancel = function () {
      $uibModalInstance.close();
    };
  },
]);
*/
