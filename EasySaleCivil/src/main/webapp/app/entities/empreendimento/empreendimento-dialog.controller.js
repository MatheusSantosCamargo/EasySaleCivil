(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('EmpreendimentoDialogController', EmpreendimentoDialogController);

    EmpreendimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Empreendimento'];

    function EmpreendimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Empreendimento) {
        var vm = this;

        vm.empreendimento = entity;
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
            if (vm.empreendimento.id !== null) {
                Empreendimento.update(vm.empreendimento, onSaveSuccess, onSaveError);
            } else {
                Empreendimento.save(vm.empreendimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easySaleCivilApp:empreendimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
