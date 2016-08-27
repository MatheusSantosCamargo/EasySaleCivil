'use strict';

describe('Controller Tests', function() {

    describe('Corretor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCorretor, MockImobiliaria;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCorretor = jasmine.createSpy('MockCorretor');
            MockImobiliaria = jasmine.createSpy('MockImobiliaria');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Corretor': MockCorretor,
                'Imobiliaria': MockImobiliaria
            };
            createController = function() {
                $injector.get('$controller')("CorretorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'easySaleCivilApp:corretorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
