package br.com.motosecurityx.service;

import br.com.motosecurityx.domain.Patio;
import java.util.List;

public interface PatioService {
    List<Patio> listar();
    Patio buscar(Long id);
    Patio salvar(Patio p);       // create/update
    void excluir(Long id);
}
