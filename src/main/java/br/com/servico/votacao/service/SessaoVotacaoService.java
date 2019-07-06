package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.dto.SessaoVotacaoDTO;
import br.com.servico.votacao.dto.VotacaoDTO;
import br.com.servico.votacao.entity.SessaoVotacao;
import br.com.servico.votacao.repository.SessaoVotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);

    private final SessaoVotacaoRepository repository;
    private final VotacaoService votacaoService;
    private static final Integer TEMPO_DEFAULT = 1;
    private final PautaService pautaService;


    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository repository, VotacaoService votacaoService, PautaService pautaService) {
        this.repository = repository;
        this.votacaoService = votacaoService;
        this.pautaService = pautaService;
    }


    public SessaoVotacaoDTO abrirSessaoVotacao(Integer oidPauta, Integer tempo) {
        LOGGER.info("Abrir sessao de votacao para a pauta {oidPauta}", oidPauta);

        PautaDTO pautaDTO = pautaService.buscarPautaPeloOID(oidPauta);
        VotacaoDTO votacaoDTO = votacaoService.iniciarVotacao(pautaDTO);

        SessaoVotacaoDTO dto = new SessaoVotacaoDTO(
                null,
                votacaoDTO,
                LocalDateTime.now(),
                calcularTempo(tempo),
                tempo,
                Boolean.TRUE);
        return salvar(dto);
    }

    public List<SessaoVotacaoDTO> buscarSessaoesEmAndamento() {
        List<SessaoVotacaoDTO> list = repository.buscarTodasSessoesEmAndamento()
                .stream()
                .map(SessaoVotacaoDTO::toDTO)
                .collect(Collectors.toList());

        return list
                .stream()
                .filter(dto -> dto.getDataHoraFim().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void encerraoSessaoVotacao(SessaoVotacaoDTO dto) {
        dto.setAtiva(Boolean.FALSE);
        salvar(dto);
    }

    private SessaoVotacaoDTO buscarSessaoVotacaoPeloOID(Integer oid) {
        SessaoVotacao sessaoVotacao = repository.getOne(oid);
        if (Optional.ofNullable(sessaoVotacao).isPresent()) {
            return SessaoVotacaoDTO.toDTO(sessaoVotacao);
        }
        return null;
    }

    private LocalDateTime calcularTempo(Integer tempo) {
        if (tempo != null && tempo != 0) {
            return LocalDateTime.now().plusMinutes(tempo);
        } else {
            return LocalDateTime.now().plusMinutes(TEMPO_DEFAULT);
        }
    }

    private SessaoVotacaoDTO salvar(SessaoVotacaoDTO dto) {
        LOGGER.info("salvar sessao de votacao para a pauta {oidPauta}", dto.getVotacaoDTO().getPautaDTO().getOid());
        return SessaoVotacaoDTO.toDTO(repository.save(SessaoVotacaoDTO.toEntity(dto)));
    }
}
