package br.com.motosecurityx.service.impl;

import br.com.motosecurityx.domain.Alocacao;
import br.com.motosecurityx.domain.Funcionario;
import br.com.motosecurityx.domain.Moto;
import br.com.motosecurityx.repository.AlocacaoRepository;
import br.com.motosecurityx.repository.FuncionarioRepository;
import br.com.motosecurityx.repository.MotoRepository;
import br.com.motosecurityx.service.AlocacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AlocacaoServiceImpl implements AlocacaoService {

    private final AlocacaoRepository alocRepo;
    private final FuncionarioRepository funcRepo;
    private final MotoRepository motoRepo;

    public AlocacaoServiceImpl(AlocacaoRepository alocRepo, FuncionarioRepository funcRepo, MotoRepository motoRepo) {
        this.alocRepo = alocRepo;
        this.funcRepo = funcRepo;
        this.motoRepo = motoRepo;
    }

    @Override
    public Alocacao alocar(Long funcionarioId, Long motoId) {
        Funcionario f = funcRepo.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
        Moto m = motoRepo.findById(motoId)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada"));

        if (!Boolean.TRUE.equals(f.getAtivo())) {
            throw new IllegalStateException("Funcionário inativo.");
        }
        if (!Boolean.TRUE.equals(m.getDisponivel())) {
            throw new IllegalStateException("Moto indisponível para alocação.");
        }
        if (alocRepo.existsByFuncionarioIdAndDataFimIsNull(f.getId())) {
            throw new IllegalStateException("Funcionário já possui alocação ativa.");
        }
        if (alocRepo.existsByMotoIdAndDataFimIsNull(m.getId())) {
            throw new IllegalStateException("Moto já está alocada.");
        }

        // cria alocação
        Alocacao a = new Alocacao();
        a.setFuncionario(f);
        a.setMoto(m);
        Alocacao saved = alocRepo.save(a);

        // marca indisponível
        m.setDisponivel(false);
        motoRepo.save(m);

        return saved;
    }

    @Override
    public void devolver(Long alocacaoId) {
        Alocacao a = alocRepo.findById(alocacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Alocação não encontrada"));
        if (a.getDataFim() != null) {
            throw new IllegalStateException("Alocação já encerrada.");
        }
        a.setDataFim(LocalDateTime.now());
        alocRepo.save(a);

        // libera moto
        Moto m = a.getMoto();
        m.setDisponivel(true);
        motoRepo.save(m);
    }
}
