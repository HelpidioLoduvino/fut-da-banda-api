package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findAllByRecipientIdAndStatus(Long id, String status);
    List<Invitation> findAllBySenderIdAndStatusOrSenderIdAndStatus(Long id, String status, Long senderId, String senderStatus);
    List<Invitation> findAllBySenderIdAndStatus(Long id, String status);
    long countAllBySeenFalseAndRecipientId(Long recipientId);
}
