(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CadastroClienteController', CadastroClienteController);

    CadastroClienteController.$inject = ['$scope', '$state', 'CadastroCliente', 'CadastroClienteSearch'];

    function CadastroClienteController ($scope, $state, CadastroCliente, CadastroClienteSearch) {
        var vm = this;
        
        vm.cadastroClientes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CadastroCliente.query(function(result) {
                vm.cadastroClientes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CadastroClienteSearch.query({query: vm.searchQuery}, function(result) {
                vm.cadastroClientes = result;
            });
        }    }
})();
