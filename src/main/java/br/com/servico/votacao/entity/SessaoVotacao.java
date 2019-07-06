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

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    private Boolean ativa;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_votacao", referencedColumnName = "oid", nullable = false)
    private Votacao votacao;

}
