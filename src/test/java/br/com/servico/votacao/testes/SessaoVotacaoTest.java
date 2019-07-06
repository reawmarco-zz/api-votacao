package br.com.servico.votacao.testes;

import br.com.servico.votacao.entity.SessaoVotacao;
import br.com.servico.votacao.repository.SessaoVotacaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SessaoVotacaoTest {

    @Autowired
    private SessaoVotacaoRepository repository;

    @Test
    public void devePersistirSessaoVotacao() {
        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        this.repository.save(sessaoVotacao);
        assertThat(sessaoVotacao.getOid()).isNotNull();
    }

    @Test
    public void deveRetornarVerdadeiroParaListaDeSessoesVotacaoEmAndamento() {
        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        this.repository.save(sessaoVotacao);
        assertThat(this.repository.buscarTodasSessoesEmAndamento(Boolean.TRUE)).isNotEmpty();
    }

    @Test
    public void deveRetornarVerdadeiroParaListaVaziaDeSessoesVotacaoEmAndamento() {
        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.FALSE);
        this.repository.save(sessaoVotacao);
        assertThat(this.repository.buscarTodasSessoesEmAndamento(Boolean.TRUE)).isEmpty();
    }

    @Test
    public void deveRetornarVerdadeiroParaBuscaSessaoExistenteAtiva() {
        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        this.repository.save(sessaoVotacao);
        assertThat(this.repository.existsByOidAndAndAtiva(sessaoVotacao.getOid(), Boolean.TRUE)).isTrue();
    }

    @Test
    public void deveRetornarFalseParaBuscaSessaoExistenteAtiva() {
        this.repository.deleteAll();
        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.FALSE);
        this.repository.save(sessaoVotacao);
        assertThat(this.repository.existsByOidAndAndAtiva(sessaoVotacao.getOid(), Boolean.TRUE)).isFalse();
    }
}
