package br.com.financeiro.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Lancamento;

public interface ILancamentoRepository {
	
	void salvar(Lancamento lancamento);
	void excluir(Lancamento lancamento);
	Lancamento carregar(Long id);
	List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim);
	BigDecimal saldo(Conta conta, Date data);
	
	Object carregarObjetos(Object object);
}
