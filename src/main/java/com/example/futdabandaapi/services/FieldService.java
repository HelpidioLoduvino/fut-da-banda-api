package com.example.futdabandaapi.services;

import com.example.futdabandaapi.entities.Field;
import com.example.futdabandaapi.repositories.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final FieldRepository repository;

    public ResponseEntity<Optional<List<Field>>> findFreeFields(){
        return ResponseEntity.ok(this.repository.findAllByOccupiedIsFalse());
    }

    public ResponseEntity<Optional<List<Field>>> findFieldsByLocation(String location){
        return ResponseEntity.ok(this.repository.findAllByLocationEqualsIgnoreCase(location));
    }

    public ResponseEntity<Optional<Field>> findFieldByName(String name){
        return ResponseEntity.ok(this.repository.findByNameEqualsIgnoreCase(name));
    }
}
