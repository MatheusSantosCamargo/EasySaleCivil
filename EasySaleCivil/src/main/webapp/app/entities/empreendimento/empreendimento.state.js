(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('empreendimento', {
            parent: 'entity',
            url: '/empreendimento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.empreendimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/empreendimento/empreendimentos.html',
                    controller: 'EmpreendimentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empreendimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('empreendimento-detail', {
            parent: 'entity',
            url: '/empreendimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.empreendimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/empreendimento/empreendimento-detail.html',
                    controller: 'EmpreendimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empreendimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Empreendimento', function($stateParams, Empreendimento) {
                    return Empreendimento.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('empreendimento.new', {
            parent: 'empreendimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empreendimento/empreendimento-dialog.html',
                    controller: 'EmpreendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                localizacao: null,
                                indCondominioFechado: false,
                                distanciaCentroCidade: null,
                                caminhoImagemEmpreendimento: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('empreendimento', null, { reload: true });
                }, function() {
                    $state.go('empreendimento');
                });
            }]
        })
        .state('empreendimento.edit', {
            parent: 'empreendimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empreendimento/empreendimento-dialog.html',
                    controller: 'EmpreendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Empreendimento', function(Empreendimento) {
                            return Empreendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('empreendimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('empreendimento.delete', {
            parent: 'empreendimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/empreendimento/empreendimento-delete-dialog.html',
                    controller: 'EmpreendimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Empreendimento', function(Empreendimento) {
                            return Empreendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('empreendimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
