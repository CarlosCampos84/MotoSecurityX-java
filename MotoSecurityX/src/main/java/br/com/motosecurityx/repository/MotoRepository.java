package br.com.motosecurityx.repository;

import br.com.motosecurityx.domain.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {

    // Buscar por placa (única)
    Optional<Moto> findByPlaca(String placa);

    // Contar motos em determinado pátio
    @Query("select count(m) from Moto m where m.patioAtual.id = :patioId")
    long countByPatioAtualId(Long patioId);

    // Listar apenas motos disponíveis
    List<Moto> findByDisponivelTrue();
}
