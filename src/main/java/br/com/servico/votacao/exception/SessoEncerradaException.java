package br.com.servico.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SessoEncerradaException extends RuntimeException {

    public SessoEncerradaException(String mensagem) {
        super(mensagem);
    }
}
