'use strict';

angular.module('vrApp').controller('GraveController',
    ['GraveService', '$scope', function (GraveService, $scope) {

        var self = this;
        self.grave = {};
        self.graves = [];

        self.submit = submit;
        self.getAllGraves = getAllGraves;
        self.createGrave = createGrave;
        self.updateGrave = updateGrave;
        self.removeGrave = removeGrave;
        self.editGrave = editGrave;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.grave.id === undefined || self.grave.id === null) {
                console.log('Saving New Grave', self.grave);
                createGrave(self.grave);
            } else {
                updateGrave(self.grave, self.grave.id);
                console.log('Grave updated with id ', self.grave.id);
            }
        }

        function createGrave(grave) {
            console.log('About to create grave');
            GraveService.createGrave(grave)
                .then(
                    function (response) {
                        console.log('Grave created successfully');
                        self.successMessage = 'Grave created successfully';
                        self.errorMessage = '';
                        self.done = true;
                        self.grave = {};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Grave');
                        self.errorMessage = 'Error while creating Grave: ' + errResponse.data.errorMessage;
                        self.successMessage = '';
                    }
                );
        }


        function updateGrave(grave, id) {
            console.log('About to update grave');
            GraveService.updateGrave(grave, id)
                .then(
                    function (response) {
                        console.log('Grave updated successfully');
                        self.successMessage = 'Grave updated successfully';
                        self.errorMessage = '';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while updating Grave');
                        self.errorMessage = 'Error while updating Grave ' + errResponse.data;
                        self.successMessage = '';
                    }
                );
        }


        function removeGrave(id) {
            console.log('About to remove Grave with id ' + id);
            GraveService.removeGrave(id)
                .then(
                    function () {
                        console.log('Grave ' + id + ' removed successfully');
                    },
                    function (errResponse) {
                        console.error('Error while removing grave ' + id + ', Error :' + errResponse.data);
                    }
                );
        }


        function getAllGraves() {
            return GraveService.getAllGraves();
        }

        function editGrave(id) {
            self.successMessage = '';
            self.errorMessage = '';
            GraveService.getGrave(id).then(
                function (grave) {
                    self.grave = grave;
                },
                function (errResponse) {
                    console.error('Error while removing grave ' + id + ', Error :' + errResponse.data);
                }
            );
        }

        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.grave = {};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);