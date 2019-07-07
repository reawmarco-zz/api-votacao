package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.Pauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {


    private Integer oid;

    @NotBlank(message = "Descrição deve ser preenchido")
    private String descricao;

    public static final Pauta toEntity(PautaDTO dto) {
        return Pauta.builder()
                .oid(dto.getOid())
                .descricao(dto.getDescricao())
                .build();
    }

    public static final PautaDTO toDTO(Pauta pauta) {
        return PautaDTO.builder()
                .oid(pauta.getOid())
                .descricao(pauta.getDescricao())
                .build();
    }
}
