package com.leonardo.propostaapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.pending-proposal.exchange}")
    private String pendingProposalExchange;

    @Value("${rabbitmq.completed-proposal.exchange}")
    private String completedProposalExchange;

    @Bean
    public Queue createPendingProposalQueueForCreditAnalysis() {
        return QueueBuilder.durable("pending-proposal.ms-credit-analysis")
                .deadLetterExchange("pending-proposal-dlx.ex").maxPriority(10)
                .build();
    }

    @Bean
    public Queue createPendingProposalDeadLetterQueue() {
        return QueueBuilder.durable("pending-proposal.dlq").build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange("pending-proposal-dlx.ex").build();
    }

    @Bean
    public Binding createBinding() {
        return BindingBuilder.bind(createPendingProposalDeadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    public Queue createPendingProposalNotificationQueue() {
        return QueueBuilder.durable("pending-proposal.ms-notification").build();
    }

    @Bean
    public Queue createCompletedProposalQueueForProposal() {
        return QueueBuilder.durable("completed-proposal.ms-proposal").build();
    }

    @Bean
    public Queue createCompletedProposalNotificationQueue() {
        return QueueBuilder.durable("completed-proposal.ms-notification").build();
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange createFanoutExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange(pendingProposalExchange).build();
    }

    @Bean
    public FanoutExchange createFanoutExchangeCompletedProposal() {
        return ExchangeBuilder.fanoutExchange(completedProposalExchange).build();
    }

    @Bean
    public Binding createBindingPendingProposalMSCreditAnalysis() {
        return BindingBuilder.bind(createPendingProposalQueueForCreditAnalysis())
                .to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Binding createBindingPendingProposalMSNotification() {
        return BindingBuilder.bind(createPendingProposalNotificationQueue()).to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Binding createBindingCompletedProposalMSProposalApp() {
        return BindingBuilder.bind(createCompletedProposalQueueForProposal())
                .to(createFanoutExchangeCompletedProposal());
    }

    @Bean
    public Binding createBindingCompletedProposalMSNotification() {
        return BindingBuilder.bind(createCompletedProposalNotificationQueue())
                .to(createFanoutExchangeCompletedProposal());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}