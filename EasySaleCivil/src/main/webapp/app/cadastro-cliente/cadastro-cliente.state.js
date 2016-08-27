(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cadastro-cliente', {
            parent: 'app',
            url: '/cadastro-cliente',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.cadastroCliente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/cadastro-cliente/cadastro-clientes.html',
                    controller: 'CadastroClienteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cadastroCliente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cadastro-cliente-detail', {
            parent: 'app',
            url: '/cadastro-cliente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'easySaleCivilApp.cadastroCliente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/cadastro-cliente/cadastro-cliente-detail.html',
                    controller: 'CadastroClienteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cadastroCliente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CadastroCliente', function($stateParams, CadastroCliente) {
                    return CadastroCliente.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('cadastro-cliente.new', {
            parent: 'cadastro-cliente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/cadastro-cliente/cadastro-cliente-dialog.html',
                    controller: 'CadastroClienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                cpf_cpnj: null,
                                rg: null,
                                rua: null,
                                bairro: null,
                                numero: null,
                                complemento: null,
                                cidade: null,
                                cep: null,
                                estado: null,
                                telefone: null,
                                celular: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cadastro-cliente', null, { reload: true });
                }, function() {
                    $state.go('cadastro-cliente');
                });
            }]
        })
        .state('cadastro-cliente.edit', {
            parent: 'cadastro-cliente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/cadastro-cliente/cadastro-cliente-dialog.html',
                    controller: 'CadastroClienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CadastroCliente', function(CadastroCliente) {
                            return CadastroCliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cadastro-cliente', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cadastro-cliente.delete', {
            parent: 'cadastro-cliente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/cadastro-cliente/cadastro-cliente-delete-dialog.html',
                    controller: 'CadastroClienteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CadastroCliente', function(CadastroCliente) {
                            return CadastroCliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cadastro-cliente', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
