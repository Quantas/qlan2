(function() {
    'use strict';

    angular
        .module('qlan.controllers')
        .run(appRun);

    appRun.$inject = ['routehelper'];

    /* @ngInject */
    function appRun(routehelper) {
        routehelper.configureRoutes(getRoutes());
    }

    function getRoutes() {
        return [
            {
                url: '/',
                config: {
                    templateUrl: 'app/controllers/organization.html',
                    controller: 'OrganizationController',
                    controllerAs: 'vm',
                    title: 'Orgs'
                }
            }
        ];
    }
})();