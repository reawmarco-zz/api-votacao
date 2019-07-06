package br.com.servico.votacao.repository;

import br.com.servico.votacao.entity.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer> {


    @Query("select s from SessaoVotacao s where s.ativa=:ativo")
    List<SessaoVotacao> buscarTodasSessoesEmAndamento(Boolean ativo);

    Boolean existsByOidAndAndAtiva(Integer oid, Boolean ativa);
}
