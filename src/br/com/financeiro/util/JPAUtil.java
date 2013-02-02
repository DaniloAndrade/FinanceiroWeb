package br.com.financeiro.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("financeiro");

	@Produces @RequestScoped
	public EntityManager getEntityManager() {
		System.out.println("criando EntityManager...");
		return emf.createEntityManager();
	}
	
	
	public void close(@Disposes EntityManager em){
		System.out.println("Fechando EntityManager...");
		em.close();
	}
}
