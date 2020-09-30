appControllers.controller("NotificheCtrl", [
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
    console.log("Notifiche  Controller");

    $scope.loading = false;
    $scope.notifiche = new Array();
    $scope.filter = {
      daVisionare: false,
      tamponeDaRichiedere: false,
    };


    $scope.filtered = {currentPage:1};
    $scope.pageSize = 10;
    $scope.order = {};


    $scope.feedback = {};

     var doLoadNotifiche = function(){
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

    $scope.tamponeDaRichiedereFilter = function (notifica) {
      if (!$scope.filter.tamponeDaRichiedere) {
        return true;
      }
      return notifica && notifica.esitoTampone == 'Tampone da richiedere';
    };

    $scope.eventoNotificaIsolamentoFilter = function (notifica) {
      return notifica && notifica.tipoEvento && notifica.tipoEvento == 'ISOLAMENTO';
    };
   
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
		};


	},
]);

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
      var putNotifica = function () {
        console.log("putNotifica");
        $scope.feedback = {};
        gestionepazientiService.putNotifica($scope.notifica.id, $scope.testoRisposta).then(
          function (result) {
            console.log("putNotifica SUCCESS", result);
            //Se salvataggio è ok fare questo:
            $scope.notifica.dataPresaVisione = result.data.dataPresaVisione;
            $scope.notifica.cfUtentePresaVisione = result.data.cfUtentePresaVisione;
            $scope.notifica.testoRisposta = result.data.testoRisposta;

            $uibModalInstance.close('ok');
          },
          function (error) {
            console.error("putNotifica  Error", error);
            var e = error && error.data;


            if(!e || !e.status || e.status == 500){
              $scope.feedback.type = "danger";
              $scope.feedback.message = "FEEDBACK_SERVER_ERROR";
              if (error) {
                $scope.feedback.detail = error;
              }
            } else if(e.status == 400) {
              $scope.feedback.type = "warning";
              $scope.feedback.message = "FEEDBACK_SERVER_ERROR";
              if (error) {
                $scope.feedback.detail = error;
              }
            }

            
          }
        );
      };

      putNotifica();
      
    };

    

    $scope.cancel = function () {
      $uibModalInstance.close();
    };
  },
]);

