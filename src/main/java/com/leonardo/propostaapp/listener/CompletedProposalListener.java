package com.leonardo.propostaapp.listener;

import com.leonardo.propostaapp.dto.ProposalResponse;
import com.leonardo.propostaapp.entity.Proposal;
import com.leonardo.propostaapp.repository.ProposalRepository;
import com.leonardo.propostaapp.service.WebSocketService;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CompletedProposalListener {

    private final ProposalRepository propostaRepository;

    private final WebSocketService webSocketService;

    private void updateProposal(Proposal proposta) {
        propostaRepository.setApprovedAndTextById(proposta.getId(), proposta.getApproved(),  proposta.getObservation());
    }

    @RabbitListener(queues = "${rabbitmq.queue.completed.proposal}")
    public void propostaConcluida(Proposal proposal) {
        updateProposal(proposal);
        webSocketService.notify(new ProposalResponse(proposal));
    }
}