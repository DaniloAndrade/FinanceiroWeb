package br.com.financeiro.repository;

import br.com.financeiro.entitys.Idioma;

public interface IIdiomaRepository {
	Idioma buscarPorISO(String iso);
}
