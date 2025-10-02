package br.com.motosecurityx.repository;

import br.com.motosecurityx.domain.Alocacao;
import br.com.motosecurityx.domain.Funcionario;
import br.com.motosecurityx.domain.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlocacaoRepository extends JpaRepository<Alocacao, Long> {

    // Ativa = data_fim IS NULL
    Optional<Alocacao> findByMotoAndDataFimIsNull(Moto moto);

    Optional<Alocacao> findByFuncionarioAndDataFimIsNull(Funcionario funcionario);

    boolean existsByMotoIdAndDataFimIsNull(Long motoId);

    boolean existsByFuncionarioIdAndDataFimIsNull(Long funcionarioId);
}
