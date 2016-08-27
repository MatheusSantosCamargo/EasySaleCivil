(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('SituacaoLoteController', SituacaoLoteController);

    SituacaoLoteController.$inject = ['$scope', '$state', 'SituacaoLote', 'SituacaoLoteSearch'];

    function SituacaoLoteController ($scope, $state, SituacaoLote, SituacaoLoteSearch) {
        var vm = this;
        
        vm.situacaoLotes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            SituacaoLote.query(function(result) {
                vm.situacaoLotes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SituacaoLoteSearch.query({query: vm.searchQuery}, function(result) {
                vm.situacaoLotes = result;
            });
        }    }
})();
