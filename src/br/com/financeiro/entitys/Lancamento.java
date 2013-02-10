package br.com.financeiro.entitys;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@NamedQueries(value={
		@NamedQuery(name="buscarPorPeriodoEConta" ,query="select l from Lancamento l where l.conta = :conta and (l.data between :dataInicio and :dataFim) order by l.data asc"),
		@NamedQuery(name="buscarPorDataMaioIgualEConta", query="select l from Lancamento l where l.conta = :conta and l.data >= :data order by l.data asc"),
		@NamedQuery(name="buscarPorDataMenorIgualEConta", query="select l from Lancamento l where l.conta = :conta and l.data <= :data order by l.data asc")})
public class Lancamento {

	public final static String BUSCAR_POR_PERIODO_E_CONTA = "buscarPorPeriodoEConta";
	public final static String BUSCAR_POR_DATA_MAIOR_IGUAL_E_CONTA = "buscarPorDataMaioIgualEConta";
	public final static String BUSCAR_POR_DATA_MENOR_IGUAL_E_CONTA = "buscarPorDataMenorIgualEConta";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE,CascadeType.DETACH})
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false )
	@ForeignKey(name="fk_lancamento_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.MERGE,CascadeType.DETACH})
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false )
	@ForeignKey(name="fk_lancamento_categoria")
	private Categoria categoria;
	
	@ManyToOne(fetch=FetchType.LAZY , cascade={CascadeType.MERGE,CascadeType.DETACH})
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false )
	@ForeignKey(name="fk_lancamento_conta")
	private Conta conta;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="lancamento")
	private Cheque cheque;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private String descricao;
	
	@Column(precision=10 , scale=2)
	private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	
	public Cheque getCheque() {
		return cheque;
	}
	
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((conta == null) ? 0 : conta.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Lancamento other = (Lancamento) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (conta == null) {
			if (other.conta != null)
				return false;
		} else if (!conta.equals(other.conta))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	
	
}
