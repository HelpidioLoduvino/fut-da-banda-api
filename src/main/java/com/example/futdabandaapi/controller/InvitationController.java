package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Invitation;
import com.example.futdabandaapi.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/invite")
    public ResponseEntity<Invitation> createInvitation(@RequestParam("playerId") Long playerId) {
        return ResponseEntity.ok(invitationService.invite(playerId));
    }

    @PostMapping("/join")
    public ResponseEntity<Invitation> joinInvitation(@RequestParam("clubId") Long clubId) {
        return ResponseEntity.ok(invitationService.askToJoin(clubId));
    }

    @GetMapping("/captain")
    public ResponseEntity<List<Invitation>> getCaptainInvitations() {
        return ResponseEntity.ok(invitationService.getCaptainInvitations());
    }

    @GetMapping("/player")
    public ResponseEntity<List<Invitation>> getPlayerInvitations() {
        return ResponseEntity.ok(invitationService.getPlayerInvitations());
    }

    @GetMapping("/sender")
    public ResponseEntity<List<Invitation>> getSenderInvitations() {
        return ResponseEntity.ok(invitationService.getSenderInvitations());
    }

    @PutMapping("/accept-invitation")
    public ResponseEntity<Invitation> acceptInvitation(@RequestParam("invitationId") Long invitationId) {
        return ResponseEntity.ok(invitationService.acceptInvitation(invitationId));
    }

    @PutMapping("/accept-player")
    public ResponseEntity<Invitation> acceptPlayerPermission(@RequestParam("invitationId") Long invitationId) {
        return ResponseEntity.ok(invitationService.acceptPlayerPermission(invitationId));
    }

    @PutMapping("/reject-invitation")
    public ResponseEntity<Invitation> rejectInvitation(@RequestParam("invitationId") Long invitationId) {
        return ResponseEntity.ok(invitationService.rejectInvitation(invitationId));
    }

    @PutMapping("/reject-player")
    public ResponseEntity<Invitation> rejectPlayerPermission(@RequestParam("invitationId") Long invitationId) {
        return ResponseEntity.ok(invitationService.rejectPlayer(invitationId));
    }

    @GetMapping("/club")
    public ResponseEntity<List<Invitation>> clubInvitations(){
        return ResponseEntity.ok(invitationService.clubInvitations());
    }

    @GetMapping("/unseen")
    public long countUnseenInvitations() {
        return invitationService.unseenInvitations();
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Invitation>> getPlayerPermissions(){
        return ResponseEntity.ok(invitationService.playerAlreadyAskedForPermission());
    }
}