(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('ImobiliariaDialogController', ImobiliariaDialogController);

    ImobiliariaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Imobiliaria'];

    function ImobiliariaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Imobiliaria) {
        var vm = this;

        vm.imobiliaria = entity;
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
            if (vm.imobiliaria.id !== null) {
                Imobiliaria.update(vm.imobiliaria, onSaveSuccess, onSaveError);
            } else {
                Imobiliaria.save(vm.imobiliaria, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easySaleCivilApp:imobiliariaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
