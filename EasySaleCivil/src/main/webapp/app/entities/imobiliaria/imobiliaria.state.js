(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('imobiliaria', {
            parent: 'entity',
            url: '/imobiliaria',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.imobiliaria.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/imobiliaria/imobiliarias.html',
                    controller: 'ImobiliariaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('imobiliaria');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('imobiliaria-detail', {
            parent: 'entity',
            url: '/imobiliaria/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.imobiliaria.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/imobiliaria/imobiliaria-detail.html',
                    controller: 'ImobiliariaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('imobiliaria');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Imobiliaria', function($stateParams, Imobiliaria) {
                    return Imobiliaria.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('imobiliaria.new', {
            parent: 'imobiliaria',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imobiliaria/imobiliaria-dialog.html',
                    controller: 'ImobiliariaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                endereco: null,
                                telefone: null,
                                nomeResponsavel: null,
                                creciResponsavel: null,
                                indAtiva: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('imobiliaria', null, { reload: true });
                }, function() {
                    $state.go('imobiliaria');
                });
            }]
        })
        .state('imobiliaria.edit', {
            parent: 'imobiliaria',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imobiliaria/imobiliaria-dialog.html',
                    controller: 'ImobiliariaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Imobiliaria', function(Imobiliaria) {
                            return Imobiliaria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('imobiliaria', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('imobiliaria.delete', {
            parent: 'imobiliaria',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imobiliaria/imobiliaria-delete-dialog.html',
                    controller: 'ImobiliariaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Imobiliaria', function(Imobiliaria) {
                            return Imobiliaria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('imobiliaria', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
