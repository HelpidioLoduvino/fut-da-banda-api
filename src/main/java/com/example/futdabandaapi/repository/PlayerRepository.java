package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.dto.PlayerDto;
import com.example.futdabandaapi.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Page<Player> findAllByAvailableAndIdNot(Pageable pageable, String available, Long id);


}
