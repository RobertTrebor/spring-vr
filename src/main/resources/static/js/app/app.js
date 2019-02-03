var vrApp = angular.module('vrApp', ['ui.router', 'ngStorage']);

vrApp.constant('urls', {
    BASE: 'http://vr-spring.herokuapp.com/vr/',
    GRAVE_SERVICE_API: 'http://vr-spring.herokuapp.com/vr/api/grave/',
    CEMETERY_SERVICE_API: 'http://vr-spring.herokuapp.com/vr/api/cemetery/'
});

vrApp.config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        console.log('Vor stateprovider' + $urlRouterProvider.valueOf().toString());

        $stateProvider
            .state('cemetery', {
                url: '/',
                templateUrl: 'partials/cemeterieslist',
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

