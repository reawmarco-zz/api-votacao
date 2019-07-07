package br.com.servico.votacao.tests.integracao;

import br.com.servico.votacao.dto.PautaDTO;
import br.com.servico.votacao.entity.Pauta;
import br.com.servico.votacao.repository.PautaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PautaControllerTest {

    private static final String url = "/api/v1/pautas";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PautaRepository repository;

    @Test
    public void deveraCadastrarUmaNovaPauta() {
        ResponseEntity<PautaDTO> responseEntity = restTemplate.postForEntity(url, new PautaDTO(null, "Teste Cadastro Pauta"), PautaDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void deveraConsultarUmaPautaJaCadastra() {
        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        repository.save(pauta);

        ResponseEntity<PautaDTO> responseEntity = restTemplate.getForEntity(url.concat("/{oid}"), PautaDTO.class, 1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getDescricao()).isEqualTo(pauta.getDescricao());
    }

    @Test
    public void deveraCadastrarUmaPautaComValoresNULL_QuandoRetornaErro() {
        ResponseEntity<PautaDTO> responseEntity = restTemplate.postForEntity(url, new PautaDTO(null, null), PautaDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deveraConsultarUmaPautaNaoExistente_QuandoRetornaErro() {
        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        repository.save(pauta);

        ResponseEntity<PautaDTO> responseEntity = restTemplate.getForEntity(url.concat("/{oid}"), PautaDTO.class, 2);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getDescricao()).isNotEqualTo(pauta.getDescricao());
    }
}
