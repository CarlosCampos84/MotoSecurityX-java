package br.com.motosecurityx.service.impl;

import br.com.motosecurityx.domain.Moto;
import br.com.motosecurityx.domain.Movimentacao;
import br.com.motosecurityx.domain.Patio;
import br.com.motosecurityx.repository.MotoRepository;
import br.com.motosecurityx.repository.MovimentacaoRepository;
import br.com.motosecurityx.repository.PatioRepository;
import br.com.motosecurityx.service.MotoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MotoServiceImpl implements MotoService {

    private final MotoRepository repo;
    private final PatioRepository patioRepo;
    private final MovimentacaoRepository movRepo;

    public MotoServiceImpl(MotoRepository repo, PatioRepository patioRepo, MovimentacaoRepository movRepo) {
        this.repo = repo;
        this.patioRepo = patioRepo;
        this.movRepo = movRepo;
    }

    @Override @Transactional(readOnly = true)
    public List<Moto> listar() { return repo.findAll(); }

    @Override @Transactional(readOnly = true)
    public Moto buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Moto não encontrada"));
    }

    @Override
    public Moto salvar(Moto m) {
        // validação de placa única (opcional – já há unique no DB)
        repo.findByPlaca(m.getPlaca()).ifPresent(existing -> {
            if (m.getId() == null || !existing.getId().equals(m.getId())) {
                throw new IllegalStateException("Placa já cadastrada.");
            }
        });
        return repo.save(m);
    }

    @Override
    public void excluir(Long id) { repo.deleteById(id); }

    @Override
    public void moverMoto(Long motoId, Long novoPatioId) {
        Moto moto = buscar(motoId);
        Patio origem = moto.getPatioAtual();
        Patio destino = patioRepo.findById(novoPatioId)
                .orElseThrow(() -> new IllegalArgumentException("Pátio destino não encontrado"));

        long ocupadas = repo.countByPatioAtualId(destino.getId());
        if (ocupadas >= destino.getCapacidade()) {
            throw new IllegalStateException("Pátio destino sem capacidade.");
        }

        moto.setPatioAtual(destino);
        repo.save(moto);

        Movimentacao mov = new Movimentacao();
        mov.setMoto(moto);
        mov.setOrigem(origem);
        mov.setDestino(destino);
        movRepo.save(mov);
    }
}
