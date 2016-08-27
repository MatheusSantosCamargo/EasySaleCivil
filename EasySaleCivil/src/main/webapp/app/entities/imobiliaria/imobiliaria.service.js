(function() {
    'use strict';
    angular
        .module('easySaleCivilApp')
        .factory('Imobiliaria', Imobiliaria);

    Imobiliaria.$inject = ['$resource'];

    function Imobiliaria ($resource) {
        var resourceUrl =  'api/imobiliarias/:id';

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
