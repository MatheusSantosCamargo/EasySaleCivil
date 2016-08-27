(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CadastroClienteDeleteController',CadastroClienteDeleteController);

    CadastroClienteDeleteController.$inject = ['$uibModalInstance', 'entity', 'CadastroCliente'];

    function CadastroClienteDeleteController($uibModalInstance, entity, CadastroCliente) {
        var vm = this;

        vm.cadastroCliente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CadastroCliente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
