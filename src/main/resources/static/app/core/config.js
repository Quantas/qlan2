(function() {
    'use strict';

    var core = angular.module('qlan.core');

    var config = {
        appErrorPrefix: '[NG-Modular Error] ', //Configure the exceptionHandler decorator
        appTitle: 'Angular Modular Demo',
        version: '1.0.0'
    };

    core.value('config', config);

    core.config(configure);

    /* @ngInject */
    function configure ($routeProvider, routehelperConfigProvider) {
        routehelperConfigProvider.config.$routeProvider = $routeProvider;
        routehelperConfigProvider.config.docTitle = 'QLAN 2.0: ';
    }
})();