package br.com.servico.votacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "tbl_sessao_votacao")
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacao {

    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oid;

    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    @Column(name = "status")
    private Boolean ativa;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oid_votacao", referencedColumnName = "oid", nullable = false)
    private Votacao votacao;

}
