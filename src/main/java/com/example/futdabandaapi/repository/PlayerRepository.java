package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.dto.PlayerDto;
import com.example.futdabandaapi.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select new com.example.futdabandaapi.dto.PlayerDto(p.id, p.fullName, p.email, p.userRole, p.photo, p.position, p.gender, p.biography, p.available) from Player p where p.available = 'Disponível'")
    Page<PlayerDto> findAllAvailablePlayers(Pageable pageable);

}
