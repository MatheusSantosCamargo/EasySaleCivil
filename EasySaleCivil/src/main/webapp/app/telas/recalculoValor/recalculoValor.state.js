(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('recalculoValor', {
            parent: 'telas',
            url: '/recalculoValor',
            data: {
                authorities: [],
        		pageTitle: 'recalculoValores.titleBar'
            },
            views: {
                'content@': {
                    templateUrl: 'app/telas/recalculoValor/recalculoValor.html',
                    controller: 'RecalculoValorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('recalculoValor');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
