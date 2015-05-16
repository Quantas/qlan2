(function() {
    'use strict';

    angular
        .module('qlan.services')
        .service('OrganizationService', OrganizationService);

    OrganizationService.$inject = ['$http', 'SpringDataRestAdapter'];

    function OrganizationService($http, SpringDataRestAdapter) {

        return {
            orgs: orgs
        };

        /////////////////////

        function orgs(callback) {
            var deferred = $http.get('/api/organizations');

            return SpringDataRestAdapter.process(deferred).then(function(data) {
                callback && callback(data._embeddedItems);
            });

        }
    }
}());