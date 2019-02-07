'use strict';

angular.module('vrApp').factory('CemeteryService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllCemeteries: loadAllCemeteries,
                getAllCemeteries: getAllCemeteries,
                getCemetery: getCemetery,
                createCemetery: createCemetery,
                updateCemetery: updateCemetery,
                removeCemetery: removeCemetery,
                getGravesInCemetery: getGravesInCemetery
            };

            return factory;

            function loadAllCemeteries() {
                console.log('Fetching all cemeteries');
                var deferred = $q.defer();
                $http.get(urls.CEMETERY_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all cemeteries');
                            $localStorage.cemeteries = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading cemeteries');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllCemeteries() {
                console.log('localStorage.cemeteries');
                return $localStorage.cemeteries;
            }

            function getCemetery(id) {
                console.log('Fetching Cemetery with id :' + id);
                var deferred = $q.defer();
                $http.get(urls.CEMETERY_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Cemetery with id :' + id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading cemetery with id :' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getGravesInCemetery(id) {
                console.log('Fetching Graves in Cemetery with id :' + id);
                var deferred = $q.defer();
                $http.get(urls.CEMETERY_SERVICE_API + id + '/graves')
                    .then(
                        function (response) {
                            console.log('Fetched successfully Graves in Cemetery with id :' + id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Graves in Cemetery with id :' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }


            function createCemetery(cemetery) {
                console.log('Creating Cemetery');
                var deferred = $q.defer();
                $http.post(urls.CEMETERY_SERVICE_API, cemetery)
                    .then(
                        function (response) {
                            loadAllCemeteries();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Cemetery : ' + errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateCemetery(cemetery, id) {
                console.log('Updating Cemetery with id ' + id);
                var deferred = $q.defer();
                $http.put(urls.CEMETERY_SERVICE_API + id, cemetery)
                    .then(
                        function (response) {
                            console.log('Loading all Cemeteres after update');
                            loadAllCemeteries();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while Loading all Cemeteres after update' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeCemetery(id) {
                console.log('Removing Cemetery with id ' + id);
                var deferred = $q.defer();
                $http.delete(urls.CEMETERY_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllCemeteries();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Cemetery with id :' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
        }
    ]);