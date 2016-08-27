(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('corretor', {
            parent: 'entity',
            url: '/corretor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.corretor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/corretor/corretors.html',
                    controller: 'CorretorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corretor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('corretor-detail', {
            parent: 'entity',
            url: '/corretor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.corretor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/corretor/corretor-detail.html',
                    controller: 'CorretorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corretor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Corretor', function($stateParams, Corretor) {
                    return Corretor.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('corretor.new', {
            parent: 'corretor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/corretor/corretor-dialog.html',
                    controller: 'CorretorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                creci: null,
                                primeiroTelefone: null,
                                segundoTelefone: null,
                                primeiroEmail: null,
                                segundoEmail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('corretor', null, { reload: true });
                }, function() {
                    $state.go('corretor');
                });
            }]
        })
        .state('corretor.edit', {
            parent: 'corretor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/corretor/corretor-dialog.html',
                    controller: 'CorretorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Corretor', function(Corretor) {
                            return Corretor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('corretor', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('corretor.delete', {
            parent: 'corretor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/corretor/corretor-delete-dialog.html',
                    controller: 'CorretorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Corretor', function(Corretor) {
                            return Corretor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('corretor', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
