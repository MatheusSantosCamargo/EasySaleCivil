(function() {
    'use strict';
    angular
        .module('easySaleCivilApp')
        .factory('Corretor', Corretor);

    Corretor.$inject = ['$resource'];

    function Corretor ($resource) {
        var resourceUrl =  'api/corretors/:id';

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
