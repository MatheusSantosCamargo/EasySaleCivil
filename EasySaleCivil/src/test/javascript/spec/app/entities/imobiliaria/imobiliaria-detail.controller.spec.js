'use strict';

describe('Controller Tests', function() {

    describe('Imobiliaria Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockImobiliaria;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockImobiliaria = jasmine.createSpy('MockImobiliaria');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Imobiliaria': MockImobiliaria
            };
            createController = function() {
                $injector.get('$controller')("ImobiliariaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'easySaleCivilApp:imobiliariaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
