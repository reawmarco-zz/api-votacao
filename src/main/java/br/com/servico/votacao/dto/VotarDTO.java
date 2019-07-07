package br.com.servico.votacao.dto;

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
public class VotarDTO {

    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer oidPauta;

    @NotNull(message = "oidSessaoVotacao deve ser preenchido")
    private Integer oidSessaoVotacao;

    @NotNull(message = "voto deve ser preenchido")
    private Boolean voto;

    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "cpf do associado deve ser preenchido")
    private String cpfAssociado;
}
