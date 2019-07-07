package br.com.servico.votacao.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "ResultadoDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoDTO {

    @ApiModelProperty(value = "Objeto PautaDTO com os dados do que foi votado", reference = "PautaDTO")
    private PautaDTO pautaDTO;
    @ApiModelProperty(value = "Objeto VotacaoDTO com dados do resultado da votação", reference = "VotacaoDTO")
    private VotacaoDTO votacaoDTO;
}
