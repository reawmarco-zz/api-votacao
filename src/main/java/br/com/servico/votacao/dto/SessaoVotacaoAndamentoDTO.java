package br.com.servico.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacaoAndamentoDTO {

    private Integer oid;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Integer tempo;
    private Boolean ativa;

    public static final SessaoVotacaoAndamentoDTO toDTOAndamento(SessaoVotacaoDTO dto) {
        return SessaoVotacaoAndamentoDTO.builder()
                .oid(dto.getOid())
                .dataHoraInicio(dto.getDataHoraInicio())
                .dataHoraFim(dto.getDataHoraFim())
                .tempo(dto.getTempo())
                .ativa(dto.getAtiva())
                .build();
    }
}
