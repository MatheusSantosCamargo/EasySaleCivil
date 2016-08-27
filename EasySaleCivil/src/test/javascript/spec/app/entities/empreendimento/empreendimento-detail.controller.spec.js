'use strict';

describe('Controller Tests', function() {

    describe('Empreendimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEmpreendimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEmpreendimento = jasmine.createSpy('MockEmpreendimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Empreendimento': MockEmpreendimento
            };
            createController = function() {
                $injector.get('$controller')("EmpreendimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'easySaleCivilApp:empreendimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
