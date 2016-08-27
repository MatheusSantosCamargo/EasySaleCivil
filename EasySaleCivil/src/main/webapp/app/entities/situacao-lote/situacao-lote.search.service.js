(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .factory('SituacaoLoteSearch', SituacaoLoteSearch);

    SituacaoLoteSearch.$inject = ['$resource'];

    function SituacaoLoteSearch($resource) {
        var resourceUrl =  'api/_search/situacao-lotes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
