package br.com.servico.votacao.client.controller;

import br.com.servico.votacao.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ValidaCPFClient {

    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    public Boolean isVerificaAssociadoHabilitadoVotacao(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "https://user-info.herokuapp.com/users/".concat(cpf);
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);

            if (ABLE_TO_VOTE.equals(response.getBody())) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;

        } catch (HttpClientErrorException ex) {
            throw new NotFoundException("Não foi possivel localizar o CPF do associado");
        }
    }
}
