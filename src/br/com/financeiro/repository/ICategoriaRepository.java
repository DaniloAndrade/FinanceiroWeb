package br.com.financeiro.repository;

import java.util.List;

import br.com.financeiro.entitys.Categoria;
import br.com.financeiro.entitys.Usuario;

public interface ICategoriaRepository {

	Categoria salvar(Categoria categoria);
	void excluir(Categoria categoria);
	Categoria carregar(Integer codigo);
	List<Categoria> listar(Usuario usuario);
	Categoria atualiza(Categoria categoria);
}
