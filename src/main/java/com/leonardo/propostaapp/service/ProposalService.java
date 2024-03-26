package com.leonardo.propostaapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leonardo.propostaapp.dto.ProposalRequest;
import com.leonardo.propostaapp.dto.ProposalResponse;
import com.leonardo.propostaapp.entity.Proposal;
import com.leonardo.propostaapp.repository.ProposalRepository;

@Service
public class ProposalService {

    private final NotificationRabbitService notificacaoRabbitService;

    private final String exchange;

    private final ProposalRepository proposalRepository;

    public ProposalService(ProposalRepository proposalRepository,
            NotificationRabbitService notificacaoRabbitService,
            @Value("${rabbitmq.pending-proposal.exchange}") String pendingProposalExchange) {
        this.proposalRepository = proposalRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = pendingProposalExchange;
    }

    private void notifyRabbit(Proposal proposta, MessagePostProcessor messagePostProcessor) {
        try {
            notificacaoRabbitService.notify(proposta, exchange, messagePostProcessor);
        } catch (RuntimeException ex) {
            proposalRepository.setIntegratedById(proposta.getId(), false);
        }
    }

    public Proposal createProposal(ProposalRequest proposalRequestDTO) {
        var userRequest = (proposalRequestDTO.toUser());
        var proposalRequest = proposalRequestDTO.toProposal(userRequest);
        var priority = proposalRequest.getUser().getFinancialIncome() > 10000 ? 10 : 5;

        var response = proposalRepository.save(proposalRequest);

        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setPriority(priority);
            return message;
        };

        notifyRabbit(response, messagePostProcessor);

        return response;
    }

    public List<ProposalResponse> getProposals() {
        return proposalRepository.findAll().stream().map(ProposalResponse::new).collect(Collectors.toList());
    }
}
