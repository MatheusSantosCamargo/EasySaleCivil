'use strict';

describe('Controller Tests', function() {

    describe('Lote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLote, MockImobiliaria, MockEmpreendimento, MockSituacaoLote;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLote = jasmine.createSpy('MockLote');
            MockImobiliaria = jasmine.createSpy('MockImobiliaria');
            MockEmpreendimento = jasmine.createSpy('MockEmpreendimento');
            MockSituacaoLote = jasmine.createSpy('MockSituacaoLote');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Lote': MockLote,
                'Imobiliaria': MockImobiliaria,
                'Empreendimento': MockEmpreendimento,
                'SituacaoLote': MockSituacaoLote
            };
            createController = function() {
                $injector.get('$controller')("LoteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'easySaleCivilApp:loteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
