package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.Associado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoDTO {

    private Integer oid;

    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "CPF do associado deve ser preenchido")
    private String cpfAssociado;

    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer oidPauta;

    public static final Associado toEntity(AssociadoDTO dto) {
        return Associado.builder()
                .oid(dto.getOid())
                .cpfAssociado(dto.getCpfAssociado())
                .oidPauta(dto.getOidPauta())
                .build();
    }

    public static final AssociadoDTO toDTO(Associado associado) {
        return AssociadoDTO.builder()
                .oid(associado.getOid())
                .cpfAssociado(associado.getCpfAssociado())
                .oidPauta(associado.getOidPauta())
                .build();
    }
}
