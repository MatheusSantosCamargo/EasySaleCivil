(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('ImobiliariaDeleteController',ImobiliariaDeleteController);

    ImobiliariaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Imobiliaria'];

    function ImobiliariaDeleteController($uibModalInstance, entity, Imobiliaria) {
        var vm = this;

        vm.imobiliaria = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Imobiliaria.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
