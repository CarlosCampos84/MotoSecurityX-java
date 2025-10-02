package br.com.motosecurityx.service;

import br.com.motosecurityx.domain.Alocacao;

public interface AlocacaoService {
    Alocacao alocar(Long funcionarioId, Long motoId);
    void devolver(Long alocacaoId);
}
