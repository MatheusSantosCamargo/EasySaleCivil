(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('SituacaoLoteDetailController', SituacaoLoteDetailController);

    SituacaoLoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SituacaoLote'];

    function SituacaoLoteDetailController($scope, $rootScope, $stateParams, entity, SituacaoLote) {
        var vm = this;

        vm.situacaoLote = entity;

        var unsubscribe = $rootScope.$on('easySaleCivilApp:situacaoLoteUpdate', function(event, result) {
            vm.situacaoLote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
