(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('LoteController', LoteController);

    LoteController.$inject = ['$scope', '$state', 'Lote', 'LoteSearch'];

    function LoteController ($scope, $state, Lote, LoteSearch) {
        var vm = this;
        
        vm.lotes = [];
        vm.lotesPorEmpreendimento = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Lote.query(function(result) {
                vm.lotes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            LoteSearch.query({query: vm.searchQuery}, function(result) {
                vm.lotes = result;
            });
        }
    }
})();
