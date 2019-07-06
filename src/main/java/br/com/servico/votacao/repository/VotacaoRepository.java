package br.com.servico.votacao.repository;

import br.com.servico.votacao.entity.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

    Integer countVotacaoByOidPautaAndOidSessaoVotacaoAndVoto(Integer oidPauta, Integer oidSessaoVotacao, Boolean voto);
}
