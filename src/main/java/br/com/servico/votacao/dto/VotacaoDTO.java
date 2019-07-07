package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.Votacao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "VotacaoDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoDTO {

    @ApiModelProperty(value = "ID da votação aberta")
    private Integer oid;

    @ApiModelProperty(value = "ID da pauta da votação aberta")
    private Integer oidPauta;

    @ApiModelProperty(value = "ID da sessão de votação aberta")
    private Integer oidSessaoVotacao;

    @ApiModelProperty(value = "Voto")
    private Boolean voto;

    @ApiModelProperty(value = "Quantidade de votos positivos")
    private Integer quantidadeVotosSim;

    @ApiModelProperty(value = "Quantidade de votos negativos")
    private Integer quantidadeVotosNao;

    public static Votacao toEntity(VotacaoDTO dto) {
        return Votacao.builder()
                .oid(dto.getOid())
                .oidPauta(dto.getOidPauta())
                .oidSessaoVotacao(dto.getOidSessaoVotacao())
                .voto(dto.getVoto())
                .build();
    }
}
