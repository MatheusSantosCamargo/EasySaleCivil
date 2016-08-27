(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CorretorController', CorretorController);

    CorretorController.$inject = ['$scope', '$state', 'Corretor', 'CorretorSearch'];

    function CorretorController ($scope, $state, Corretor, CorretorSearch) {
        var vm = this;
        
        vm.corretors = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Corretor.query(function(result) {
                vm.corretors = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CorretorSearch.query({query: vm.searchQuery}, function(result) {
                vm.corretors = result;
            });
        }    }
})();
