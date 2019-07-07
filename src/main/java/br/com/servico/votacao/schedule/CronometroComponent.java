package br.com.servico.votacao.schedule;

import br.com.servico.votacao.dto.SessaoVotacaoDTO;
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
        LOGGER.debug("Contador de tempo sendo excutado...");
        List<SessaoVotacaoDTO> list = sessaoVotacaoService.buscarSessaoesEmAndamento();
        LOGGER.debug("Quantidade de sessoes abertas  = {}", list.size());
        list.forEach(dto -> {
            LOGGER.debug("Sessao encerrada {}", dto.getOid());
            if (dto.getAtiva()) {
                sessaoVotacaoService.encerraoSessaoVotacao(dto);
            }
        });
    }
}