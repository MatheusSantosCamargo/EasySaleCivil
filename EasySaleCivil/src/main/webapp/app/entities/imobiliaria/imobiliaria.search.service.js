(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .factory('ImobiliariaSearch', ImobiliariaSearch);

    ImobiliariaSearch.$inject = ['$resource'];

    function ImobiliariaSearch($resource) {
        var resourceUrl =  'api/_search/imobiliarias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
