package br.com.servico.votacao.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface ValidaCPFClient {

    @GetMapping("{cpf}")
    String verificarPermissaoAssocidadoPeloCPF(@PathVariable("cpf") String cpf);
}
