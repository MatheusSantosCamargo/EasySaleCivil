(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('LoteDialogController', LoteDialogController);

    LoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Lote', 'Imobiliaria', 'Empreendimento', 'SituacaoLote'];

    function LoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Lote, Imobiliaria, Empreendimento, SituacaoLote) {
        var vm = this;

        vm.lote = entity;
        vm.clear = clear;
        vm.save = save;
        vm.imobiliarias = Imobiliaria.query();
        vm.empreendimentos = Empreendimento.query();
        vm.situacaolotes = SituacaoLote.query({filter: 'lote-is-null'});
        $q.all([vm.lote.$promise, vm.situacaolotes.$promise]).then(function() {
            if (!vm.lote.situacaoLote || !vm.lote.situacaoLote.id) {
                return $q.reject();
            }
            return SituacaoLote.get({id : vm.lote.situacaoLote.id}).$promise;
        }).then(function(situacaoLote) {
            vm.situacaolotes.push(situacaoLote);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lote.id !== null) {
                Lote.update(vm.lote, onSaveSuccess, onSaveError);
            } else {
                Lote.save(vm.lote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('easySaleCivilApp:loteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
