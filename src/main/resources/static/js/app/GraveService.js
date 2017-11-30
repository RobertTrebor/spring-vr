'use strict';

angular.module('vrApp').factory('GraveService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllGraves: loadAllGraves,
                getAllGraves: getAllGraves,
                getGrave: getGrave,
                createGrave: createGrave,
                updateGrave: updateGrave,
                removeGrave: removeGrave
            };

            return factory;

            function loadAllGraves() {
                console.log('Fetching all graves');
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all graves');
                            $localStorage.graves = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading graves');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllGraves() {
                return $localStorage.graves;
            }

            function getGrave(id) {
                console.log('Fetching Grave with id :' + id);
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Grave with id :' + id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading grave with id :' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createGrave(grave) {
                console.log('Creating Grave');
                var deferred = $q.defer();
                $http.post(urls.USER_SERVICE_API, grave)
                    .then(
                        function (response) {
                            loadAllGraves();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Grave : ' + errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateGrave(grave, id) {
                console.log('Updating Grave with id ' + id);
                var deferred = $q.defer();
                $http.put(urls.USER_SERVICE_API + id, grave)
                    .then(
                        function (response) {
                            loadAllGraves();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Grave with id :' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeGrave(id) {
                console.log('Removing Grave with id ' + id);
                var deferred = $q.defer();
                $http.delete(urls.USER_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllGraves();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Grave with id :' + id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);