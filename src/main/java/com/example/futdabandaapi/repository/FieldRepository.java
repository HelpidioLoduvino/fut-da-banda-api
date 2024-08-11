package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    Optional<Field> findByNameEqualsIgnoreCase(String name);
    List<Field> findAllByStatus(String status);
}
