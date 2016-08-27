'use strict';

describe('Controller Tests', function() {

    describe('SituacaoLote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSituacaoLote;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSituacaoLote = jasmine.createSpy('MockSituacaoLote');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SituacaoLote': MockSituacaoLote
            };
            createController = function() {
                $injector.get('$controller')("SituacaoLoteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'easySaleCivilApp:situacaoLoteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
