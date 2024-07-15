package com.example.futdabandaapi.repositories;

import com.example.futdabandaapi.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findAllByOccupiedIsFalse();
    List<Field> findAllByLocationEqualsIgnoreCase(String location);
    Optional<Field> findByNameEqualsIgnoreCase(String name);
}
