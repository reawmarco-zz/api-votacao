package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.Votacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoDTO {

    private Integer oid;
    private PautaDTO pautaDTO;

    public static final Votacao toEntity(VotacaoDTO dto) {
        return Votacao.builder()
                .oid(dto.getOid())
                .pauta(PautaDTO.toEntity(dto.getPautaDTO()))
                .build();
    }

    public static final VotacaoDTO toDTO(Votacao votacao) {
        return VotacaoDTO.builder()
                .oid(votacao.getOid())
                .pautaDTO(PautaDTO.toDTO(votacao.getPauta()))
                .build();
    }
}
