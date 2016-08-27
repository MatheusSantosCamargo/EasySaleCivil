(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .factory('CorretorSearch', CorretorSearch);

    CorretorSearch.$inject = ['$resource'];

    function CorretorSearch($resource) {
        var resourceUrl =  'api/_search/corretors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
