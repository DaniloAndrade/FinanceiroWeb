package br.com.financeiro.negocio;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.financeiro.acao.AcaoVirtual;
import br.com.financeiro.entitys.Acao;
import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.exceptions.NegocioException;
import br.com.financeiro.repository.IAcaoRepository;
import br.com.financeiro.util.YahooFinanceUtil;

public class AcaoNegocio {
	
	@Inject
	private IAcaoRepository acaoRepository;
	
	public Acao salvar(Acao acao){
		return this.acaoRepository.salvar(acao);
	}
	
	
	public void excluir(Acao acao){
		this.acaoRepository.excluir(acao);
	}
	
	public Acao carregar(String codigo){
		return this.acaoRepository.carregar(codigo);
	}
	
	public List<Acao> listar(Usuario usuario){
		return this.acaoRepository.listar(usuario);
	}
	
	public List<AcaoVirtual> listarAcaoVirtual(Usuario usuario) throws NegocioException{
		List<Acao> listaAcao = null;
		List<AcaoVirtual> listaAcaoVirtual = new ArrayList<AcaoVirtual>();
		AcaoVirtual acaoVirtual = null;
		String cotacao = null;
		BigDecimal ultimoPreco = new BigDecimal(0.0);
		BigDecimal total = new BigDecimal(0.0);
		int quantidade = 0;
		
		try{
			listaAcao = this.listar(usuario);
			for (Acao acao : listaAcao) {
				acaoVirtual = new AcaoVirtual();
				acaoVirtual.setAcao(acao);
				cotacao = this.retornaCotacao(YahooFinanceUtil.ULTIMO_PRECO_DIA_ACAO_INDICE, acao);
				if(cotacao !=null){
					ultimoPreco = new BigDecimal(cotacao);
					quantidade = acao.getQuantidade();
					total = ultimoPreco.multiply(new BigDecimal(quantidade));
					acaoVirtual.setUltimoPreco(ultimoPreco);
					acaoVirtual.setTotal(total);
					listaAcaoVirtual.add(acaoVirtual);
				}
			}
			
			
		}catch(Exception e){
			throw new NegocioException("Não foi possível recuperar cotação. Erro: "+e.getMessage());
		}
		
		return listaAcaoVirtual;
	}


	public String retornaCotacao(int indiceInformacao, Acao acao) throws NegocioException {
		YahooFinanceUtil yahooFinanceUtil = null;
		String informacao = null;
		try{
		
			yahooFinanceUtil = new YahooFinanceUtil(acao);
			informacao = yahooFinanceUtil.retornaCotacao(indiceInformacao, acao.getSigla());
		}catch(IOException e){
			throw new NegocioException("Não foi possivel recuperar cotação. Erro: "+e.getMessage());
		}
		
		return informacao;
	}
	
	
	public String montaLinkAcao(Acao acao){
		String link = null;
		if(acao!=null){
			if(acao.getOrigem() == null){
				link = YahooFinanceUtil.INDICE_BOVESPA;
			}
			else if(acao.origemEh(YahooFinanceUtil.ORIGEM_BOVESPA)){
				link = acao.getSigla() + YahooFinanceUtil.POSFIXO_ACAO_BOVESPA;
			}else{
				link = acao.getSigla();
			}
		}else{
			link = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return link;
	}
}
