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
    private Integer oidPauta;
    private Integer oidSessaoVotacao;
    private Boolean voto;

    public static final Votacao toEntity(VotacaoDTO dto) {
        return Votacao.builder()
                .oid(dto.getOid())
                .oidPauta(dto.getOidPauta())
                .oidSessaoVotacao(dto.getOidSessaoVotacao())
                .voto(dto.getVoto())
                .build();
    }

    public static final VotacaoDTO toDTO(Votacao votacao) {
        return VotacaoDTO.builder()
                .oid(votacao.getOid())
                .voto(votacao.getVoto())
                .oidSessaoVotacao(votacao.getOidSessaoVotacao())
                .oidPauta(votacao.getOidPauta())
                .build();
    }
}
