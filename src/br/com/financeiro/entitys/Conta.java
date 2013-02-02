package br.com.financeiro.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(value= {
						@NamedQuery(name="listarPorUsuario", query="select c from Conta c where c.usuario = :usuario"),
						@NamedQuery(name="buscarPorFavoritaDoUsuario" ,query = "select c from Conta c where c.usuario = :usuario and c.favorita = true")
					})
public class Conta implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String LISTAR_POR_USUARIO = "listarPorUsuario";
	public static String BUSCAR_POR_FAVORITA_USUARIO = "buscarPorFavoritaDoUsuario";
	
	
	@Id
	@GeneratedValue
	private Long codigo;
	
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name = "codigo_usuario",nullable=false)
	private Usuario usuario;
	
	@Column(nullable=false,updatable=false)
	private Date dataCadastro;
	
	@Column(precision=9 , scale = 2 )
	private BigDecimal saldoInicial;
	
	private String descricao;
	
	private boolean favorita;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isFavorita() {
		return favorita;
	}

	public void setFavorita(boolean favorita) {
		this.favorita = favorita;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + (favorita ? 1231 : 1237);
		result = prime * result
				+ ((saldoInicial == null) ? 0 : saldoInicial.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Conta other = (Conta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (favorita != other.favorita)
			return false;
		if (saldoInicial == null) {
			if (other.saldoInicial != null)
				return false;
		} else if (!saldoInicial.equals(other.saldoInicial))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	
	
	
	
}
