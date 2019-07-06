package br.com.servico.votacao.service;

import br.com.servico.votacao.repository.AssociadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssociadoService {


    private static final Logger LOGGER = LoggerFactory.getLogger(AssociadoService.class);
    private final AssociadoRepository repository;

    @Autowired
    public AssociadoService(AssociadoRepository repository) {
        this.repository = repository;
    }


    @Transactional(readOnly = true)
    public Boolean isValidaParticipacaoAssociadoVotacao(Integer oidAssociado, Integer oidPauta) {
        LOGGER.info("Validando participacao do associado na votacao da pauta  oid = {}", oidPauta);
        return repository.existsByOidAssociadoAndOidPauta(oidAssociado, oidPauta);
    }
}
