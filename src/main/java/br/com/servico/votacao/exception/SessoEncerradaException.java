package br.com.servico.votacao.exception;

public class SessoEncerradaException extends RuntimeException {

    public SessoEncerradaException(String mensagem) {
        super(mensagem);
    }
}
