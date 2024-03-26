package com.leonardo.propostaapp.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leonardo.propostaapp.repository.ProposalRepository;
import com.leonardo.propostaapp.service.NotificationRabbitService;

import java.util.concurrent.TimeUnit;

@Component
public class PendingProposal {

    private final ProposalRepository proposalRepository;
    private final NotificationRabbitService notificacaoRabbitService;
    private final String exchange;
    private final Logger logger = LoggerFactory.getLogger(PendingProposal.class);

    public PendingProposal(ProposalRepository proposalRepository,
            NotificationRabbitService notificacaoRabbitService,
            @Value("${rabbitmq.pending-proposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notify(proposta, exchange);
                proposalRepository.setIntegratedById(proposta.getId(), true);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }
}