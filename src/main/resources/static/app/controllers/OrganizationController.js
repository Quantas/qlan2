(function() {
    'use strict';

    angular
        .module('qlan.controllers')
        .controller('OrganizationController', OrganizationController);

    OrganizationController.$inject = ['OrganizationService'];

    function OrganizationController(OrganizationService) {

        /*jshint validthis: true */
        var vm = this;

        vm.orgs = [];

        activate();

        function activate() {
            OrganizationService.orgs(function(data) {
                vm.orgs = data ? data : [];
            });
        }
    }
})();