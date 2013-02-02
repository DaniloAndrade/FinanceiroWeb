package br.com.financeiro.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.financeiro.entitys.Categoria;
import br.com.financeiro.infra.Transactional;
import br.com.financeiro.negocio.CategoriaNegocio;


@Named
@RequestScoped
public class CategoriaBean {

	private TreeNode categoriaTree = null;
	private Categoria editada = new Categoria();
	private List<SelectItem> categoriasSelect = null;
	private boolean mostraEdicao = false;
	
	@Inject
	private CategoriaNegocio categoriaNegocio;
	
	@Inject
	private ContextoBean contextoBean;
	
	public void novo(){
		Categoria pai = null;
		if (this.editada.getCodigo()!=null) {
			pai = categoriaNegocio.carregar(this.editada.getCodigo());
		}
		
		this.editada = new Categoria();
		this.editada.setPai(pai);
		this.mostraEdicao = true;
	}
	
	
	public void selecionar(NodeSelectEvent event){
		this.editada = (Categoria) event.getTreeNode().getData();
		Categoria pai = this.editada.getPai();
		
		if(this.editada!=null && pai!=null && pai.getCodigo()!=null){
			this.mostraEdicao = true;
		}else {
			this.mostraEdicao = false;
		}
	}
	
	@Transactional
	public void salvar(){
		this.editada.setUsuario(contextoBean.getUsuarioLogado());
		categoriaNegocio.salvar(editada);
		
		this.editada = null;
		this.mostraEdicao = false;
		this.categoriaTree = null;
		this.categoriasSelect = null;
	}
	
	@Transactional
	public void excluir(){
		
		
		categoriaNegocio.excluir(editada);
		
		this.editada = null;
		this.mostraEdicao = false;
		this.categoriaTree = null;
		this.categoriasSelect = null;
	}
	
	
	public TreeNode getCategoriasTree(){
		
		if(this.categoriaTree == null){
			List<Categoria> categorias = categoriaNegocio.listar(contextoBean.getUsuarioLogado());
			this.categoriaTree = new DefaultTreeNode(null, null) ;
			this.montaDadosTree(this.categoriaTree,categorias);
		}
		
		return this.categoriaTree;
	}


	private void montaDadosTree(TreeNode pai,
			List<Categoria> categorias) {
		if(categorias != null && !categorias.isEmpty() ){
			TreeNode filho = null;
			for (Categoria categoria : categorias) {
				filho = new DefaultTreeNode(categoria, pai);
				this.montaDadosTree(filho, categoria.getFilhos());
			}
		}
	}
	
	
	public List<SelectItem> getCategoriasSelect(){
		if(this.categoriasSelect==null){
			this.categoriasSelect = new ArrayList<SelectItem>();
			List<Categoria> categorias = categoriaNegocio.listar(contextoBean.getUsuarioLogado());
			this.montaDadosSelect(this.categoriasSelect,categorias,"");
		}
		
		return this.categoriasSelect;
	}


	private void montaDadosSelect(List<SelectItem> select,
			List<Categoria> categorias, String prefixo) {
		SelectItem item = null; 
		if (categorias !=null) {
			for (Categoria categoria : categorias) {
				item = new SelectItem(categoria, prefixo + categoria.getDescricao());
				item.setEscape(false);
				select.add(item);
				this.montaDadosSelect(select, categoria.getFilhos(), prefixo+"&nbsp;&nbsp;");
			}
		}
	}


	


	


	public Categoria getEditada() {
		return editada;
	}


	public void setEditada(Categoria editada) {
		this.editada = editada;
	}


	public boolean isMostraEdicao() {
		return mostraEdicao;
	}


	public void setMostraEdicao(boolean mostraEdicao) {
		this.mostraEdicao = mostraEdicao;
	}


	
	
	
	
}
