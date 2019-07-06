package br.com.servico.votacao.controller;

import br.com.servico.votacao.dto.SessaoVotacaoDTO;
import br.com.servico.votacao.service.SessaoVotacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/sessoes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SessaoVotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoController.class);

    private final SessaoVotacaoService service;

    @Autowired
    public SessaoVotacaoController(SessaoVotacaoService service) {
        this.service = service;
    }

    @PostMapping(value = "/abrir-sessao/{oidPauta}/{tempo}")
    public ResponseEntity<?> abrirSessaoVotacao(@PathVariable("oidPauta") Integer oidPauta, @PathVariable("tempo") Integer tempo) {
        LOGGER.info("Abrindo a sessao para votacao");
        SessaoVotacaoDTO dto = service.abrirSessaoVotacao(oidPauta, tempo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
