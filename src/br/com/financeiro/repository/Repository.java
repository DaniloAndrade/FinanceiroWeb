package br.com.financeiro.repository;

import java.util.List;

public interface Repository<T> {

	public void adiciona(T t);

	public void remove(T t);

	public T atualiza(T t);

	public List<T> listaTodos();
	
	public Long contaTodos();

	public List<T> listaTodosPaginada(int firstResult, int maxResults);

	public T buscaPorId(Long id);
	
	public T carrega(T t);
	
	public Object carregaDependecia(Object object);
	
	
}
