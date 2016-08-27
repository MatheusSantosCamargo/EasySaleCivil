(function() {
    'use strict';
    angular
        .module('easySaleCivilApp')
        .factory('CadastroCliente', CadastroCliente);

    CadastroCliente.$inject = ['$resource'];

    function CadastroCliente ($resource) {
        var resourceUrl =  'api/cadastro-clientes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
