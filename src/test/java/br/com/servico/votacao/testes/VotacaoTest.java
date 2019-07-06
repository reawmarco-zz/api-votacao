package br.com.servico.votacao.testes;

import br.com.servico.votacao.entity.Votacao;
import br.com.servico.votacao.repository.VotacaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VotacaoTest {

    @Autowired
    VotacaoRepository repository;

    @Test
    public void devePersistirVotacao() {
        Votacao votacao = new Votacao(null, Boolean.TRUE, 1, 1);
        this.repository.save(votacao);
        assertThat(votacao.getOid()).isNotNull();
    }

    @Test
    public void deveRetornarValorIgualUmParaConsultaTotaldeVotos() {
        Votacao votacao = new Votacao(null, Boolean.TRUE, 1, 1);
        this.repository.save(votacao);
        assertThat(this.repository.countVotacaoByOidPautaAndOidSessaoVotacaoAndVoto(1, 1, Boolean.TRUE)).isEqualTo(1);
    }

    @Test
    public void deveRetornarValorIgualZeroParaConsultaTotaldeVotos() {
        Votacao votacao = new Votacao(null, Boolean.TRUE, 1, 1);
        this.repository.save(votacao);
        assertThat(this.repository.countVotacaoByOidPautaAndOidSessaoVotacaoAndVoto(1, 1, Boolean.FALSE)).isEqualTo(0);
    }
}
