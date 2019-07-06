package br.com.servico.votacao.controller;


import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.service.PautaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PautaController.class);

    private final PautaService service;

    @Autowired
    public PautaController(PautaService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> salvarPauta(@Valid @RequestBody PautaDTO dto) {
        LOGGER.info("Salvando a pauta {descricao}", dto.getDescricao());
        dto = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping(value = "/{oid}")
    public ResponseEntity<?> buscarPautaPeloOID(@PathVariable("oid") Integer oid) {
        LOGGER.info("Buscando a pauta pelo OID {oid}", oid);
        PautaDTO pautaDTO = service.buscarPautaPeloOID(oid);
        return ResponseEntity.ok(pautaDTO);
    }
}
