package br.com.financeiro.entitys;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@NamedQueries(value={@NamedQuery(name="buscarPorConta" , query="Select ch from Cheque ch where ch.conta = :conta ")})
public class Cheque {
	
	public static final char SITUACAO_CHEQUE_BAIXADO = 'B';
	public static final char SITUACAO_CHEQUE_CANCELADO = 'C';
	public static final char SITUACAO_CHEQUE_NAO_EMITIDO = 'N';
	public static final String BUSCAR_POR_CONTA = "buscarPorConta";
	
	@EmbeddedId
	private ChequeID chequeID;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade={CascadeType.MERGE})
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(name = "codigoConta", insertable=false, updatable = false )
	@ForeignKey(name="fk_cheque_conta")
	private Conta conta;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(nullable=false, precision=1)
	private Character situacao;
	
	@OneToOne(fetch = FetchType.LAZY , cascade={CascadeType.MERGE,CascadeType.PERSIST})
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(nullable=true)
	@ForeignKey(name="fk_cheque_lancamento")
	private Lancamento lancamento;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chequeID == null) ? 0 : chequeID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cheque other = (Cheque) obj;
		if (chequeID == null) {
			if (other.chequeID != null)
				return false;
		} else if (!chequeID.equals(other.chequeID))
			return false;
		return true;
	}

	public ChequeID getChequeID() {
		return chequeID;
	}

	public void setChequeID(ChequeID chequeID) {
		this.chequeID = chequeID;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Character getSituacao() {
		return situacao;
	}

	public void setSituacao(Character situacao) {
		this.situacao = situacao;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public boolean isSituacao(char situacao) {
		return this.situacao == situacao;
	}
	
	
	
}
