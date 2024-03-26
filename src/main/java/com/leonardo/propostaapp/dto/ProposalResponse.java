package com.leonardo.propostaapp.dto;

import java.text.NumberFormat;

import com.leonardo.propostaapp.entity.Proposal;

public record ProposalResponse(
        String nome,
        String sobrenome,
        String telefone,
        String cpf,
        Double renda,
        String valorSolicitadoFmt,
        int prazoPagamento,
        Boolean aprovada,
        String observacao) {
    public ProposalResponse(Proposal proposal) {
        this(
                proposal.getUser().getName().toUpperCase(),
                proposal.getUser().getLastName().toUpperCase(),
                proposal.getUser().getTellPhone(),
                proposal.getUser().getCpf(),
                proposal.getUser().getFinancialIncome(),
                NumberFormat.getCurrencyInstance().format(proposal.getProposalValue()),
                proposal.getPaymentTerm(),
                proposal.getApproved(),
                proposal.getObservation());
    }
}
