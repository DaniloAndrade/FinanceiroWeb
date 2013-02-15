package br.com.financeiro.negocio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import br.com.financeiro.entitys.Cheque;
import br.com.financeiro.entitys.ChequeID;
import br.com.financeiro.entitys.Conta;
import br.com.financeiro.entitys.Lancamento;
import br.com.financeiro.exceptions.NegocioException;
import br.com.financeiro.infra.Transactional;
import br.com.financeiro.repository.IChequeRepository;

public class ChequeNegocio implements Serializable{
	
	@Inject
	private IChequeRepository repository;
	
	public void salvar(Cheque cheque){
		this.repository.salvar(cheque);
	}
	
	
	public long salvarSequencia(Conta conta, Long chequeInicial, Long chequeFinal){
		Cheque cheque = null;
		Cheque chequeVerifica = null;
		ChequeID chequeID = null;
		long contaTotal = 0;
		
		for (long i = chequeInicial; i < chequeFinal; i++) {
			chequeID = new ChequeID();
			chequeID.setNumero(i);
			chequeID.setCodigoConta(conta.getCodigo());
			chequeVerifica = this.carregar(chequeID);
			
			if(chequeVerifica == null){
				cheque = new Cheque();
				cheque.setChequeID(chequeID);
				cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
				cheque.setDataCadastro(Calendar.getInstance().getTime());
				this.salvar(cheque);
				contaTotal++;
			}
		}
		return contaTotal;
	}


	public Cheque carregar(ChequeID chequeID) {
		return this.repository.carregar(chequeID);
	}
	
	public void excluir(Cheque cheque) throws NegocioException{
		if(cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO){
			this.repository.excluir(cheque);
		}else{
			throw new NegocioException("Não é possivel excluir cheque, situação não permite esta operação.");
		}
	}
	
	
	public List<Cheque> listar(Conta conta){
		return this.repository.listar(conta);
	}
	
	
	public void cancelarCheque(Cheque cheque) throws NegocioException{
		if(cheque.isSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO) 
				|| cheque.isSituacao(Cheque.SITUACAO_CHEQUE_CANCELADO)){
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_CANCELADO);
			this.repository.salvar(cheque);
		}else{
			throw new NegocioException("Não é possivel cancelar cheque, situação não permite esta operação.");
		}
	}
	
	public void baixarCheque(ChequeID chequeID, Lancamento lancamento){
		Cheque cheque = this.repository.carregar(chequeID);
		if(cheque != null){
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_BAIXADO);
			cheque.setLancamento(lancamento);
			this.repository.salvar(cheque);
		}
	}
	
	
	public void desvinculaLancamento(Conta conta, Long numeroCheque)throws NegocioException{
		ChequeID chequeID = new ChequeID();
		chequeID.setCodigoConta(conta.getCodigo());
		chequeID.setNumero(numeroCheque);
		Cheque  cheque = this.carregar(chequeID);
		
		if(cheque.isSituacao(Cheque.SITUACAO_CHEQUE_CANCELADO)){
			throw new NegocioException("Não é possível usar cheque cancelado.");
		}else{
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
			Lancamento lancamento = cheque.getLancamento();
			lancamento.setCheque(null);
			cheque.setLancamento(null);
			this.salvar(cheque);
		}
	}
}
