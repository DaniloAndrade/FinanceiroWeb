package br.com.financeiro.web.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

import br.com.financeiro.entitys.Categoria;
import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Lancamento;
import br.com.financeiro.infra.Transactional;
import br.com.financeiro.negocio.LancamentoNegocio;
@Named
@ViewAccessScoped
public class LancamentoBean implements Serializable{
	
	@Inject
	private ContextoBean contextoBean;
	@Inject
	private LancamentoNegocio lancamentoNegocio;
	
	private List<Lancamento> lista;
	private List<BigDecimal> saldos = new ArrayList<BigDecimal>();
	private BigDecimal saldoGeral;
	private Lancamento editado = new Lancamento();
	private List<Lancamento> listaAteHoje;
	private List<Lancamento> listaFuturos;
	
	public LancamentoBean() {
		this.novo();
	}
	
	
	public void novo(){
		this.editado = new  Lancamento();
		this.editado.setData(Calendar.getInstance().getTime());
	}
	
	public void editar(){
		
	}
	
	@Transactional
	public void salvar(){
		this.editado.setUsuario(contextoBean.getUsuarioLogado());
		this.editado.setConta(contextoBean.getContaAtiva());
		lancamentoNegocio.salvar(editado);
		this.novo();
		zeraListas();
	}


	private void zeraListas() {
		this.lista = null;
		this.saldos = new ArrayList<BigDecimal>();
	}
	
	@Transactional
	public void excluir(){
		editado = lancamentoNegocio.carregar(editado.getId());
		lancamentoNegocio.excluir(editado);
		zeraListas();
	}
	
	
	
	public List<Lancamento> getLista(){

		if(this.lista == null){
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar dataSaldo = Calendar.getInstance();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);
			
			Calendar inicio = Calendar.getInstance();
			inicio.add(Calendar.MONTH, -1);
			
			this.saldoGeral = lancamentoNegocio.saldo(conta, dataSaldo.getTime());
			this.lista = lancamentoNegocio.listar(conta, inicio.getTime(), null);
			
			Categoria categoria = null;
			BigDecimal saldo = this.saldoGeral;
			
			for (Lancamento lancamento : this.lista) {
				categoria = lancamento.getCategoria();
				
				saldo = saldo.add(lancamento.getValor().multiply(new BigDecimal(categoria.getFator())));
				this.saldos.add(saldo);
			}
			
		}
		
		return lista;
	}
	
	public List<Lancamento> getListaAteHoje(){
		if(this.listaAteHoje == null){
			Conta conta = contextoBean.getContaAtiva();
			Calendar hoje = Calendar.getInstance();
			
			this.listaAteHoje=lancamentoNegocio.listar(conta, null, hoje.getTime());
			
		}
		return this.listaAteHoje;
	}
	
	public List<Lancamento> getListaFuturos(){
		if(this.listaFuturos == null){
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar amanha = Calendar.getInstance();
			
			amanha.add(Calendar.DAY_OF_MONTH, 1);
			
			this.listaFuturos= lancamentoNegocio.listar(conta, amanha.getTime(), null);
		}
		
		return this.listaFuturos;
	}
	
	
	
	public List<BigDecimal> getSaldos() {
		return saldos;
	}
	
	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}
	
	public Lancamento getEditado() {
		return editado;
	}
	
	
	
	
}

