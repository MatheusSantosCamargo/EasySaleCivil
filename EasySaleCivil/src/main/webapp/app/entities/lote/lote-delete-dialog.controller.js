(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('LoteDeleteController',LoteDeleteController);

    LoteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lote'];

    function LoteDeleteController($uibModalInstance, entity, Lote) {
        var vm = this;

        vm.lote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Lote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
