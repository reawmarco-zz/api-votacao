package br.com.servico.votacao.controller;

import br.com.servico.votacao.dto.VotarDTO;
import br.com.servico.votacao.service.VotacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/votacoes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoController.class);

    private final VotacaoService service;

    @Autowired
    public VotacaoController(VotacaoService service) {
        this.service = service;
    }

    @PostMapping(value = "/votar")
    public ResponseEntity<?> votar(@Valid @RequestBody VotarDTO dto) {
        LOGGER.info("Associado votando associado = {}", dto.getAssociado());
        service.votar(dto);
        LOGGER.info("Voto associado finalizado associado = {}", dto.getAssociado());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
