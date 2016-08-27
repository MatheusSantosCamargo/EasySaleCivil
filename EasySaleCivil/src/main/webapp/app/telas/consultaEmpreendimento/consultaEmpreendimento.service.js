(function() {
    'use strict';
    
    angular
        .module('easySaleCivilApp')
        .factory('ConsultaEmpreendimento', ConsultaEmpreendimento);

    ConsultaEmpreendimento.$inject = ['$resource'];

    function ConsultaEmpreendimento($resource) {
        var resourceUrl =  'api/lotesPorEmpreendimento/:idEmpreendimento';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
