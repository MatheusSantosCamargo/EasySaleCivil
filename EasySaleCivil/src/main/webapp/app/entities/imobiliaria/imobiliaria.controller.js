(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('ImobiliariaController', ImobiliariaController);

    ImobiliariaController.$inject = ['$scope', '$state', 'Imobiliaria', 'ImobiliariaSearch'];

    function ImobiliariaController ($scope, $state, Imobiliaria, ImobiliariaSearch) {
        var vm = this;
        
        vm.imobiliarias = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Imobiliaria.query(function(result) {
                vm.imobiliarias = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ImobiliariaSearch.query({query: vm.searchQuery}, function(result) {
                vm.imobiliarias = result;
            });
        }    }
})();
