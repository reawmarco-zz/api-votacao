package br.com.servico.votacao.controller;

import br.com.servico.votacao.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/votacoes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotacaoController {

    private final VotacaoService service;

    @Autowired
    public VotacaoController(VotacaoService service) {
        this.service = service;
    }
}
