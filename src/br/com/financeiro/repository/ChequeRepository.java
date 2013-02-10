package br.com.financeiro.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.financeiro.entitys.Cheque;
import br.com.financeiro.entitys.ChequeID;
import br.com.financeiro.entitys.Conta;

public class ChequeRepository implements IChequeRepository {

	@Inject
	private DAO<Cheque> dao;
	
	@Override
	public void salvar(Cheque cheque) {
		this.dao.atualiza(cheque);
	}

	@Override
	public void excluir(Cheque cheque) {
		this.dao.remove(cheque);
	}

	@Override
	public Cheque carregar(ChequeID id) {
		return this.dao.buscaPorId(id);
	}

	@Override
	public List<Cheque> listar(Conta conta) {
		TypedQuery<Cheque> query = (TypedQuery<Cheque>) this.dao.getEntityManager()
				.createNamedQuery(Cheque.BUSCAR_POR_CONTA, Cheque.class);
		query.setParameter("conta", conta);
		return query.getResultList();
	}

}
