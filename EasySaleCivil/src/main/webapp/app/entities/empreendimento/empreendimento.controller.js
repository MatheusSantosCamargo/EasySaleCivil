(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('EmpreendimentoController', EmpreendimentoController);

    EmpreendimentoController.$inject = ['$scope', '$state', 'Empreendimento', 'EmpreendimentoSearch'];

    function EmpreendimentoController ($scope, $state, Empreendimento, EmpreendimentoSearch) {
        var vm = this;
        
        vm.empreendimentos = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Empreendimento.query(function(result) {
                vm.empreendimentos = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmpreendimentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.empreendimentos = result;
            });
        }    }
})();
