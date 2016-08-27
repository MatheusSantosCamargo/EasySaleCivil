(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('LoteDetailController', LoteDetailController);

    LoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Lote', 'Imobiliaria', 'Empreendimento', 'SituacaoLote'];

    function LoteDetailController($scope, $rootScope, $stateParams, entity, Lote, Imobiliaria, Empreendimento, SituacaoLote) {
        var vm = this;

        vm.lote = entity;

        var unsubscribe = $rootScope.$on('easySaleCivilApp:loteUpdate', function(event, result) {
            vm.lote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
