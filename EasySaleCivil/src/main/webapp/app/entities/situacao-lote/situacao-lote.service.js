(function() {
    'use strict';
    angular
        .module('easySaleCivilApp')
        .factory('SituacaoLote', SituacaoLote);

    SituacaoLote.$inject = ['$resource'];

    function SituacaoLote ($resource) {
        var resourceUrl =  'api/situacao-lotes/:id';

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
