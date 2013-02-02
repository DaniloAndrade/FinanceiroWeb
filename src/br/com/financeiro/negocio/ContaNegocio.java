package br.com.financeiro.negocio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.repository.ContaRepository;

@RequestScoped
public class ContaNegocio  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContaRepository repository;
	
	public List<Conta> listar(Usuario usuario){
		return repository.listar(usuario);
	}
	
	public Conta carregar(Long codigo){
		return repository.buscaPorId(codigo);
	}
	
	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		repository.atualiza(conta);
	}
	
	
	public void  excluir(Conta conta) {
		repository.remove(conta);
	}
	
	public void tornarFavorita(Conta contaFavorita){
		Conta conta = repository.buscarFavorita(contaFavorita.getUsuario());
		if(conta!= null){
			conta.setFavorita(false);
			repository.atualiza(conta);
		}else{
			contaFavorita.setFavorita(true);
			repository.atualiza(contaFavorita);
		}
		
	}
	
	public Conta buscarFavorita(Usuario usuario){
		return repository.buscarFavorita(usuario);
	}
}
