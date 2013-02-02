package br.com.financeiro.repository;

import java.lang.reflect.ParameterizedType;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class DAOFactory {
	
	@Inject
	private EntityManager em;
	
	public DAOFactory() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Produces @Dependent
	public DAO create(final InjectionPoint injectionPoint){
		ParameterizedType parameterizedType = (ParameterizedType) injectionPoint.getType();
		Class classe = (Class) parameterizedType.getActualTypeArguments()[0];
		return new DAO(classe, em);
	}
}
