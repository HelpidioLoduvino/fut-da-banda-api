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
    public ResponseEntity<Field> newField(Field field){
        return ResponseEntity.ok(this.repository.save(field));
    }

    public ResponseEntity<Field> findFieldById(long id){
        if(this.repository.findById(id).isPresent())
            return ResponseEntity.ok(this.repository.findById(id).get());

        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<Field> updateFieldById(Field field, long id){
        if(repository.findById(id).isPresent()){
            return ResponseEntity.ok(this.repository.save(field));
        }
        
        return ResponseEntity.status(404).body(null);
    }

    public void deleteFieldById(long id){
        this.repository.deleteById(id);
    }

    public ResponseEntity<List<Field>> findAvailableFields(){
        return ResponseEntity.ok(this.repository.findAllByOccupiedIsFalse());
    }

    public ResponseEntity<List<Field>> findFieldsByLocation(String location){
        return ResponseEntity.ok(this.repository.findAllByLocationEqualsIgnoreCase(location));
    }

    public ResponseEntity<Optional<Field>> findFieldByName(String name){
        return ResponseEntity.ok(this.repository.findByNameEqualsIgnoreCase(name));
    }

    public ResponseEntity<List<Field>> findAllFields(){
        return ResponseEntity.ok(this.repository.findAll());
    }
}
