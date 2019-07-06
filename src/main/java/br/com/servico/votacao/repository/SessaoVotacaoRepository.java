package br.com.servico.votacao.repository;

import br.com.servico.votacao.entity.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer> {


    @Query("select s.oid, s.ativa, s.dataHoraInicio, s.dataHoraFim from SessaoVotacao s where s.ativa:=true")
    List<SessaoVotacao> buscarTodasSessoesEmAndamento();

}
