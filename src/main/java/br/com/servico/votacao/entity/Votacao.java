package br.com.servico.votacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oid_pauta", referencedColumnName = "oid", nullable = false)
    private Pauta pauta;

    @Column(name = "voto")
    private Boolean voto;
}
