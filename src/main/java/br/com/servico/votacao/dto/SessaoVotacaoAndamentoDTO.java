package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.SessaoVotacao;
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
    private Boolean ativa;

    public static final SessaoVotacaoAndamentoDTO toDTOAndamento(SessaoVotacao sessaoVotacao) {
        return SessaoVotacaoAndamentoDTO.builder()
                .oid(sessaoVotacao.getOid())
                .dataHoraInicio(sessaoVotacao.getDataHoraInicio())
                .dataHoraFim(sessaoVotacao.getDataHoraFim())
                .ativa(sessaoVotacao.getAtiva())
                .build();
    }
}
