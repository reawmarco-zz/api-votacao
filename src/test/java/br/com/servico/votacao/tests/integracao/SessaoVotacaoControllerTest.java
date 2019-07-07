package br.com.servico.votacao.tests.integracao;

import br.com.servico.votacao.dto.SessaoVotacaoAbrirDTO;
import br.com.servico.votacao.dto.SessaoVotacaoAndamentoDTO;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessaoVotacaoControllerTest {

    private static final String url = "/api/v1/votacoes";
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    public void deveraCadastrarUmaSessaoVotacao_quandoRetornaSucesso() throws Exception {
        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO = new SessaoVotacaoAbrirDTO(pauta.getOid(), null);

        ResponseEntity<SessaoVotacaoAndamentoDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), sessaoVotacaoAbrirDTO, SessaoVotacaoAndamentoDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getAtiva()).isTrue();
        assertThat(responseEntity.getBody().getDataHoraInicio().format(formatter)).isEqualTo(LocalDateTime.now().format(formatter));
        assertThat(responseEntity.getBody().getDataHoraFim().format(formatter)).isEqualTo(LocalDateTime.now().plusMinutes(1).format(formatter));
    }

    @Test
    public void deveraCadastrarUmaSessaoVotacao_quandoRetornaSucesso_e_tempoSessao10_minutos() throws Exception {
        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO = new SessaoVotacaoAbrirDTO(pauta.getOid(), 10);

        ResponseEntity<SessaoVotacaoAndamentoDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), sessaoVotacaoAbrirDTO, SessaoVotacaoAndamentoDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getAtiva()).isTrue();
        assertThat(responseEntity.getBody().getDataHoraInicio().format(formatter)).isEqualTo(LocalDateTime.now().format(formatter));
        assertThat(responseEntity.getBody().getDataHoraFim().format(formatter)).isEqualTo(LocalDateTime.now().plusMinutes(10).format(formatter));
    }

    @Test
    public void deveraCadastrarUmaSessaoVotacao_quandoRetornaErro_404() throws Exception {
        SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO = new SessaoVotacaoAbrirDTO(10, null);

        ResponseEntity<SessaoVotacaoAndamentoDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), sessaoVotacaoAbrirDTO, SessaoVotacaoAndamentoDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deveraCadastrarUmaSessaoVotacao_quandoRetornaErro_400() throws Exception {
        SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO = new SessaoVotacaoAbrirDTO(null, null);

        ResponseEntity<SessaoVotacaoAndamentoDTO> responseEntity = restTemplate.postForEntity(url.concat("/abrir-sessao"), sessaoVotacaoAbrirDTO, SessaoVotacaoAndamentoDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
