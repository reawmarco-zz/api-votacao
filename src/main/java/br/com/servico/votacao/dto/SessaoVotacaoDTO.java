package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.SessaoVotacao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(value = "SessaoVotacaoDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacaoDTO {

    @ApiModelProperty(value = "ID da sessão de votação aberta")
    private Integer oid;

    @ApiModelProperty(value = "Data Hora de início da sessão de votação aberta")
    private LocalDateTime dataHoraInicio;

    @ApiModelProperty(value = "Data Hora de fim sessão de votação aberta")
    private LocalDateTime dataHoraFim;

    @ApiModelProperty(value = "Status da sessão de votação aberta")
    private Boolean ativa;


    public static SessaoVotacao toEntity(SessaoVotacaoDTO dto) {
        return SessaoVotacao.builder()
                .oid(dto.getOid())
                .dataHoraInicio(dto.getDataHoraInicio())
                .dataHoraFim(dto.getDataHoraFim())
                .ativa(dto.getAtiva())
                .build();
    }

    public static SessaoVotacaoDTO toDTO(SessaoVotacao sessaoVotacao) {
        return SessaoVotacaoDTO.builder()
                .oid(sessaoVotacao.getOid())
                .dataHoraInicio(sessaoVotacao.getDataHoraInicio())
                .dataHoraFim(sessaoVotacao.getDataHoraFim())
                .ativa(sessaoVotacao.getAtiva())
                .build();
    }
}