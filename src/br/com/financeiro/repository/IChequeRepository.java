package br.com.financeiro.repository;

import java.util.List;

import br.com.financeiro.entitys.Cheque;
import br.com.financeiro.entitys.ChequeID;
import br.com.financeiro.entitys.Conta;

public interface IChequeRepository {
	
	void salvar(Cheque cheque);
	void excluir(Cheque cheque);
	Cheque carregar(ChequeID id);
	List<Cheque> listar(Conta conta);
}
