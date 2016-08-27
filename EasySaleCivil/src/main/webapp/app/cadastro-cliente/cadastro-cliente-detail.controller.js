(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CadastroClienteDetailController', CadastroClienteDetailController);

    CadastroClienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CadastroCliente'];

    function CadastroClienteDetailController($scope, $rootScope, $stateParams, entity, CadastroCliente) {
        var vm = this;

        vm.cadastroCliente = entity;

        var unsubscribe = $rootScope.$on('easySaleCivilApp:cadastroClienteUpdate', function(event, result) {
            vm.cadastroCliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
