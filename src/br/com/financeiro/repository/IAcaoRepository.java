package br.com.financeiro.repository;

import java.util.List;

import br.com.financeiro.entitys.Acao;
import br.com.financeiro.entitys.Usuario;

public interface IAcaoRepository {
	Acao salvar(Acao acao);
	void excluir(Acao acao);
	Acao carregar(String codigo);
	List<Acao> listar(Usuario usuario);
}
