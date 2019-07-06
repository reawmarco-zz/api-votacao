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
@Table(name = "tbl_pauta")
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {

    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oid;

    @NotNull
    @Column(name = "descricao", length = 100)
    private String descricao;

}
