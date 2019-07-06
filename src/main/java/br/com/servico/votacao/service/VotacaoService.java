package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.VotacaoDTO;
import br.com.servico.votacao.dto.VotarDTO;
import br.com.servico.votacao.repository.VotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final VotacaoRepository repository;
    private final PautaService pautaService;
    private final SessaoVotacaoService sessaoVotacaoService;
    private final AssociadoService associadoService;

    @Autowired
    public VotacaoService(VotacaoRepository repository, PautaService pautaService, SessaoVotacaoService sessaoVotacaoService, AssociadoService associadoService) {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.associadoService = associadoService;
    }

    @Transactional(readOnly = true)
    public Boolean isValidaVoto(VotarDTO dto) {
        LOGGER.info("Validando os dados para voto oidSessao = {}, oidPauta = {}, oidAssiciado = {}", dto.getOidSessaoVotacao(), dto.getOidPauta(), dto.getAssociado());
        if (!pautaService.isPautaValida(dto.getOidPauta())) {

        } else if (!sessaoVotacaoService.isSessaoVotacaoValida(dto.getOidSessaoVotacao())) {

        } else if (!associadoService.isValidaParticipacaoAssociadoVotacao(dto.getAssociado(), dto.getOidPauta())) {

        }
        return Boolean.TRUE;
    }

    @Transactional
    public void votar(VotarDTO dto) {
        if (isValidaVoto(dto)) {
            LOGGER.info("Dados validos para voto oidSessao = {}, oidPauta = {}, oidAssiciado = {}", dto.getOidSessaoVotacao(), dto.getOidPauta(), dto.getAssociado());
            VotacaoDTO votacaoDTO = new VotacaoDTO(null,
                    dto.getOidPauta(),
                    dto.getOidSessaoVotacao(),
                    dto.getVoto());
            salvarVoto(votacaoDTO);
        }
    }

    @Transactional
    public VotacaoDTO salvarVoto(VotacaoDTO dto) {
        LOGGER.info("Salvando o voto");
        return VotacaoDTO.toDTO(repository.save(VotacaoDTO.toEntity(dto)));
    }
}