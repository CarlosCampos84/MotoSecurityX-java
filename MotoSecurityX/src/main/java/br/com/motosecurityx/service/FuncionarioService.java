package br.com.motosecurityx.service;

import br.com.motosecurityx.domain.Funcionario;

import java.util.List;

public interface FuncionarioService {
    List<Funcionario> listar();
    Funcionario buscar(Long id);
    Funcionario salvar(Funcionario f);
    void excluir(Long id);
}
