package br.com.financeiro.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.financeiro.entitys.Acao;
import br.com.financeiro.entitys.Usuario;

public class AcaoRepository implements IAcaoRepository {

	@Inject
	private DAO<Acao> dao;
	
	@Override
	public Acao salvar(Acao acao) {
		return dao.atualiza(acao);
	}

	@Override
	public void excluir(Acao acao) {
		dao.remove(acao);
	}

	@Override
	public Acao carregar(String codigo) {
		return dao.buscaPorId(codigo);
	}

	@Override
	public List<Acao> listar(Usuario usuario) {
		TypedQuery<Acao> query = dao.getEntityManager().createNamedQuery(Acao.BUSCAR_POR_USUARIO,Acao.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

}
