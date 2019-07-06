package br.com.servico.votacao.schedule;

import br.com.servico.votacao.dto.SessaoVotacaoAndamentoDTO;
import br.com.servico.votacao.service.SessaoVotacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CronometroComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronometroComponent.class);

    private final SessaoVotacaoService sessaoVotacaoService;

    public CronometroComponent(SessaoVotacaoService sessaoVotacaoService) {
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    @Scheduled(cron = "15 * * * * *")
    private void teste() {
        LOGGER.info("Contador de tempo sendo excutado...");
        List<SessaoVotacaoAndamentoDTO> list = sessaoVotacaoService.buscarSessaoesEmAndamento();
        list.forEach(dto -> {
            LOGGER.info("Sessao encerrada {}", dto.getOid());
            sessaoVotacaoService.encerraoSessaoVotacao(dto);
        });
    }
}