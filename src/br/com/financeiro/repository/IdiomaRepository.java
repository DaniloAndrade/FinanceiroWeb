package br.com.financeiro.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.financeiro.entitys.Idioma;

public class IdiomaRepository implements IIdiomaRepository,Serializable {

	@Inject
	private DAO<Idioma> dao;
	
	@Override
	public Idioma buscarPorISO(String iso) {
		
		TypedQuery<Idioma> query = dao.getEntityManager().createNamedQuery(Idioma.BUSCAR_POR_ISO,Idioma.class);
		query.setParameter("codigo", iso);
		return query.getSingleResult();
	}

}
