package br.com.financeiro.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.financeiro.converter.ConverterID;

@Entity
@NamedQuery(name="buscarPorUsuario", query="select c from Categoria c where c.pai is null and c.usuario = :usuario")
public class Categoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BUSCAR_POR_USUARIO="buscarPorUsuario" ;
	
	@ConverterID
	@Id @GeneratedValue
	private Integer codigo;
	
	@ManyToOne
	@JoinColumn(name="categoria_pai", nullable=true)
	@ForeignKey(name="fk_categoria_categoria")
	private Categoria pai;
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST},optional=false)
	@JoinColumn(name="usuario")
	@OnDelete(action=OnDeleteAction.CASCADE)
	@ForeignKey(name="fk_categoria_usuario")
	private Usuario usuario;

	
	private String descricao;
	
	private int fator;
	
	@OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE,CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="categoria_pai",updatable=false)
	@OrderBy(value="descricao asc")
	private List<Categoria> filhos = new ArrayList<Categoria>();
	
	public Categoria() {
	}

	public Categoria(Categoria pai, Usuario usuario, String descricao, int fator) {
		super();
		this.pai = pai;
		this.usuario = usuario;
		this.descricao = descricao;
		this.fator = fator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}
	
	
	public void addCategoria(Categoria categoria){
		if(categoria!=null){
			categoria.setPai(this);
			filhos.add(categoria);
		}
	}

	public void removeCategoria(Categoria filho) {
		if(filho != null){
			this.filhos.remove(filho);
			filho.setPai(null);
			filho.setUsuario(null);
		}
	}
	
	public boolean getFatorPositivo(){
		System.out.println("#########################################################");
		System.out.println("fator é positivo: "+(fator>0));
		return fator>0 ;
	}
	
}
