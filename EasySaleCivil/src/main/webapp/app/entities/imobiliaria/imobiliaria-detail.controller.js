(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('ImobiliariaDetailController', ImobiliariaDetailController);

    ImobiliariaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Imobiliaria'];

    function ImobiliariaDetailController($scope, $rootScope, $stateParams, entity, Imobiliaria) {
        var vm = this;

        vm.imobiliaria = entity;

        var unsubscribe = $rootScope.$on('easySaleCivilApp:imobiliariaUpdate', function(event, result) {
            vm.imobiliaria = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
