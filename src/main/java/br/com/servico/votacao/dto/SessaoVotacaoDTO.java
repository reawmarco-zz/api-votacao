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
public class SessaoVotacaoDTO {


    private Integer oid;
    private VotacaoDTO votacaoDTO;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Integer tempo;
    private Boolean ativa;


    public static final SessaoVotacao toEntity(SessaoVotacaoDTO dto) {
        return SessaoVotacao.builder()
                .oid(dto.getOid())
                .votacao(VotacaoDTO.toEntity(dto.getVotacaoDTO()))
                .dataHoraInicio(dto.getDataHoraInicio())
                .dataHoraFim(dto.getDataHoraFim())
                .tempo(dto.getTempo())
                .ativa(dto.getAtiva())
                .build();
    }

    public static final SessaoVotacaoDTO toDTO(SessaoVotacao sessaoVotacao) {
        return SessaoVotacaoDTO.builder()
                .oid(sessaoVotacao.getOid())
                .votacaoDTO(VotacaoDTO.toDTO(sessaoVotacao.getVotacao()))
                .dataHoraInicio(sessaoVotacao.getDataHoraInicio())
                .dataHoraFim(sessaoVotacao.getDataHoraFim())
                .tempo(sessaoVotacao.getTempo())
                .ativa(sessaoVotacao.getAtiva())
                .build();
    }
}