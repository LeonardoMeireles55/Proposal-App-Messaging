package com.leonardo.propostaapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.leonardo.propostaapp.dto.ProposalRequest;
import com.leonardo.propostaapp.dto.ProposalResponse;
import com.leonardo.propostaapp.service.ProposalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposta")
public class ProposalController {
    private final ProposalService proposalService;

    @PostMapping()
    public ResponseEntity<ProposalResponse> 
    PostProposal(@RequestBody ProposalRequest proposalRequestDTO, UriComponentsBuilder uriBuilder) {
        var response = proposalService.createProposal(proposalRequestDTO);
        var responseDTO = new ProposalResponse(response);
        var uri = uriBuilder.path("/proposta/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<ProposalResponse>> getProposalList() {
        var response = proposalService.getProposals();
        return ResponseEntity.ok(response);
    }
}
