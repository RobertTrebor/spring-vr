var vrApp = angular.module('vrApp', ['ui.router', 'ngStorage']);
var addr = function(){
    if((window.location.href.substr(0, 21) == 'http://localhost:8080')) {
        return 'http://localhost:8080';
    } else {
        return 'https://vr-spring.herokuapp.com:8080';
    }
}
vrApp.constant('urls', {
    BASE: addr(), // 'http://localhost:8080',
    GRAVE_SERVICE_API: addr() + '/api/grave/',
    CEMETERY_SERVICE_API: addr() + '/api/cemetery/'
});

vrApp.config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        console.log('Vor stateprovider' + $urlRouterProvider.valueOf().toString());

        $stateProvider
            .state('cemetery', {
                url: '/',
                templateUrl: 'partials/angular',
                controller: 'CemeteryController',
                controllerAs: 'cctrl',
                resolve: {
                    graves: function ($q, CemeteryService) {
                        console.log('Load all cemeteries');
                        var deferred = $q.defer();
                        CemeteryService.loadAllCemeteries().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            })
            .state('cemetery.graves', {
                url: '/',
                templateUrl: 'partials/cemeterieslist',
                controller: 'CemeteryController',
                controllerAs: 'cctrl',
                resolve: {
                    graves: function ($q, CemeteryService) {
                        console.log('Load all graves on this cemetery');
                        var deferred = $q.defer();
                        CemeteryService.loadAllGraves().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        //$urlRouterProvider.when('/').then('partials/index');
        $urlRouterProvider.otherwise('/');

    }]);

