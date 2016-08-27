(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('EmpreendimentoDetailController', EmpreendimentoDetailController);

    EmpreendimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Empreendimento'];

    function EmpreendimentoDetailController($scope, $rootScope, $stateParams, entity, Empreendimento) {
        var vm = this;

        vm.empreendimento = entity;

        var unsubscribe = $rootScope.$on('easySaleCivilApp:empreendimentoUpdate', function(event, result) {
            vm.empreendimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
