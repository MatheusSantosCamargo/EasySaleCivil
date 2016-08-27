'use strict';

describe('Controller Tests', function() {

    describe('CadastroCliente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCadastroCliente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCadastroCliente = jasmine.createSpy('MockCadastroCliente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CadastroCliente': MockCadastroCliente
            };
            createController = function() {
                $injector.get('$controller')("CadastroClienteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'easySaleCivilApp:cadastroClienteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
