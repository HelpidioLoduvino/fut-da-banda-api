package com.example.futdabandaapi.services;

import com.example.futdabandaapi.entities.Field;
import com.example.futdabandaapi.repositories.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    public ResponseEntity<Field> newField(Field field){
        return ResponseEntity.ok(this.fieldRepository.save(field));
    }

    public ResponseEntity<Field> findFieldById(Long id){
        if(this.fieldRepository.findById(id).isPresent())
            return ResponseEntity.ok(this.fieldRepository.findById(id).get());

        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<Field> updateFieldById(Field field, Long id){
        if(fieldRepository.findById(id).isPresent()){
            return ResponseEntity.ok(this.fieldRepository.save(field));
        }

        return ResponseEntity.status(404).body(null);
    }

    public void deleteFieldById(long id){
        this.fieldRepository.deleteById(id);
    }

    public ResponseEntity<List<Field>> findAvailableFields(){
        return ResponseEntity.ok(this.fieldRepository.findAllByOccupiedIsFalse());
    }

    public ResponseEntity<List<Field>> findFieldsByLocation(String location){
        return ResponseEntity.ok(this.fieldRepository.findAllByLocationEqualsIgnoreCase(location));
    }

    public ResponseEntity<Optional<Field>> findFieldByName(String name){
        return ResponseEntity.ok(this.fieldRepository.findByNameEqualsIgnoreCase(name));
    }

    public ResponseEntity<List<Field>> findAllFields(){
        return ResponseEntity.ok(this.fieldRepository.findAll());
    }
}