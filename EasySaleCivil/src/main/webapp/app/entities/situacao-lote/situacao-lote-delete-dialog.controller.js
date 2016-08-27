(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('SituacaoLoteDeleteController',SituacaoLoteDeleteController);

    SituacaoLoteDeleteController.$inject = ['$uibModalInstance', 'entity', 'SituacaoLote'];

    function SituacaoLoteDeleteController($uibModalInstance, entity, SituacaoLote) {
        var vm = this;

        vm.situacaoLote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SituacaoLote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
