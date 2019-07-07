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

    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer oidPauta;

    @NotNull(message = "oidSessaoVotacao deve ser preenchido")
    private Integer oidSessaoVotacao;

    @NotNull(message = "voto deve ser preenchido")
    private Boolean voto;

    @NotNull(message = "cpf do associado deve ser preenchido")
    private String cpfAssociado;
}
