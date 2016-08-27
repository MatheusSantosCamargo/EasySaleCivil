(function() {
    'use strict';

    angular
        .module('easySaleCivilApp')
        .controller('ConsultaEmpreendimentoController', ConsultaEmpreendimentoController);

    ConsultaEmpreendimentoController.$inject = ['Empreendimento','ConsultaEmpreendimento'];

    function ConsultaEmpreendimentoController(Empreendimento, ConsultaEmpreendimento) {
        var vm = this;
        vm.empreendimentos = [];
        vm.empreendimentoEscolhido = [];
        vm.lotes = [];
        vm.loteEscolhido = null;
        vm.imagem = "../content/images/hipster.png";
        vm.verificaComboPreenchido = verificaComboPreenchido;
        vm.buscaLotePorEmpreendimento = buscaLotePorEmpreendimento
        
        loadAllEmprendimentos();
        
        function loadAllEmprendimentos() {
            Empreendimento.query(function(result) {
            	vm.empreendimentos = result;
            });
        }
        
        function buscaLotePorEmpreendimento() {
        	ConsultaEmpreendimento.get({query: vm.empreendimentoEscolhido.id}, function(result) {
                vm.lotes = result;
            });
        }
        
        function verificaComboPreenchido(){
        	if (vm.empreendimentoEscolhido != null){
        		return true;
        	}
        	else {
        		return false;
        	}
        }
    }
})();
