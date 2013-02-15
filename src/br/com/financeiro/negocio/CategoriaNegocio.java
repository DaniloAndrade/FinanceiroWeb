package br.com.financeiro.negocio;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.financeiro.entitys.Categoria;
import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.repository.ICategoriaRepository;

@RequestScoped
public class CategoriaNegocio implements Serializable{
	
	@Inject
	private ICategoriaRepository repository;
	
	public List<Categoria> listar(Usuario usuario){
		return repository.listar(usuario);
	}
	
	
	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();
		
		if(pai == null){
			String msg = "A Categoria "+ categoria.getDescricao() + " deve ter um pai definido";
			throw new IllegalArgumentException(msg);
		}
		
		boolean mudouFator = pai.getFator() != categoria.getFator();
		
		categoria.setFator(pai.getFator());
		categoria = repository.salvar(categoria);
		
		if(mudouFator){
			categoria = this.carregar(categoria.getCodigo());
			this.replicarFator(categoria, categoria.getFator());
		}
		
		return categoria;
	}


	public Categoria carregar(Integer codigo) {
		return repository.carregar(codigo);
	}


	private void replicarFator(Categoria categoria, int fator) {
		if (categoria.getFilhos()!=null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				this.repository.salvar(filho);
				this.replicarFator(filho, fator);
			}
		}
	}
	
	public void  excluir(Categoria categoria) {
		
		if(categoria.getPai() == null){
			this.repository.excluir(categoria);
		}else{
			
			Categoria pai = categoria.getPai();
			pai.removeCategoria(categoria);
			
			categoria.setPai(null);
			
			this.repository.atualiza(pai);
			this.repository.excluir(categoria);
		}
		
	}
	
	
	public void  excluir(Usuario usuario) {
		List<Categoria> categorias = repository.listar(usuario);
		for (Categoria categoria : categorias) {
			this.repository.excluir(categoria);
		}
		
	}
	
	
	public void salvarEstruturaPadrao(Usuario usuario){
		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		
		
		
		despesas.addCategoria(new Categoria(null, usuario, "Moradia", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Alimetação", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Vestuário", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Deslocamento", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Cuidados Pessoais", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Educação", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Saúde", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Lazer", -1));
		despesas.addCategoria(new Categoria(null, usuario, "Despesas Financeiras", -1));
		repository.salvar(despesas);
		
		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas.addCategoria(new Categoria(null, usuario, "Salário", 1));
		receitas.addCategoria(new Categoria(null, usuario, "Restituições", 1));
		receitas.addCategoria(new Categoria(null, usuario, "Rendimento", 1));
		
		repository.salvar(receitas);
		
		/*despesas = repository.salvar(despesas);
		repository.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		repository.salvar(new Categoria(despesas, usuario, "Alimeta��o", -1));
		repository.salvar(new Categoria(despesas, usuario, "Vestu�rio", -1));
		repository.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		repository.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		repository.salvar(new Categoria(despesas, usuario, "Educa��o", -1));
		repository.salvar(new Categoria(despesas, usuario, "Sa�de", -1));
		repository.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		repository.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));
		
		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		
		repository.salvar(new Categoria(receitas, usuario, "Sal�rio", 1));
		repository.salvar(new Categoria(receitas, usuario, "Restitui��es", 1));
		repository.salvar(new Categoria(receitas, usuario, "Rendimento", 1));*/
	}
	
	
	
	
}
