package br.com.servico.votacao.unitarios;

import br.com.servico.votacao.entity.Pauta;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PautaTest {

    @Test
    public void quandoChamadoGetDescricao_entaoRetornaCerto() {
        Pauta pauta = new Pauta(1, "Pauta Teste Descricao");


        assertThat(pauta.getDescricao()).isEqualTo("Pauta Teste Descricao");
    }

    @Test
    public void quandoChamadoSeDescricao_entaoRetornaCerto() {
        Pauta pauta = new Pauta(1, "Pauta Teste Descricao");

        pauta.setDescricao("Pauta Teste Descricao Set");

        assertThat(pauta.getDescricao()).isEqualTo("Pauta Teste Descricao Set");
    }

}
