package br.com.servico.votacao.controller;

import br.com.servico.votacao.dto.ResultadoDTO;
import br.com.servico.votacao.dto.VotoDTO;
import br.com.servico.votacao.service.VotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/votacoes")
@Api(value = "Votacao", produces = "JSON", consumes = "JSON", tags = "Votacao")
public class VotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoController.class);

    private final VotacaoService service;

    @Autowired
    public VotacaoController(VotacaoService service) {
        this.service = service;
    }

    @ApiOperation(value = "Votar em determinada pauta, enquanto a sessão de votação estiver aberta")
    @PostMapping(value = "/votar")
    public ResponseEntity<String> votar(@Valid @RequestBody VotoDTO dto) {
        LOGGER.debug("Associado votando associado = {}", dto.getCpfAssociado());
        String mensagem = service.votar(dto);
        LOGGER.debug("Voto associado finalizado associado = {}", dto.getCpfAssociado());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @ApiOperation(value = "Resultado da votacao, somente após finalização da sessão de votação")
    @GetMapping(value = "/resultado/{oidPauta}/{oidSessaoVotacao}")
    public ResponseEntity<ResultadoDTO> resultadoVotacao(@PathVariable("oidPauta") Integer oidPauta, @PathVariable("oidSessaoVotacao") Integer oidSessaoVotacao) {
        LOGGER.debug("Buscando resultado da votacao oidPauta = {} , oidSessaoVotacao = {} ", oidPauta, oidSessaoVotacao);
        ResultadoDTO dto = service.buscarDadosResultadoVotacao(oidPauta, oidSessaoVotacao);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
