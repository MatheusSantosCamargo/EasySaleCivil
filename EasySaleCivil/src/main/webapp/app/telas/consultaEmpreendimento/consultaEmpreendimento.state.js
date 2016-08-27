(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('consultaEmpreendimento', {
            parent: 'telas',
            url: '/consultaEmpreendimento',
            data: {
                authorities: [],
        		pageTitle: 'consultaEmpreendimento.titleBar'
            },
            views: {
                'content@': {
                    templateUrl: 'app/telas/consultaEmpreendimento/consultaEmpreendimento.html',
                    controller: 'ConsultaEmpreendimentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('consultaEmpreendimento');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
