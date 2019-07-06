package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.entity.Pauta;
import br.com.servico.votacao.exception.NotFoundException;
import br.com.servico.votacao.repository.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PautaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);

    private final PautaRepository repository;

    @Autowired
    public PautaService(PautaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PautaDTO salvar(PautaDTO dto) {
        return PautaDTO.toDTO(repository.save(PautaDTO.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public PautaDTO buscarPautaPeloOID(Integer oid) {
        Optional<Pauta> pautaOptional = repository.findById(oid);

        if (!pautaOptional.isPresent()) {
            LOGGER.error("Pauta não localizada para oid" + oid);
            throw new NotFoundException("Pauta não localizada para o oid " + oid);
        }

        return PautaDTO.toDTO(pautaOptional.get());
    }

    @Transactional(readOnly = true)
    public boolean isPautaValida(Integer oid) {
        return repository.existsByOid(oid);
    }
}