package br.com.financeiro.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Lancamento;

public class LancamentoRepository implements ILancamentoRepository {
	
	@Inject
	private DAO<Lancamento> dao;
	
	
	@Override
	public void salvar(Lancamento lancamento) {
		dao.atualiza(lancamento);
	}

	@Override
	public void excluir(Lancamento lancamento) {
		dao.remove(lancamento);
	}

	@Override
	public Lancamento carregar(Long id) {
		return dao.buscaPorId(id);
	}

	@Override
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		TypedQuery<Lancamento> query = null;
		if(dataInicio!=null && dataFim != null ){
			query = dao.getEntityManager().createNamedQuery(Lancamento.BUSCAR_POR_PERIODO_E_CONTA, Lancamento.class);
			query.setParameter("dataInicio", dataInicio);
			query.setParameter("dataFim", dataFim);
		}else if(dataInicio != null){
			query = dao.getEntityManager().createNamedQuery(Lancamento.BUSCAR_POR_DATA_MAIOR_IGUAL_E_CONTA, Lancamento.class);
			query.setParameter("data", dataInicio);
		}else if(dataFim !=null){
			query = dao.getEntityManager().createNamedQuery(Lancamento.BUSCAR_POR_DATA_MENOR_IGUAL_E_CONTA, Lancamento.class);
			query.setParameter("data", dataFim);
		}
		
		query.setParameter("conta", conta);
		return query.getResultList();
	}

	@Override
	public BigDecimal saldo(Conta conta, Date data) {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(l.valor * c.fator) ");
		sql.append(" from Lancamento l, Categoria c");
		sql.append(" where l.categoria = c ");
		sql.append(" and l.conta = :conta ");
		sql.append(" and l.data <= :data ");
		
		Query query = dao.getEntityManager().createQuery(sql.toString());
		query.setParameter("conta", conta);
		query.setParameter("data", data);
		
		BigDecimal saldo = (BigDecimal) query.getSingleResult();
		
		if(saldo!=null){
			return saldo;
		}
		
		return BigDecimal.ZERO;
	}

	@Override
	public Object carregarObjetos(Object object) {
		return dao.carregaDependecia(object);
	}
	
	
	

}
