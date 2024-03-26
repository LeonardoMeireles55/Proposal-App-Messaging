package com.leonardo.propostaapp.service;

import com.leonardo.propostaapp.dto.ProposalResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate template;

    public void notify(ProposalResponse proposal) {
        template.convertAndSend("/propostas", proposal);
    }
}