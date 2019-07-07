package br.com.servico.votacao.tests.unitarios;

import br.com.servico.votacao.entity.Pauta;
import br.com.servico.votacao.repository.PautaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PautaTest {

    @Autowired
    private PautaRepository repository;

    @Test
    public void devePersistirPauta() {
        Pauta pauta = new Pauta(null, "Pauta Teste Descricao");
        this.repository.save(pauta);
        assertThat(pauta.getOid()).isNotNull();
        assertThat(pauta.getDescricao()).isEqualTo("Pauta Teste Descricao");
    }

    @Test
    public void deveRetornarVerdadeiro() {
        Pauta pauta = new Pauta(null, "Pauta Teste Descricao");
        this.repository.save(pauta);
        assertThat(this.repository.existsByOid(pauta.getOid())).isTrue();
    }

    @Test
    public void deveRetornarFalso() {
        Pauta pauta = new Pauta(null, "Pauta Teste Descricao");
        this.repository.save(pauta);
        assertThat(this.repository.existsByOid(3)).isFalse();
    }

}
