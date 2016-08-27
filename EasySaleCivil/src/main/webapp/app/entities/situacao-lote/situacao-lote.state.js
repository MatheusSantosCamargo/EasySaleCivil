(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('situacao-lote', {
            parent: 'entity',
            url: '/situacao-lote',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.situacaoLote.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/situacao-lote/situacao-lotes.html',
                    controller: 'SituacaoLoteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('situacaoLote');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('situacao-lote-detail', {
            parent: 'entity',
            url: '/situacao-lote/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.situacaoLote.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/situacao-lote/situacao-lote-detail.html',
                    controller: 'SituacaoLoteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('situacaoLote');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SituacaoLote', function($stateParams, SituacaoLote) {
                    return SituacaoLote.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('situacao-lote.new', {
            parent: 'situacao-lote',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/situacao-lote/situacao-lote-dialog.html',
                    controller: 'SituacaoLoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                situacao: null,
                                cor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('situacao-lote', null, { reload: true });
                }, function() {
                    $state.go('situacao-lote');
                });
            }]
        })
        .state('situacao-lote.edit', {
            parent: 'situacao-lote',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/situacao-lote/situacao-lote-dialog.html',
                    controller: 'SituacaoLoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SituacaoLote', function(SituacaoLote) {
                            return SituacaoLote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('situacao-lote', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('situacao-lote.delete', {
            parent: 'situacao-lote',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/situacao-lote/situacao-lote-delete-dialog.html',
                    controller: 'SituacaoLoteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SituacaoLote', function(SituacaoLote) {
                            return SituacaoLote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('situacao-lote', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
