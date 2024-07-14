package com.example.futdabandaapi.repositories;

import com.example.futdabandaapi.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    public List<Field> findAllByOccupiedIsFalse();
    public List<Field> findAllByLocationEqualsIgnoreCase(String location);
    public Optional<Field> findByNameEqualsIgnoreCase(String name);
}
