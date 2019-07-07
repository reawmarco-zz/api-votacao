package br.com.servico.votacao.tests.integracao;

import br.com.servico.votacao.dto.ResultadoDTO;
import br.com.servico.votacao.dto.VotoDTO;
import br.com.servico.votacao.entity.Associado;
import br.com.servico.votacao.entity.Pauta;
import br.com.servico.votacao.entity.SessaoVotacao;
import br.com.servico.votacao.entity.Votacao;
import br.com.servico.votacao.repository.AssociadoRepository;
import br.com.servico.votacao.repository.PautaRepository;
import br.com.servico.votacao.repository.SessaoVotacaoRepository;
import br.com.servico.votacao.repository.VotacaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VotacaoControllerTest {

    private static final String url = "/api/v1/votacoes";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private VotacaoRepository repository;
    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;
    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    public void deveraRealizarVoto_quandoRetornaSucesso() {

        this.pautaRepository.deleteAll();
        this.sessaoVotacaoRepository.deleteAll();
        this.associadoRepository.deleteAll();

        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        sessaoVotacao = this.sessaoVotacaoRepository.save(sessaoVotacao);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VotoDTO(pauta.getOid(),
                        sessaoVotacao.getOid(),
                        Boolean.TRUE, "47819429038"),
                String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo("Voto validado");
    }

    @Test
    public void deveraRetornarResultadoVotacao_quandoRetornaSucesso() {
        this.pautaRepository.deleteAll();
        this.sessaoVotacaoRepository.deleteAll();

        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        sessaoVotacao = this.sessaoVotacaoRepository.save(sessaoVotacao);

        repository.save(new Votacao(null, Boolean.TRUE, pauta.getOid(), sessaoVotacao.getOid()));
        repository.save(new Votacao(null, Boolean.TRUE, pauta.getOid(), sessaoVotacao.getOid()));
        repository.save(new Votacao(null, Boolean.TRUE, pauta.getOid(), sessaoVotacao.getOid()));
        repository.save(new Votacao(null, Boolean.TRUE, pauta.getOid(), sessaoVotacao.getOid()));
        repository.save(new Votacao(null, Boolean.FALSE, pauta.getOid(), sessaoVotacao.getOid()));
        repository.save(new Votacao(null, Boolean.FALSE, pauta.getOid(), sessaoVotacao.getOid()));
        repository.save(new Votacao(null, Boolean.FALSE, pauta.getOid(), sessaoVotacao.getOid()));

        sessaoVotacao = new SessaoVotacao(sessaoVotacao.getOid(), sessaoVotacao.getDataHoraInicio(), sessaoVotacao.getDataHoraFim(), Boolean.FALSE);
        this.sessaoVotacaoRepository.save(sessaoVotacao);

        ResponseEntity<ResultadoDTO> responseEntity = restTemplate.getForEntity(url.concat("/resultado/{oidPauta}/{oidSessaoVotacao}"),
                ResultadoDTO.class,
                pauta.getOid(),
                sessaoVotacao.getOid());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getVotacaoDTO().getOidSessaoVotacao()).isEqualTo(sessaoVotacao.getOid());
        assertThat(responseEntity.getBody().getVotacaoDTO().getQuantidadeVotosSim()).isEqualTo(4);
        assertThat(responseEntity.getBody().getVotacaoDTO().getQuantidadeVotosNao()).isEqualTo(3);
    }

    @Test
    public void deveraRealizarVoto_quandoRetornaErro_cpfAssociadoInvalido() {

        this.pautaRepository.deleteAll();
        this.sessaoVotacaoRepository.deleteAll();
        this.associadoRepository.deleteAll();

        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        sessaoVotacao = this.sessaoVotacaoRepository.save(sessaoVotacao);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VotoDTO(pauta.getOid(),
                        sessaoVotacao.getOid(),
                        Boolean.TRUE, "123"),
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deveraRealizarVoto_quandoRetornaErro_sessaoEncerrada() {

        this.pautaRepository.deleteAll();
        this.sessaoVotacaoRepository.deleteAll();
        this.associadoRepository.deleteAll();

        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE);
        sessaoVotacao = this.sessaoVotacaoRepository.save(sessaoVotacao);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VotoDTO(pauta.getOid(),
                        sessaoVotacao.getOid(),
                        Boolean.TRUE,
                        "47819429038"),
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }

    @Test
    public void deveraRealizarVoto_quandoRetornaErro_400_quandoAssociado_votouNovamente() {

        this.pautaRepository.deleteAll();
        this.sessaoVotacaoRepository.deleteAll();
        this.associadoRepository.deleteAll();

        Pauta pauta = new Pauta(null, "Teste Pauta 1");
        pauta = this.pautaRepository.save(pauta);

        SessaoVotacao sessaoVotacao = new SessaoVotacao(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), Boolean.TRUE);
        sessaoVotacao = this.sessaoVotacaoRepository.save(sessaoVotacao);

        Associado associado = new Associado(null, "47819429038", pauta.getOid());
        associadoRepository.save(associado);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.concat("/votar"),
                new VotoDTO(pauta.getOid(),
                        sessaoVotacao.getOid(),
                        Boolean.TRUE,
                        "47819429038"),
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
