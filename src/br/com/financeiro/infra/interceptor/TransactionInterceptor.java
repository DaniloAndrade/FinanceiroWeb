package br.com.financeiro.infra.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.financeiro.exceptions.DAOException;
import br.com.financeiro.infra.Transactional;

@Transactional
@Interceptor
public class TransactionInterceptor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception{
		Object resultado = null;
		em.getTransaction().begin();
		System.out.println("iniciando transação...");
		try{
			resultado = ctx.proceed();
			em.getTransaction().commit();
			em.clear();
			System.out.println("fechando transação...");
			return resultado;
		
		}catch(PersistenceException e){
			System.out.println("transação não efetivada: "+ e.getMessage());
			em.getTransaction().rollback();
			throw new DAOException("transação não efetivada",e);
		}
			
	}
}
