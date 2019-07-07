package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.SessaoVotacaoAbrirDTO;
import br.com.servico.votacao.dto.SessaoVotacaoAndamentoDTO;
import br.com.servico.votacao.dto.SessaoVotacaoDTO;
import br.com.servico.votacao.entity.SessaoVotacao;
import br.com.servico.votacao.exception.NotFoundException;
import br.com.servico.votacao.repository.SessaoVotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);
    private static final Integer TEMPO_DEFAULT = 1;

    private final SessaoVotacaoRepository repository;
    private final PautaService pautaService;


    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository repository, PautaService pautaService) {
        this.repository = repository;
        this.pautaService = pautaService;
    }

    @Transactional
    public SessaoVotacaoDTO abrirSessaoVotacao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO) {
        LOGGER.debug("Abrindo a sessao de votacao para a pauta {}", sessaoVotacaoAbrirDTO.getOidPauta());

        isValidaAbrirSessao(sessaoVotacaoAbrirDTO);

        SessaoVotacaoDTO dto = new SessaoVotacaoDTO(
                null,
                LocalDateTime.now(),
                calcularTempo(sessaoVotacaoAbrirDTO.getTempo()),
                Boolean.TRUE);

        return salvar(dto);
    }

    @Transactional(readOnly = true)
    public void isValidaAbrirSessao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO) {
        pautaService.isPautaValida(sessaoVotacaoAbrirDTO.getOidPauta());
    }

    @Transactional(readOnly = true)
    public List<SessaoVotacaoAndamentoDTO> buscarSessaoesEmAndamento() {
        LOGGER.debug("Buscando sessoes em andamento");
        List<SessaoVotacaoAndamentoDTO> list = repository.buscarTodasSessoesEmAndamento(Boolean.TRUE)
                .stream()
                .map(SessaoVotacaoAndamentoDTO::toDTOAndamento)
                .collect(Collectors.toList());

        return list
                .stream()
                .filter(dto -> dto.getDataHoraFim().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void encerraoSessaoVotacao(SessaoVotacaoAndamentoDTO dto) {
        LOGGER.debug("Encerrando sessao com tempo de duracao expirado {}", dto.getOid());
        dto.setAtiva(Boolean.FALSE);
        salvar(buscarSessaoVotacaoPeloOID(dto.getOid()));
    }

    @Transactional(readOnly = true)
    public SessaoVotacaoDTO buscarSessaoVotacaoPeloOID(Integer oid) {
        Optional<SessaoVotacao> optionalSessaoVotacao = repository.findById(oid);
        if (!optionalSessaoVotacao.isPresent()) {
            LOGGER.error("Sessao de votacao nao localizada para o oid {}", oid);
            throw new NotFoundException("Sessão de votação não localizada para o oid " + oid);
        }
        return SessaoVotacaoDTO.toDTO(optionalSessaoVotacao.get());
    }

    @Transactional(readOnly = true)
    public Boolean isSessaoVotacaoValida(Integer oid) {
        return repository.existsByOidAndAndAtiva(oid, Boolean.TRUE);
    }

    private LocalDateTime calcularTempo(Integer tempo) {
        if (tempo != null && tempo != 0) {
            return LocalDateTime.now().plusMinutes(tempo);
        } else {
            return LocalDateTime.now().plusMinutes(TEMPO_DEFAULT);
        }
    }

    @Transactional
    public SessaoVotacaoDTO salvar(SessaoVotacaoDTO dto) {
        LOGGER.debug("Salvando a sessao de votacao");
        if (Optional.ofNullable(dto).isPresent()) {
            return SessaoVotacaoDTO.toDTO(repository.save(SessaoVotacaoDTO.toEntity(dto)));
        }
        return null;
    }
}
