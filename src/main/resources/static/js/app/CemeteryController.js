'use strict';

angular.module('vrApp').controller('CemeteryController',
    ['CemeteryService', '$scope', function (CemeteryService, $scope) {

        var self = this;
        self.cemetery = {};
        self.cemeteries = [];
        self.graves = [];

        self.submit = submit;
        self.getAllCemeteries = getAllCemeteries;
        self.getGravesInCemetery = getGravesInCemetery;
        self.createCemetery = createCemetery;
        self.updateCemetery = updateCemetery;
        self.removeCemetery = removeCemetery;
        self.editCemetery = editCemetery;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.cemetery.id === undefined || self.cemetery.id === null) {
                console.log('Saving New Cemetery', self.cemetery);
                createCemetery(self.cemetery);
            } else {
                updateCemetery(self.cemetery, self.cemetery.id);
                console.log('Cemetery updated with id ', self.cemetery.id);
            }
        }

        function createCemetery(cemetery) {
            console.log('About to create cemetery');
            CemeteryService.createCemetery(cemetery)
                .then(
                    function (response) {
                        console.log('Cemetery created successfully');
                        self.successMessage = 'Cemetery created successfully';
                        self.errorMessage = '';
                        self.done = true;
                        self.cemetery = {};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Cemetery');
                        self.errorMessage = 'Error while creating Cemetery: ' + errResponse.data.errorMessage;
                        self.successMessage = '';
                    }
                );
        }


        function updateCemetery(cemetery, id) {
            console.log('About to update cemetery');
            CemeteryService.updateCemetery(cemetery, id)
                .then(
                    function (response) {
                        console.log('Cemetery updated successfully');
                        self.successMessage = 'Cemetery updated successfully';
                        self.errorMessage = '';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while updating Cemetery');
                        self.errorMessage = 'Error while updating Cemetery ' + errResponse.data;
                        self.successMessage = '';
                    }
                );
        }


        function removeCemetery(id) {
            console.log('About to remove Cemetery with id ' + id);
            CemeteryService.removeCemetery(id).then(
                    function () {
                        console.log('Cemetery ' + id + ' removed successfully');
                    },
                    function (errResponse) {
                        console.error('Error while removing cemetery ' + id + ', Error :' + errResponse.data);
                    }
                );
        }


        function getAllCemeteries() {
            console.log('getAllCemeteries');
            return CemeteryService.getAllCemeteries();
        }

        function getGravesInCemetery() {
            console.log('Get graves in selected Cemetery');
            CemeteryService.getGravesInCemetery(self.cemetery.id).then(
                function (graves) {
                    self.graves = graves;
                    console.log('getGravesInCemetery SUCCESS');
                },
                function (errResponse) {
                    console.log('getGravesInCemetery FAIL');
                }
            )
        }


        function editCemetery(id) {
            console.log('Edit Cemetery with id ' + id);
            self.successMessage = '';
            self.errorMessage = '';
            CemeteryService.getCemetery(id).then(
                function (cemetery) {
                    self.cemetery = cemetery;
                    console.log('editCemetery with id: ' + id);
                },
                function (errResponse) {
                    console.error('Error while removing cemetery ' + id + ', Error :' + errResponse.data);
                }
            );
        }

        function reset() {
            console.log('reset');
            self.successMessage = '';
            self.errorMessage = '';
            self.cemetery = {};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);