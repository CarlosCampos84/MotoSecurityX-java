package br.com.motosecurityx.repository;

import br.com.motosecurityx.domain.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);

    @Query("select count(m) from Moto m where m.patioAtual.id = :patioId")
    long countByPatioAtualId(Long patioId);
}
