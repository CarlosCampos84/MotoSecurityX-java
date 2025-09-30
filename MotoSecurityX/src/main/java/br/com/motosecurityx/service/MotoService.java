package br.com.motosecurityx.service;

import br.com.motosecurityx.domain.Moto;
import java.util.List;

public interface MotoService {
    List<Moto> listar();
    Moto buscar(Long id);
    Moto salvar(Moto m);         // create/update
    void excluir(Long id);

    void moverMoto(Long motoId, Long novoPatioId); // Fluxo 1
}
