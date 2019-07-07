package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.SessaoVotacaoAbrirDTO;
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

    /**
     * Se a sessao votacao é valida entao persiste os dados na base e inicia
     * a contagem para o encerramento da mesma.
     *
     * @param sessaoVotacaoAbrirDTO - @{@link SessaoVotacaoAbrirDTO}
     * @return - @{@link SessaoVotacaoDTO}
     */
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

    /**
     * valida se os dados para iniciar uma validacao são consistentes
     * e ja estao persistidos na base de dados
     *
     * @param sessaoVotacaoAbrirDTO - @{@link SessaoVotacaoAbrirDTO}
     * @return - boolean
     */
    @Transactional(readOnly = true)
    public boolean isValidaAbrirSessao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO) {
        if (pautaService.isPautaValida(sessaoVotacaoAbrirDTO.getOidPauta())) {
            return Boolean.TRUE;
        } else {
            throw new NotFoundException("Pauta não localizada oidPauta" + sessaoVotacaoAbrirDTO.getOidPauta());
        }
    }

    /**
     * busca se ha sessoes em andamento, se houver, os dados sao retornados para o validador de tempo
     *
     * @return - List<@{@link SessaoVotacaoDTO}>
     */
    @Transactional(readOnly = true)
    public List<SessaoVotacaoDTO> buscarSessaoesEmAndamento() {
        LOGGER.debug("Buscando sessoes em andamento");
        List<SessaoVotacaoDTO> list = repository.buscarTodasSessoesEmAndamento(Boolean.TRUE)
                .stream()
                .map(SessaoVotacaoDTO::toDTO)
                .collect(Collectors.toList());

        return list
                .stream()
                .filter(dto -> dto.getDataHoraFim().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    /**
     * Quando houver sessao de votacao com o tempo data hora fim expirado,
     * a flag ativo é setado como FALSE e persistido a alteracao na base de dados.
     *
     * @param dto - @{@link SessaoVotacaoDTO}
     */
    @Transactional
    public void encerraoSessaoVotacao(SessaoVotacaoDTO dto) {
        LOGGER.debug("Encerrando sessao com tempo de duracao expirado {}", dto.getOid());
        dto.setAtiva(Boolean.FALSE);
        salvar(buscarSessaoVotacaoPeloOID(dto.getOid()));
    }

    /**
     * @param oid - @{@link SessaoVotacaoDTO} ID
     * @return - @{@link SessaoVotacaoDTO}
     */
    @Transactional(readOnly = true)
    public SessaoVotacaoDTO buscarSessaoVotacaoPeloOID(Integer oid) {
        Optional<SessaoVotacao> optionalSessaoVotacao = repository.findById(oid);
        if (!optionalSessaoVotacao.isPresent()) {
            LOGGER.error("Sessao de votacao nao localizada para o oid {}", oid);
            throw new NotFoundException("Sessão de votação não localizada para o oid " + oid);
        }
        return SessaoVotacaoDTO.toDTO(optionalSessaoVotacao.get());
    }

    /**
     * Se houver a sessao de ID informado e com a tag ativa igual a TRUE
     * entao e considerada como valida para votacao.
     *
     * @param oid - @{@link SessaoVotacao} ID
     * @return - boolean
     */
    @Transactional(readOnly = true)
    public boolean isSessaoVotacaoValida(Integer oid) {
        return repository.existsByOidAndAndAtiva(oid, Boolean.TRUE);
    }

    /**
     * Se houver a sessao de ID informado e com tag ativa igual a FALSE
     * entao e considerada como valida para contagem
     *
     * @param oid - @{@link SessaoVotacao} ID
     * @return - boolean
     */
    @Transactional(readOnly = true)
    public boolean isSessaoValidaParaContagem(Integer oid) {
        return repository.existsByOidAndAndAtiva(oid, Boolean.FALSE);
    }

    /**
     * Com base no LocalDateTime inicial é calculada a LocalDateTime final somando-se o
     * tempo em minutos informado na chamada do servico.
     * <p>
     * Se o tempo nao for informado ou for informado com valor 0,
     * entao é considerado o tempo de 1 minuto como default.
     *
     * @param tempo - tempo em minutos
     * @return - localDateTime
     */
    private LocalDateTime calcularTempo(Integer tempo) {
        if (tempo != null && tempo != 0) {
            return LocalDateTime.now().plusMinutes(tempo);
        } else {
            return LocalDateTime.now().plusMinutes(TEMPO_DEFAULT);
        }
    }

    /**
     * @param dto - @{@link SessaoVotacaoDTO}
     * @return - @{@link SessaoVotacaoDTO}
     */
    @Transactional
    public SessaoVotacaoDTO salvar(SessaoVotacaoDTO dto) {
        LOGGER.debug("Salvando a sessao de votacao");
        if (Optional.ofNullable(dto).isPresent()) {
            return SessaoVotacaoDTO.toDTO(repository.save(SessaoVotacaoDTO.toEntity(dto)));
        }
        return null;
    }
}
