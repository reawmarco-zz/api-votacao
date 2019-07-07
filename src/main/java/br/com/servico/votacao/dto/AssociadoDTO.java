package br.com.servico.votacao.dto;

import br.com.servico.votacao.entity.Associado;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "AssociadoDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoDTO {

    private Integer oid;

    @ApiModelProperty(value = "CPF válido referente ao associado.", required = true)
    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "CPF do associado deve ser preenchido")
    private String cpfAssociado;

    @ApiModelProperty(value = "ID da pauta a ser votada.", required = true, reference = "PautaDTO")
    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer oidPauta;

    public static Associado toEntity(AssociadoDTO dto) {
        return Associado.builder()
                .oid(dto.getOid())
                .cpfAssociado(dto.getCpfAssociado())
                .oidPauta(dto.getOidPauta())
                .build();
    }
}
