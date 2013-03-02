package br.com.financeiro.negocio;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.entitys.enums.Permissao;
import br.com.financeiro.exceptions.NegocioException;
import br.com.financeiro.exceptions.UtilException;
import br.com.financeiro.repository.ContaRepository;
import br.com.financeiro.repository.UsuarioRepository;
import br.com.financeiro.util.EmailUtil;
import br.com.financeiro.util.MensagemUtil;

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
	
	public void enviarEmailPosCadastramento(Usuario usuario) throws NegocioException{
		String [] info = usuario.getIdioma().getCodigoISO().split("_");
		Locale locale = new Locale(info[0],info[1]);
		String titulo = MensagemUtil.getMensagem(locale, "email_titulo");
		String mensagem = MensagemUtil.getMensagem(locale, "email_mensagem",usuario.getNome(),usuario.getLogin(),usuario.getSenha());
		try{
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.enviarEmail(null, usuario.getEmail(), titulo, mensagem);
			
		}catch(UtilException e){
			throw new NegocioException(e);
		}
	}
}
