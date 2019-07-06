package br.com.servico.votacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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


}
