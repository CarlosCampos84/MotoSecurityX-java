package br.com.motosecurityx.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alocacao")
public class Alocacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "moto_id")
    private Moto moto;

    @ManyToOne(optional = false) @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio = LocalDateTime.now();

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    public boolean estaAtiva() { return dataFim == null; }

    // getters/setters
    public Long getId() { return id; }

    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
}
