package br.com.motosecurityx.service.impl;

import br.com.motosecurityx.domain.Patio;
import br.com.motosecurityx.repository.PatioRepository;
import br.com.motosecurityx.repository.MotoRepository;
import br.com.motosecurityx.service.PatioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatioServiceImpl implements PatioService {

    private final PatioRepository repo;
    private final MotoRepository motoRepo;

    public PatioServiceImpl(PatioRepository repo, MotoRepository motoRepo) {
        this.repo = repo;
        this.motoRepo = motoRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patio> listar() { return repo.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Patio buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado"));
    }

    @Override
    public Patio salvar(Patio p) {
        if (p.getCapacidade() != null && p.getId() != null) {
            long ocupadas = motoRepo.countByPatioAtualId(p.getId());
            if (p.getCapacidade() < ocupadas) {
                throw new IllegalStateException("Capacidade menor que motos já alocadas neste pátio.");
            }
        }
        return repo.save(p);
    }

    @Override
    public void excluir(Long id) {
        long ocupadas = motoRepo.countByPatioAtualId(id);
        if (ocupadas > 0) {
            throw new IllegalStateException("Não é possível excluir pátio com motos alocadas.");
        }
        repo.deleteById(id);
    }
}
