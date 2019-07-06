package br.com.servico.votacao.service;

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


    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository repository, VotacaoService votacaoService) {
        this.repository = repository;
        this.votacaoService = votacaoService;
    }


    public void abrirSessaoVotacao(Integer oidVotacao, Integer tempo) {
        VotacaoDTO votacaoDTO = votacaoService.buscarVotacaoPeloOID(oidVotacao);

        if (Optional.ofNullable(votacaoDTO).isPresent()) {
            SessaoVotacaoDTO dto = new SessaoVotacaoDTO(
                    null,
                    votacaoDTO,
                    LocalDateTime.now(),
                    null,
                    tempo,
                    Boolean.TRUE);
            dto = salvar(dto);

        }
    }

    private SessaoVotacaoDTO buscarSessaoVotacaoPeloOID(Integer oid) {
        SessaoVotacao sessaoVotacao = repository.getOne(oid);
        if (Optional.ofNullable(sessaoVotacao).isPresent()) {
            return SessaoVotacaoDTO.toDTO(sessaoVotacao);
        }
        return null;
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

    private SessaoVotacaoDTO salvar(SessaoVotacaoDTO dto) {
        return SessaoVotacaoDTO.toDTO(repository.save(SessaoVotacaoDTO.toEntity(dto)));
    }

    public void encerraoSessaoVotacao(SessaoVotacaoDTO dto) {
        dto.setAtiva(Boolean.FALSE);
        repository.save(SessaoVotacaoDTO.toEntity(dto));
    }
}
