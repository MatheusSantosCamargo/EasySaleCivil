(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .factory('LoteSearch', LoteSearch);

    LoteSearch.$inject = ['$resource'];

    function LoteSearch($resource) {
        var resourceUrl =  'api/_search/lotes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
