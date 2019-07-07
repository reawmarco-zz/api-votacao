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

    /**
     * Realiza a validacao se o associado ja votou na pauta informada pelo seu ID.
     * <p>
     * Se nao existir um registro na base, entao e considerado como valido para seu voto ser computado
     *
     * @param cpfAssociado @{@link br.com.servico.votacao.entity.Associado} CPF Valido
     * @param oidPauta     @{@link br.com.servico.votacao.entity.Pauta} ID
     * @return - boolean
     */
    @Transactional(readOnly = true)
    public boolean isValidaParticipacaoAssociadoVotacao(String cpfAssociado, Integer oidPauta) {
        LOGGER.debug("Validando participacao do associado na votacao da pauta  oid = {}", oidPauta);
        if (repository.existsByCpfAssociadoAndOidPauta(cpfAssociado, oidPauta)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * @param dto @{@link AssociadoDTO}
     */
    @Transactional
    public void salvarAssociado(AssociadoDTO dto) {
        LOGGER.debug("Registrando participacao do associado na votacao oidAssociado = {}, oidPauta = {}", dto.getCpfAssociado(), dto.getOidPauta());
        repository.save(AssociadoDTO.toEntity(dto));
    }

    /**
     * faz a chamada para metodo que realiza a consulta em API externa
     * para validar por meio de um cpf valido, se o associado esta habilitado para votar
     *
     * @param cpf - @{@link AssociadoDTO} CPF valido
     * @return - boolean
     */
    public boolean isAssociadoPodeVotar(String cpf) {
        return validaCPFClient.isVerificaAssociadoHabilitadoVotacao(cpf);
    }
}