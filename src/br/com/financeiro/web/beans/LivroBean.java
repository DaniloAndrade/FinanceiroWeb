package br.com.financeiro.web.beans;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
public class LivroBean {
	
	//private Livro livro = new Livro();
	private TreeNode categoriaTree;


	/*public Livro getLivro() {
		return livro;
	}*/
	
	
	
	/*public void gravar(){
	    System.out.println("Gravando livro " +this.livro.getTitulo());
	}*/
	
	
	public TreeNode getCategoriasTree(){
		categoriaTree = new DefaultTreeNode("Root", null);  
        TreeNode node0 = new DefaultTreeNode("Node 0", categoriaTree);  
        TreeNode node1 = new DefaultTreeNode("Node 1", categoriaTree);  
        TreeNode node2 = new DefaultTreeNode("Node 2", categoriaTree);  
          
        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);  
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);  
          
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);  
        TreeNode node11 = new DefaultTreeNode("Node 1.1", node1);  
          
        TreeNode node000 = new DefaultTreeNode("Node 0.0.0", node00);  
        TreeNode node001 = new DefaultTreeNode("Node 0.0.1", node00);  
        TreeNode node010 = new DefaultTreeNode("Node 0.1.0", node01);  
          
        TreeNode node100 = new DefaultTreeNode("Node 1.0.0", node10);  
		/*if(this.categoriaTree == null){
			List<Categoria> categorias = categoriaNegocio.listar(contextoBean.getUsuarioLogado());
			this.categoriaTree = new ;
			this.montaDadosTree(this.categoriaTree,categorias);
		}
		
		return this.categoriaTree;*/
        return categoriaTree;
	}

	
}
