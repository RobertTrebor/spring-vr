var app = angular.module('vrApp', ['ui.router', 'ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/vr',
    USER_SERVICE_API: 'http://localhost:8080/vr/api/grave/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller: 'GraveController',
                controllerAs: 'ctrl',
                resolve: {
                    graves: function ($q, GraveService) {
                        console.log('Load all graves');
                        var deferred = $q.defer();
                        GraveService.loadAllGraves().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

