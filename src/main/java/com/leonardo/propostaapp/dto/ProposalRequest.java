package com.leonardo.propostaapp.dto;

import com.leonardo.propostaapp.entity.Proposal;
import com.leonardo.propostaapp.entity.User;

public record ProposalRequest(
                String nome,
                String sobrenome,
                String cpf,
                String telefone,
                Double renda,
                Double valorSolicitado,
                int prazoPagamento) {
        public Proposal toProposal(User user) {
                return new Proposal(null, this.valorSolicitado(), this.prazoPagamento(),
                                null, true, null, user);
        }

        public User toUser() {
                return new User(null, this.nome(), this.sobrenome(),
                                this.cpf(), this.telefone(), this.renda(), null);
        }
}
