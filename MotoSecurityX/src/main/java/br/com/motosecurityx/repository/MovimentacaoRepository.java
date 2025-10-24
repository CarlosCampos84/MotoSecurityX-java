package br.com.motosecurityx.repository;

import br.com.motosecurityx.domain.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {}
