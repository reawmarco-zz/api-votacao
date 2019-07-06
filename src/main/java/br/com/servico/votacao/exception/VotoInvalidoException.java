package br.com.servico.votacao.exception;

public class VotoInvalidoException extends RuntimeException {

    public VotoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
