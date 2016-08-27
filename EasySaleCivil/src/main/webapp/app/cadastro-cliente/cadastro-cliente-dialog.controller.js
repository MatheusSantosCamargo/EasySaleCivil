(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CadastroClienteDialogController', CadastroClienteDialogController);

    CadastroClienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CadastroCliente'];

    function CadastroClienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CadastroCliente) {
        var vm = this;

        vm.cadastroCliente = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cadastroCliente.id !== null) {
                CadastroCliente.update(vm.cadastroCliente, onSaveSuccess, onSaveError);
            } else {
                CadastroCliente.save(vm.cadastroCliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easySaleCivilApp:cadastroClienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
