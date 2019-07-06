package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.*;
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
    private static final Integer TEMPO_DEFAULT = 1;

    private final SessaoVotacaoRepository repository;
    private final VotacaoService votacaoService;
    private final PautaService pautaService;

    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository repository, VotacaoService votacaoService, PautaService pautaService) {
        this.repository = repository;
        this.votacaoService = votacaoService;
        this.pautaService = pautaService;
    }


    public SessaoVotacaoDTO abrirSessaoVotacao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO) {
        LOGGER.info("Abrir sessao de votacao para a pauta {}", sessaoVotacaoAbrirDTO.getOidPauta());

        PautaDTO pautaDTO = pautaService.buscarPautaPeloOID(sessaoVotacaoAbrirDTO.getOidPauta());
        VotacaoDTO votacaoDTO = votacaoService.iniciarVotacao(pautaDTO);

        SessaoVotacaoDTO dto = new SessaoVotacaoDTO(
                null,
                votacaoDTO,
                LocalDateTime.now(),
                calcularTempo(sessaoVotacaoAbrirDTO.getTempo()),
                Boolean.TRUE);
        return salvar(dto);
    }

    public List<SessaoVotacaoAndamentoDTO> buscarSessaoesEmAndamento() {
        LOGGER.info("Buscando sessoes em andamento");
        List<SessaoVotacaoAndamentoDTO> list = repository.buscarTodasSessoesEmAndamento(Boolean.TRUE)
                .stream()
                .map(SessaoVotacaoAndamentoDTO::toDTOAndamento)
                .collect(Collectors.toList());

        return list
                .stream()
                .filter(dto -> dto.getDataHoraFim().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void encerraoSessaoVotacao(SessaoVotacaoAndamentoDTO dto) {
        LOGGER.info("Encerrando sessao com tempo de duracao expirado {}", dto.getOid());
        dto.setAtiva(Boolean.FALSE);
        salvar(buscarSessaoVotacaoPeloOID(dto.getOid()));
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
        LOGGER.info("salvar sessao de votacao para a pauta {}", dto.getVotacaoDTO().getPautaDTO().getOid());
        if (Optional.ofNullable(dto).isPresent()) {
            return SessaoVotacaoDTO.toDTO(repository.save(SessaoVotacaoDTO.toEntity(dto)));
        }
        return null;
    }
}
