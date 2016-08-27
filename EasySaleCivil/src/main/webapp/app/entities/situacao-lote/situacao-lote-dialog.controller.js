(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('SituacaoLoteDialogController', SituacaoLoteDialogController);

    SituacaoLoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SituacaoLote'];

    function SituacaoLoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SituacaoLote) {
        var vm = this;

        vm.situacaoLote = entity;
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
            if (vm.situacaoLote.id !== null) {
                SituacaoLote.update(vm.situacaoLote, onSaveSuccess, onSaveError);
            } else {
                SituacaoLote.save(vm.situacaoLote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easySaleCivilApp:situacaoLoteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
