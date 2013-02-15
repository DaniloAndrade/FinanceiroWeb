package br.com.financeiro.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;



import br.com.financeiro.entitys.Categoria;
import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Lancamento;
import br.com.financeiro.entitys.Usuario;
import br.com.financeiro.repository.ILancamentoRepository;

@RequestScoped
public class LancamentoNegocio implements Serializable{
	
	@Inject
	private ILancamentoRepository lancamentoRepository;
	
	
	
	public Lancamento salvar(Lancamento  lancamento){
		if(lancamento.getId() == 0){
			lancamento.setId(null);
		}
		Categoria categoria = (Categoria) lancamentoRepository.carregarObjetos(lancamento.getCategoria());
		Conta conta = (Conta) lancamentoRepository.carregarObjetos(lancamento.getConta());
		Usuario usuario = (Usuario) lancamentoRepository.carregarObjetos(lancamento.getUsuario());
		lancamento.setCategoria(categoria);
		lancamento.setConta(conta);
		lancamento.setUsuario(usuario);
		return lancamentoRepository.salvar(lancamento);
	}
	
	
	
	public void excluir(Lancamento lancamento){
		lancamentoRepository.excluir(lancamento);
	}
	
	
	public Lancamento carregar(Long id){
		return lancamentoRepository.carregar(id);
	}
	
	
	public BigDecimal saldo(Conta conta, Date data){
		BigDecimal saldoInicial = conta.getSaldoInicial();
		BigDecimal saldoNaData = lancamentoRepository.saldo(conta, data);
		return saldoInicial.add(saldoNaData);
	}
	
	
	public List<Lancamento> listar(Conta conta, Date inicio, Date fim){
		return lancamentoRepository.listar(conta, inicio, fim);
	}
}
