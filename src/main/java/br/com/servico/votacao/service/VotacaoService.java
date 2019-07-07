package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.*;
import br.com.servico.votacao.exception.NotFoundException;
import br.com.servico.votacao.exception.SessoEncerradaException;
import br.com.servico.votacao.exception.VotoInvalidoException;
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

    /**
     * metodo responsavel por realizar as validacoes antes do voto ser computado
     * e persistido na base de dados
     *
     * @param dto - @{@link VotarDTO}
     * @return - boolean
     */
    @Transactional(readOnly = true)
    public boolean isValidaVoto(VotarDTO dto) {
        LOGGER.debug("Validando os dados para voto oidSessao = {}, oidPauta = {}, oidAssiciado = {}", dto.getOidSessaoVotacao(), dto.getOidPauta(), dto.getCpfAssociado());

        if (!pautaService.isPautaValida(dto.getOidPauta())) {

            LOGGER.error("Pauta nao localizada para votacao oidPauta {}", dto.getCpfAssociado());
            throw new NotFoundException("Pauta não localizada oid " + dto.getOidPauta());

        } else if (!sessaoVotacaoService.isSessaoVotacaoValida(dto.getOidSessaoVotacao())) {

            LOGGER.error("Tentativa de voto para sessao encerrada oidSessaoVotacao {}", dto.getOidSessaoVotacao());
            throw new SessoEncerradaException("Sessão de votação já encerrada");

        } else if (!associadoService.isAssociadoPodeVotar(dto.getCpfAssociado())) {

            LOGGER.error("Associado nao esta habilitado para votar {}", dto.getCpfAssociado());
            throw new VotoInvalidoException("Não é possível votar mais de 1 vez na mesma pauta");

        } else if (!associadoService.isValidaParticipacaoAssociadoVotacao(dto.getCpfAssociado(), dto.getOidPauta())) {

            LOGGER.error("Associado tentou votar mais de 1 vez oidAssociado {}", dto.getCpfAssociado());
            throw new VotoInvalidoException("Não é possível votar mais de 1 vez na mesma pauta");
        }

        return Boolean.TRUE;
    }

    /**
     * Se os dados informados para o voto, forem considerados validos
     * entao o voto é computado e persistido na base de dados.
     *
     * @param dto - @{@link VotarDTO}
     * @return - String
     */
    @Transactional
    public String votar(VotarDTO dto) {
        if (isValidaVoto(dto)) {
            LOGGER.debug("Dados validos para voto oidSessao = {}, oidPauta = {}, oidAssiciado = {}", dto.getOidSessaoVotacao(), dto.getOidPauta(), dto.getCpfAssociado());

            VotacaoDTO votacaoDTO = new VotacaoDTO(null,
                    dto.getOidPauta(),
                    dto.getOidSessaoVotacao(),
                    dto.getVoto(),
                    null,
                    null);

            registrarVoto(votacaoDTO);

            registrarAssociadoVotou(dto);

            return "Voto validado";
        }
        return null;
    }

    /**
     * Apos voto ser computado. O associado e registrado na base de dados a fim de
     * evitar que o mesmo possa votar novamente na mesma sessao de votacao e na mesma pauta.
     * <p>
     * A opcao de voto nao e persistido na base de dados.
     *
     * @param dto - @{@link VotarDTO}
     */
    @Transactional
    public void registrarAssociadoVotou(VotarDTO dto) {
        AssociadoDTO associadoDTO = new AssociadoDTO(null, dto.getCpfAssociado(), dto.getOidPauta());
        associadoService.salvarAssociado(associadoDTO);
    }

    /**
     * @param dto - @{@link VotacaoDTO}
     */
    @Transactional
    public void registrarVoto(VotacaoDTO dto) {
        LOGGER.debug("Salvando o voto para oidPauta {}", dto.getOidPauta());
        repository.save(VotacaoDTO.toEntity(dto));
    }

    /**
     * Realiza a busca e contagem dos votos positivos e negativos para determinada sessao e pauta de votacao.
     *
     * @param oidPauta         - @{@link br.com.servico.votacao.entity.Pauta} ID
     * @param oidSessaoVotacao - @{@link br.com.servico.votacao.entity.SessaoVotacao} ID
     * @return - @{@link VotacaoDTO}
     */
    @Transactional(readOnly = true)
    public VotacaoDTO buscarResultadoVotacao(Integer oidPauta, Integer oidSessaoVotacao) {
        LOGGER.debug("Contabilizando os votos para oidPauta = {}, oidSessaoVotacao = {}", oidPauta, oidSessaoVotacao);
        VotacaoDTO dto = new VotacaoDTO();

        dto.setOidPauta(oidPauta);
        dto.setOidSessaoVotacao(oidSessaoVotacao);

        dto.setQuantidadeVotosSim(repository.countVotacaoByOidPautaAndOidSessaoVotacaoAndVoto(oidPauta, oidSessaoVotacao, Boolean.TRUE));
        dto.setQuantidadeVotosNao(repository.countVotacaoByOidPautaAndOidSessaoVotacaoAndVoto(oidPauta, oidSessaoVotacao, Boolean.FALSE));

        return dto;
    }

    /**
     * Realiza a montagem dos objetos referente ao resultado de determinada sessao e pauta de votacao.
     * <p>
     * Contagem somente e realizada apos a finalizacao da sessao.
     *
     * @param oidPauta         - @{@link br.com.servico.votacao.entity.Pauta} ID
     * @param oidSessaoVotacao - @{@link br.com.servico.votacao.entity.SessaoVotacao} ID
     * @return - @{@link ResultadoDTO}
     */
    @Transactional(readOnly = true)
    public ResultadoDTO buscarDadosResultadoVotacao(Integer oidPauta, Integer oidSessaoVotacao) {
        if (sessaoVotacaoService.isSessaoValidaParaContagem(oidSessaoVotacao)) {
            LOGGER.debug("Construindo o objeto de retorno do resultado para oidPauta = {}, oidSessaoVotacao = {}", oidPauta, oidSessaoVotacao);
            PautaDTO pautaDTO = pautaService.buscarPautaPeloOID(oidPauta);
            VotacaoDTO votacaoDTO = buscarResultadoVotacao(oidPauta, oidSessaoVotacao);
            return new ResultadoDTO(pautaDTO, votacaoDTO);
        }

        throw new NotFoundException("Sessão de votação ainda está aberta, não é possível obter a contagem do resultado.");
    }
}