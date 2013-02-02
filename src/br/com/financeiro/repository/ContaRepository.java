package br.com.financeiro.repository;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Usuario;

@RequestScoped
public class ContaRepository implements Repository<Conta>{
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private DAO<Conta> dao;
	
	@Override
	public void adiciona(Conta t) {
		dao.adiciona(t);
	}

	@Override
	public void remove(Conta t) {
		dao.remove(t);
	}

	@Override
	public Conta atualiza(Conta t) {
		return dao.atualiza(t);
	}

	@Override
	public List<Conta> listaTodos() {
		return dao.listaTodos();
	}

	@Override
	public Long contaTodos() {
		return dao.contaTodos();
	}
	
	

	@Override
	public List<Conta> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	@Override
	public Conta buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	@Override
	public Conta carrega(Conta t) {
		return dao.carrega(t);
	}

	@Override
	public Object carregaDependecia(Object object) {
		return dao.carregaDependecia(object);
	}
	
	public List<Conta> listar(Usuario usuario){
		TypedQuery<Conta> query = dao.getEntityManager().createNamedQuery(Conta.LISTAR_POR_USUARIO, Conta.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	
	public Conta buscarFavorita(Usuario usuario){
		TypedQuery<Conta> query = dao.getEntityManager().createNamedQuery(Conta.BUSCAR_POR_FAVORITA_USUARIO, Conta.class);
		query.setParameter("usuario", usuario);
		
		if( !query.getResultList().isEmpty()){
			return query.getResultList().get(0);
		}
		
		return null;
		//return query.getSingleResult();
	}

	public void removePorUsuario(Usuario usuario) {
		Query query = dao.getEntityManager().createQuery("delete from Conta c where c.usuario = :usuario");
		query.setParameter("usuario", usuario);
		query.executeUpdate();
	}

}
