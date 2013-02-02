package br.com.financeiro.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.financeiro.entitys.Categoria;
import br.com.financeiro.entitys.Usuario;

public class CategoriaRepository implements ICategoriaRepository{
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private DAO<Categoria> dao;

	@Override
	public Categoria salvar(Categoria categoria) {
		//dao.adiciona(categoria);
		//return categoria;
		return dao.atualiza(categoria);
	}

	@Override
	public void excluir(Categoria categoria) {
		dao.remove(categoria);
	}

	@Override
	public Categoria carregar(Integer codigo) {
		return dao.buscaPorId(codigo);
	}

	@Override
	public List<Categoria> listar(Usuario usuario) {
		TypedQuery<Categoria> query = dao.getEntityManager().createNamedQuery(Categoria.BUSCAR_POR_USUARIO, Categoria.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

	@Override
	public Categoria atualiza(Categoria categoria) {
		return dao.atualiza(categoria);
	}
	
	

}
