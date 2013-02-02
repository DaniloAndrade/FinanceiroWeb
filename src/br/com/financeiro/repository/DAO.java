package br.com.financeiro.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DAO<T> {
	private final Class<T> classe;
	private EntityManager em;
	
	
	public DAO(Class<T> classe, EntityManager em) {
		this.classe = classe;
		this.em = em;
	}

	public void adiciona(T t) {
		em.persist(t);
	}

	public void remove(T t) {
		em.remove(em.merge(t));
	}

	public T atualiza(T t) {
		return em.merge(t);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		List<T> lista = em.createQuery(query).getResultList();
		return lista;
	}
	
	public Long contaTodos() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> alias = query.from(classe);
		query.select(builder.count(alias));
		Long result = em.createQuery(query).getSingleResult();
		return result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}

	public T buscaPorId(Serializable id) {
		T instancia = em.find(classe, id);
		return instancia;
	}
	
	public T carrega(T t){
		return em.merge(t);
	}
	public Object carregaDependecia(Object object){
		return em.merge(object);
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}

}