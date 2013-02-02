package br.com.financeiro.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.negocio.ContaNegocio;
import br.com.financeiro.negocio.UsuarioNegocio;

@Named
@SessionScoped
public class ContextoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Usuario usuarioLogado = null;
	private Conta contaAtiva = null;
	
	@Inject
	private UsuarioNegocio usuarioNegocio;
	@Inject
	private ContaNegocio contaNegocio;
	
	
	public Usuario getUsuarioLogado() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		
		String login = externalContext.getRemoteUser();
		
		if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {
			if(login!=null){
				this.usuarioLogado = usuarioNegocio.buscarPorLogin(login);
				this.contaAtiva = null;
			}
		}
		
		return this.usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public Conta getContaAtiva() {
		if(this.contaAtiva == null){
			Usuario usuario = this.getUsuarioLogado();
			this.contaAtiva = contaNegocio.buscarFavorita(usuario);
			
			if(this.contaAtiva == null){
				List<Conta> contas = contaNegocio.listar(usuario);
				for (Conta conta : contas) {
					this.contaAtiva = conta;
					break;
				}
			}
			
			
		}
		return this.contaAtiva;
	}
	
	public void setContaAtiva(ValueChangeEvent event) {
		Object value = event.getNewValue();
		Long codigo = null;
		if(value instanceof String){
			codigo = Long.valueOf((String) value);
		}else{
			codigo = (Long) value;
		}
		this.contaAtiva = contaNegocio.carregar(codigo);
	}
}
