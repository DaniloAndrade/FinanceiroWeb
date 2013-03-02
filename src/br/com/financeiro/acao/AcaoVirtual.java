package br.com.financeiro.acao;

import java.math.BigDecimal;

import br.com.financeiro.entitys.Acao;

public class AcaoVirtual {

	private Acao acao = new Acao();
	
	private BigDecimal ultimoPreco;
	
	private BigDecimal total;

	public BigDecimal getUltimoPreco() {
		return ultimoPreco;
	}

	public void setUltimoPreco(BigDecimal ultimoPreco) {
		this.ultimoPreco = ultimoPreco;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Acao getAcao() {
		return acao;
	}
	
	public void setAcao(Acao acao) {
		this.acao = acao;
	}
	
}
