package br.com.servico.votacao.controller;

import br.com.servico.votacao.dto.SessaoVotacaoAbrirDTO;
import br.com.servico.votacao.dto.SessaoVotacaoDTO;
import br.com.servico.votacao.service.SessaoVotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/sessoes-votacao")
@Api(value = "Sessao Votacao", tags = "Sessao Votacao")
public class SessaoVotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoController.class);

    private final SessaoVotacaoService service;

    @Autowired
    public SessaoVotacaoController(SessaoVotacaoService service) {
        this.service = service;
    }

    @ApiOperation(value = "Abrir uma sessão de votação, referente a determinada pauta")
    @PostMapping(value = "/abrir-sessao")
    public ResponseEntity<SessaoVotacaoDTO> abrirSessaoVotacao(@Valid @RequestBody SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO) {
        LOGGER.debug("Abrindo a sessao para votacao da pauta  oid = {}", sessaoVotacaoAbrirDTO.getOidPauta());
        SessaoVotacaoDTO dto = service.abrirSessaoVotacao(sessaoVotacaoAbrirDTO);
        LOGGER.debug("Sessao para votacao da pauta  oid = {} aberta", sessaoVotacaoAbrirDTO.getOidPauta());
        LOGGER.debug("Hora de inicio sessao para votacao {}", dto.getDataHoraInicio());
        LOGGER.debug("Hora de encerramento sessao para votacao {}", dto.getDataHoraFim());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
