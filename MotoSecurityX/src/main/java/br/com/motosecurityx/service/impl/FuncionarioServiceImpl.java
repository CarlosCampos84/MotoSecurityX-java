package br.com.motosecurityx.service.impl;

import br.com.motosecurityx.domain.Funcionario;
import br.com.motosecurityx.repository.FuncionarioRepository;
import br.com.motosecurityx.service.FuncionarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository repo;

    public FuncionarioServiceImpl(FuncionarioRepository repo) {
        this.repo = repo;
    }

    @Override @Transactional(readOnly = true)
    public List<Funcionario> listar() { return repo.findAll(); }

    @Override @Transactional(readOnly = true)
    public Funcionario buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
    }

    @Override
    public Funcionario salvar(Funcionario f) {
        repo.findByEmail(f.getEmail()).ifPresent(ex -> {
            if (f.getId() == null || !ex.getId().equals(f.getId())) {
                throw new IllegalStateException("E-mail já cadastrado.");
            }
        });
        return repo.save(f);
    }

    @Override
    public void excluir(Long id) { repo.deleteById(id); }
}
