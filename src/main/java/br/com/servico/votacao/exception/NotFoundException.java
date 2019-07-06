package br.com.servico.votacao.exception;

import org.springframework.web.client.RestClientException;

public class NotFoundException extends RestClientException {

    public NotFoundException(String mensagem) {
        super(mensagem);
    }
}
