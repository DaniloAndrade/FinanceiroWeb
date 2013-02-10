package br.com.financeiro.entitys;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChequeID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private Long numero;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private Long codigoConta;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getCodigoConta() {
		return codigoConta;
	}

	public void setCodigoConta(Long codigoConta) {
		this.codigoConta = codigoConta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoConta == null) ? 0 : codigoConta.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		ChequeID other = (ChequeID) obj;
		if (codigoConta == null) {
			if (other.codigoConta != null)
				return false;
		} else if (!codigoConta.equals(other.codigoConta))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	

}
