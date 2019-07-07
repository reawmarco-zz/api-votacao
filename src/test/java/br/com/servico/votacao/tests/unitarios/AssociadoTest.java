package br.com.servico.votacao.tests.unitarios;

import br.com.servico.votacao.entity.Associado;
import br.com.servico.votacao.repository.AssociadoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AssociadoTest {

    @Autowired
    private AssociadoRepository repository;

    @Test
    public void devePersistirAssociado() {
        Associado associado = new Associado(null, "123123", 1);
        this.repository.save(associado);
        assertThat(associado.getOid()).isNotNull();
        assertThat(associado.getCpfAssociado()).isEqualTo("123123");
    }

    @Test
    public void deveRetornarUmAssociado() {
        Associado associado = new Associado(null, "123123", 1);
        this.repository.save(associado);
        assertThat(this.repository.findById(1)).isNotNull();
    }

    @Test
    public void deveRetonarVerdadeiro() {
        Associado associado = new Associado(null, "123123", 1);
        this.repository.save(associado);
        assertThat(this.repository.existsByCpfAssociadoAndOidPauta("123123", 1)).isTrue();
    }

    @Test
    public void deveRetonarFalso() {
        Associado associado = new Associado(null, "123123", 12);
        this.repository.save(associado);
        assertThat(this.repository.existsByCpfAssociadoAndOidPauta("123123", 1)).isFalse();
    }
}
