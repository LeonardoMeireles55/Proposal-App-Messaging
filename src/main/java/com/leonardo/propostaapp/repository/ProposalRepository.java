package com.leonardo.propostaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leonardo.propostaapp.entity.Proposal;

@Repository
public interface ProposalRepository extends ListCrudRepository<Proposal, Long> {

    List<Proposal> findAllByIntegratedIsFalse();

    List<Proposal> findAllByIntegratedIsTrue();

    @Transactional
    @Modifying
    @Query(value = "UPDATE proposal SET integrated = :integrated WHERE id = :id", nativeQuery = true)
    void setIntegratedById(Long id, boolean integrated);

    @Transactional
    @Modifying
    @Query(value = "UPDATE proposal SET approved = :bool, observation = :observation WHERE id = :id", nativeQuery = true)
    void setApprovedAndTextById(Long id, boolean bool, String observation);
}
