(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('CorretorDetailController', CorretorDetailController);

    CorretorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Corretor', 'Imobiliaria'];

    function CorretorDetailController($scope, $rootScope, $stateParams, entity, Corretor, Imobiliaria) {
        var vm = this;

        vm.corretor = entity;

        var unsubscribe = $rootScope.$on('easySaleCivilApp:corretorUpdate', function(event, result) {
            vm.corretor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
