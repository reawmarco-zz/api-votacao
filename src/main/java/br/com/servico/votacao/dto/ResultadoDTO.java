package br.com.servico.votacao.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoDTO {

    private PautaDTO pautaDTO;

    private VotacaoDTO votacaoDTO;
}
