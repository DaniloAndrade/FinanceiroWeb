package br.com.financeiro.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Query;


import br.com.financeiro.entitys.Usuario;

@RequestScoped
public class UsuarioRepository implements Serializable,Repository<Usuario> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private DAO<Usuario> dao;
	
	public void salvar(Usuario usuario) {
	}

	@Override
	public void adiciona(Usuario t) {
		dao.adiciona(t);
		
	}

	@Override
	public void remove(Usuario t) {
		dao.remove(t);
	}

	@Override
	public Usuario atualiza(Usuario t) {
		return dao.atualiza(t);
	}

	@Override
	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	@Override
	public Long contaTodos() {
		return dao.contaTodos();
	}

	@Override
	public List<Usuario> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	@Override
	public Usuario buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public Usuario buscarPorLogin(String login){
		String jpql = "select u from Usuario u where u.login = :login";
		Query query = dao.getEntityManager().createQuery(jpql);
		query.setParameter("login", login);
		return (Usuario) query.getSingleResult();
	}

	@Override
	public Usuario carrega(Usuario t) {
		return dao.carrega(t);
	}

	@Override
	public Object carregaDependecia(Object object) {
		return dao.carregaDependecia(object);
	}
	
	

}
