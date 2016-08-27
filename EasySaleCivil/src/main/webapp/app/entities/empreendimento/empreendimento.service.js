(function() {
    'use strict';
    angular
        .module('easySaleCivilApp')
        .factory('Empreendimento', Empreendimento);

    Empreendimento.$inject = ['$resource'];

    function Empreendimento ($resource) {
        var resourceUrl =  'api/empreendimentos/:id';

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
