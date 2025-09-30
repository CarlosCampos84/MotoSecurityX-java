package br.com.motosecurityx.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="moto_id")
    private Moto moto;

    @ManyToOne(optional = false) @JoinColumn(name="origem_id")
    private Patio origem;

    @ManyToOne(optional = false) @JoinColumn(name="destino_id")
    private Patio destino;

    @Column(name="data_hora", nullable=false)
    private LocalDateTime dataHora = LocalDateTime.now();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }
    public Patio getOrigem() { return origem; }
    public void setOrigem(Patio origem) { this.origem = origem; }
    public Patio getDestino() { return destino; }
    public void setDestino(Patio destino) { this.destino = destino; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
