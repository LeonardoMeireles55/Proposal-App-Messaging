package com.leonardo.propostaapp.service;

import lombok.AllArgsConstructor;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.leonardo.propostaapp.entity.Proposal;

@AllArgsConstructor
@Service
public class NotificationRabbitService {


    private RabbitTemplate rabbitTemplate;

    public void notify(Proposal proposal, String exchange, MessagePostProcessor messagePostProcessor) {
        rabbitTemplate.convertAndSend(exchange, "", proposal, messagePostProcessor);
    }
    public void notify(Proposal proposal, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposal);
    }
}