package br.com.servico.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotarDTO {

    @NotNull
    private Integer oidPauta;

    @NotNull
    private Integer oidSessaoVotacao;

    @NotNull
    private Boolean voto;

    @NotNull
    private Integer associado;
}
