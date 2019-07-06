package br.com.servico.votacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "tbl_associado")
@AllArgsConstructor
@NoArgsConstructor
public class Associado {

    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oid;

    @Column(name = "oid_associado")
    private Integer oidAssociado;

    @Column(name = "oid_pauta")
    private Integer oidPauta;
}
