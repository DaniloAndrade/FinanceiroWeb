package br.com.financeiro.web.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.financeiro.acao.AcaoVirtual;
import br.com.financeiro.entitys.Acao;
import br.com.financeiro.exceptions.NegocioException;
import br.com.financeiro.infra.Transactional;
import br.com.financeiro.negocio.AcaoNegocio;
import br.com.financeiro.util.YahooFinanceUtil;

@Model
public class AcaoBean {

	@Inject
	private ContextoBean contextoBean;
	
	@Inject
	private AcaoNegocio acaoNegocio;
	
	private AcaoVirtual selecionada = new AcaoVirtual();
	private List<AcaoVirtual> lista = null;
	private String linkCodigoAcao = null;

	private PieChartModel modelPorValor;

	private PieChartModel modelPorQuantidade;
	
	@Transactional
	public void salvar(){
		Acao acao = this.selecionada.getAcao();
		acao.setSigla(acao.getSigla().toUpperCase());
		acao.setUsuario(contextoBean.getUsuarioLogado());
		acaoNegocio.salvar(acao);
		zerar();
	}




	private void zerar() {
		this.selecionada = new AcaoVirtual();
		this.lista= null;
		this.modelPorValor = null;
	}
	
	
	
	@Transactional
	public void excluir(){
		acaoNegocio.excluir(this.selecionada.getAcao());
		zerar();
	}
	
	
	public List<AcaoVirtual> getLista(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		try{
			if(this.lista == null){
				this.lista = acaoNegocio.listarAcaoVirtual(contextoBean.getUsuarioLogado());
			}
		}catch(NegocioException e){
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		return this.lista;
	}
	
	
	public String getLinkCodigoAcao(){
		if(this.selecionada !=null){
			this.linkCodigoAcao = acaoNegocio.montaLinkAcao(this.selecionada.getAcao());
		}else{
			this.linkCodigoAcao = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return this.linkCodigoAcao;
	}
	
	
	public AcaoVirtual getSelecionada() {
		return selecionada;
	}
	
	public ChartModel getModelPorQuantidade(){
		if(modelPorQuantidade == null){
			modelPorQuantidade = new PieChartModel();
			for (AcaoVirtual acaoVirtual : getLista()) {
				modelPorQuantidade.set(acaoVirtual.getAcao().getSigla(),acaoVirtual.getAcao().getQuantidade());
			}
		}
		return modelPorQuantidade;
	}

	
	
	public ChartModel getModelPorValor(){
		if(modelPorValor == null){
			modelPorValor = new PieChartModel();
			for (AcaoVirtual acaoVirtual : getLista()) {
				modelPorValor.set(acaoVirtual.getAcao().getSigla(),acaoVirtual.getTotal());
			}
		}
		
		return modelPorValor;
	}
	
	
	public void setSelecionada(AcaoVirtual selecionada) {
		this.selecionada = selecionada;
	}
}
