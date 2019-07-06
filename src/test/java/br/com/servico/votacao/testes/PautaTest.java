package br.com.servico.votacao.testes;

import br.com.servico.votacao.entity.Pauta;
import br.com.servico.votacao.repository.PautaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PautaTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    private PautaRepository repository;

    @Test
    public void quandoChamadoSeDescricao_entaoRetornaCerto() {
        Pauta pauta = new Pauta(1, "Pauta Teste Descricao");
        pauta.setDescricao("Pauta Teste Descricao Set");
        assertThat(pauta.getDescricao()).isEqualTo("Pauta Teste Descricao Set");
    }

    @Test
    public void quandoChamadoGetDescricao_entaoRetornaCerto() {
        Pauta pauta = new Pauta(1, "Pauta Teste Descricao");
        assertThat(pauta.getDescricao()).isEqualTo("Pauta Teste Descricao");
    }

    @Test
    public void devePersistirPauta() {
        Pauta pauta = new Pauta(null, "Pauta Teste Descricao");
        this.repository.save(pauta);
        assertThat(pauta.getOid()).isNotNull();
        assertThat(pauta.getDescricao()).isEqualTo("Pauta Teste Descricao");
    }

}
