package br.com.servico.votacao.service;

import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.dto.VotacaoDTO;
import br.com.servico.votacao.entity.Votacao;
import br.com.servico.votacao.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotacaoService {


    private final VotacaoRepository repository;

    @Autowired
    public VotacaoService(VotacaoRepository repository) {
        this.repository = repository;
    }


    public VotacaoDTO buscarVotacaoPeloOID(Integer oid) {
        Votacao votacao = repository.getOne(oid);

        if (Optional.ofNullable(votacao).isPresent()) {
            return VotacaoDTO.toDTO(votacao);
        }
        return null;
    }


    public void votar() {

    }

    public VotacaoDTO iniciarVotacao(PautaDTO dto) {
        return salvarVotacao(new VotacaoDTO(null, dto));
    }

    private VotacaoDTO salvarVotacao(VotacaoDTO dto) {
        return VotacaoDTO.toDTO(repository.save(VotacaoDTO.toEntity(dto)));
    }
}
