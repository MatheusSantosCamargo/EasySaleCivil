(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CorretorDialogController', CorretorDialogController);

    CorretorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Corretor', 'Imobiliaria'];

    function CorretorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Corretor, Imobiliaria) {
        var vm = this;

        vm.corretor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.imobiliarias = Imobiliaria.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corretor.id !== null) {
                Corretor.update(vm.corretor, onSaveSuccess, onSaveError);
            } else {
                Corretor.save(vm.corretor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easySaleCivilApp:corretorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
