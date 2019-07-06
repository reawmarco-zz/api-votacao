package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.Pauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    private Integer oid;
    private String titulo;
    private String descricao;

    public static final Pauta toEntity(PautaDTO dto) {
        return Pauta.builder()
                .oid(dto.getOid())
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .build();
    }

    public static final PautaDTO toDTO(Pauta pauta) {
        return PautaDTO.builder()
                .oid(pauta.getOid())
                .titulo(pauta.getTitulo())
                .descricao(pauta.getDescricao())
                .build();
    }
}
