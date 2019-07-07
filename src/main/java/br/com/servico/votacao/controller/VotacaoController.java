package br.com.servico.votacao.controller;

import br.com.servico.votacao.dto.ResultadoDTO;
import br.com.servico.votacao.dto.VotarDTO;
import br.com.servico.votacao.service.VotacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/votacoes")
public class VotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoController.class);

    private final VotacaoService service;

    @Autowired
    public VotacaoController(VotacaoService service) {
        this.service = service;
    }

    @PostMapping(value = "/votar")
    public ResponseEntity<?> votar(@Valid @RequestBody VotarDTO dto) {
        LOGGER.debug("Associado votando associado = {}", dto.getCpfAssociado());
        String mensagem = service.votar(dto);
        LOGGER.debug("Voto associado finalizado associado = {}", dto.getCpfAssociado());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @GetMapping(value = "/resultado/{oidPauta}/{oidSessaoVotacao}")
    public ResponseEntity<?> resultadoVotacao(@PathVariable("oidPauta") Integer oidPauta, @PathVariable("oidSessaoVotacao") Integer oidSessaoVotacao) {
        LOGGER.debug("Buscando resultado da votacao oidPauta = {} , oidSessaoVotacao = {} ", oidPauta, oidSessaoVotacao);
        ResultadoDTO dto = service.buscarDadosResultadoVotacao(oidPauta, oidSessaoVotacao);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
