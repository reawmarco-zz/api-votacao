package br.com.servico.votacao.service;

import br.com.servico.votacao.client.controller.ValidaCPFClient;
import br.com.servico.votacao.dto.AssociadoDTO;
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
    private ValidaCPFClient validaCPFClient;

    @Autowired
    public AssociadoService(AssociadoRepository repository, ValidaCPFClient validaCPFClient) {
        this.repository = repository;
        this.validaCPFClient = validaCPFClient;
    }


    @Transactional(readOnly = true)
    public Boolean isValidaParticipacaoAssociadoVotacao(String cpfAssociado, Integer oidPauta) {
        LOGGER.debug("Validando participacao do associado na votacao da pauta  oid = {}", oidPauta);
        if (repository.existsByCpfAssociadoAndOidPauta(cpfAssociado, oidPauta)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Transactional
    public void salvarAssociado(AssociadoDTO dto) {
        LOGGER.debug("Registrando participacao do associado na votacao oidAssociado = {}, oidPauta = {}", dto.getCpfAssociado(), dto.getOidPauta());
        repository.save(AssociadoDTO.toEntity(dto));
    }

    public Boolean isAssociadoPodeVotar(String cpf) {
        return validaCPFClient.isVerificaAssociadoHabilitadoVotacao(cpf);
    }
}