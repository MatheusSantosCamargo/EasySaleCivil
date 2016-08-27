(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .factory('CadastroClienteSearch', CadastroClienteSearch);

    CadastroClienteSearch.$inject = ['$resource'];

    function CadastroClienteSearch($resource) {
        var resourceUrl =  'api/_search/cadastro-clientes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
