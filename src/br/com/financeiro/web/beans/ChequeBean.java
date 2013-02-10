package br.com.financeiro.web.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.financeiro.entitys.Cheque;
import br.com.financeiro.entitys.Conta;
import br.com.financeiro.exceptions.NegocioException;
import br.com.financeiro.infra.Transactional;
import br.com.financeiro.negocio.ChequeNegocio;
import br.com.financeiro.util.MensagemUtil;

@Model
public class ChequeBean {
	
	@Inject
	private ChequeNegocio chequeNegocio;
	
	@Inject
	private ContextoBean contextoBean;
	
	private Cheque selecionado = new Cheque();
	private List<Cheque> lista= null;
	private Long chequeInicial;
	private Long chequeFinal;
	
	@Transactional
	public void salvar(){
		FacesContext context = FacesContext.getCurrentInstance();
		Conta conta = contextoBean.getContaAtiva();
		
		long totalCheques = 0;
		
		if(this.chequeInicial == null || this.chequeFinal == null){
			String texto = MensagemUtil.getMensagem("cheque_erro_sequencia");
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
		}else if(this.chequeFinal.longValue()<this.chequeInicial.longValue()){
			String texto = MensagemUtil.getMensagem("cheque_erro_inicial_final",
												this.chequeInicial,this.chequeFinal);
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
			
		}else{
			totalCheques = chequeNegocio.salvarSequencia(conta, chequeInicial, chequeFinal);
			String texto = MensagemUtil.getMensagem("cheque_info_cadastro",
					this.chequeInicial,this.chequeFinal);
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
			this.lista = null;
			
		}
	}
	
	@Transactional
	public void excluir(){
		try{
			chequeNegocio.excluir(selecionado);
		}catch(NegocioException e){
			FacesContext context = FacesContext.getCurrentInstance();
			String texto = MensagemUtil.getMensagem("cheque_erro_excluir");
			FacesMessage msg = new FacesMessage(texto);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
		}
		this.lista = null;
	}

	@Transactional
	public void cancelar(){
		try{
			chequeNegocio.cancelarCheque(selecionado);
		}catch(NegocioException e){
			FacesContext context = FacesContext.getCurrentInstance();
			String texto = MensagemUtil.getMensagem("cheque.erro.cancelar");
			FacesMessage msg = new FacesMessage(texto);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
		}
		this.lista = null;
	}
	
	public List<Cheque> getLista(){
		if(lista == null){
			Conta conta = contextoBean.getContaAtiva();
			this.lista = chequeNegocio.listar(conta);
		}
		
		return this.lista;
	}
	
	
	public Cheque getSelecionado() {
		return selecionado;
	}
	
	public void setSelecionado(Cheque selecionado) {
		this.selecionado = selecionado;
	}
	
	public Long getChequeFinal() {
		return chequeFinal;
	}
	public void setChequeFinal(Long chequeFinal) {
		this.chequeFinal = chequeFinal;
	}
	
	public Long getChequeInicial() {
		return chequeInicial;
	}
	
	public void setChequeInicial(Long chequeInicial) {
		this.chequeInicial = chequeInicial;
	}
}
