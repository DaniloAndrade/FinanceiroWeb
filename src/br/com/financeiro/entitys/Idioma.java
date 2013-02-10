package br.com.financeiro.entitys;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.NaturalId;

import br.com.financeiro.converter.ConverterID;

@Entity @Cacheable
@NamedQuery(name="buscarPorIso", query="select i from Idioma i where i.codigoISO = :codigo")
public class Idioma implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUSCAR_POR_ISO = "buscarPorIso";

	@Id
	@GeneratedValue
	@ConverterID
	private Long codigo;
	
	@NaturalId
	private String codigoISO;
	private String descricao;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getCodigoISO() {
		return codigoISO;
	}
	public void setCodigoISO(String codigoISO) {
		this.codigoISO = codigoISO;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((codigoISO == null) ? 0 : codigoISO.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
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
		Idioma other = (Idioma) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (codigoISO == null) {
			if (other.codigoISO != null)
				return false;
		} else if (!codigoISO.equals(other.codigoISO))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
	
	
}
