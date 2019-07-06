package br.com.servico.votacao.integracao;


import br.com.servico.votacao.controller.PautaController;
import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.repository.PautaRepository;
import br.com.servico.votacao.service.PautaService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PautaIntegrationTest.class)
public class PautaIntegrationTest {

    @Autowired
    private static PautaController pautaController;
    @Autowired
    private static PautaRepository pautaRepository;
    @Autowired
    private static PautaService pautaService;


    @BeforeClass
    public static void setup() {
        pautaService = mock(PautaService.class);
        pautaRepository = mock(PautaRepository.class);
        pautaService = new PautaService(pautaRepository);
        pautaController = new PautaController(pautaService);
    }


    @Test
    public void quandoSalvarPauta_retornoCorreto() {
        PautaDTO pautaDTO = new PautaDTO(null, "Pauta Teste Descricao");
        //     assertThat(pautaController.salvarPauta(pautaDTO)).isEqualTo(pautaDTO.getTitulo());
    }

    @Test
    public void quandoBuscarPelaPautaPeloOID_retornoCorreto() {
        PautaDTO pautaDTO = new PautaDTO(null, "Pauta Teste Descricao");
        pautaService.salvar(pautaDTO);
        //     assertThat(pautaController.buscarPautaPeloOID(1)).isEqualTo(pautaDTO.getTitulo());
    }
}