package br.com.servico.votacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "tbl_votacao")
@AllArgsConstructor
@NoArgsConstructor
public class Votacao {

    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oid;

    @Column(name = "voto")
    private Boolean voto;

    @Column(name = "oid_pauta")
    private Integer oidPauta;

    @Column(name = "oid_sessao_votacao")
    private Integer oidSessaoVotacao;
}
