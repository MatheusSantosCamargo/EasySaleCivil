(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lote', {
            parent: 'entity',
            url: '/lote',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.lote.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lote/lotes.html',
                    controller: 'LoteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lote');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lote-detail', {
            parent: 'entity',
            url: '/lote/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.lote.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lote/lote-detail.html',
                    controller: 'LoteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lote');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Lote', function($stateParams, Lote) {
                    return Lote.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('lote.new', {
            parent: 'lote',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lote/lote-dialog.html',
                    controller: 'LoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                rua: null,
                                tamanhoTerreno: null,
                                tamanhoFrenteTerreno: null,
                                distanciaPortaria: null,
                                distanciaEsquinaMaisProxima: null,
                                elevacaoTerreno: null,
                                posicaoRelacaoAoSol: null,
                                distanciaAreaLazer: null,
                                quadra: null,
                                valorTotal: null,
                                valorEntrada: null,
                                taxaJuros: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lote', null, { reload: true });
                }, function() {
                    $state.go('lote');
                });
            }]
        })
        .state('lote.edit', {
            parent: 'lote',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lote/lote-dialog.html',
                    controller: 'LoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lote', function(Lote) {
                            return Lote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lote', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lote.delete', {
            parent: 'lote',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lote/lote-delete-dialog.html',
                    controller: 'LoteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lote', function(Lote) {
                            return Lote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lote', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
