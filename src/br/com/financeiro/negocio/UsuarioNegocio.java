package br.com.financeiro.negocio;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.entitys.enums.Permissao;
import br.com.financeiro.repository.ContaRepository;
import br.com.financeiro.repository.UsuarioRepository;

@RequestScoped
public class UsuarioNegocio implements Serializable {
	
	@Inject
	private UsuarioRepository repository;
	
	@Inject
	private ContaRepository contaRepository;
	
	@Inject
	private CategoriaNegocio categoriaNegocio;
	
	public Usuario buscarPorId(Long id){
		return repository.buscaPorId(id);
	}
	
	public Usuario buscarPorLogin(String login){
		return repository.buscarPorLogin(login);
	}
	
	public Usuario salvar(Usuario usuario){
		
		if(usuario.getCodigo() ==null || usuario.getCodigo() ==0){
			usuario.addPermissao(Permissao.ROLE_USUARIO);
			usuario =  this.repository.atualiza(usuario);
			categoriaNegocio.salvarEstruturaPadrao(usuario);
			return usuario;
			
		}else {
			return this.repository.atualiza(usuario);
		}
	}
	
	public void excluir(Usuario usuario){
		categoriaNegocio.excluir(usuario);
		contaRepository.removePorUsuario(usuario);
		usuario.removeTodasPermissao();
		this.repository.remove(usuario);
	}
	
	public List<Usuario> listar() {
		return this.repository.listaTodos();
	}
}
