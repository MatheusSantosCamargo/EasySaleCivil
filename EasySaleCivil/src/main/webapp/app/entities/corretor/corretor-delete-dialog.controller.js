(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CorretorDeleteController',CorretorDeleteController);

    CorretorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Corretor'];

    function CorretorDeleteController($uibModalInstance, entity, Corretor) {
        var vm = this;

        vm.corretor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Corretor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
