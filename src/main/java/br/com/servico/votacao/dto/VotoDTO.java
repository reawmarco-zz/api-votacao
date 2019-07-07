package br.com.servico.votacao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "VotacaoDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    @ApiModelProperty(value = "ID da pauta da votação aberta", reference = "PautaDTO", required = true)
    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer oidPauta;

    @ApiModelProperty(value = "ID da sessão de votação aberta", reference = "VotacaoDTO", required = true)
    @NotNull(message = "oidSessaoVotacao deve ser preenchido")
    private Integer oidSessaoVotacao;

    @ApiModelProperty(value = "Voto", reference = "VotoDTO", required = true)
    @NotNull(message = "Voto deve ser preenchido")
    private Boolean voto;

    @ApiModelProperty(value = "CPF valido do associaddo", reference = "AssociadoDTO", required = true)
    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "cpf do associado deve ser preenchido")
    private String cpfAssociado;
}
