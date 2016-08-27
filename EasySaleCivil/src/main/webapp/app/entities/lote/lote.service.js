(function() {
    'use strict';
    angular
        .module('easySaleCivilApp')
        .factory('Lote', Lote);

    Lote.$inject = ['$resource'];

    function Lote ($resource) {
        var resourceUrl =  'api/lotes/:id';

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
