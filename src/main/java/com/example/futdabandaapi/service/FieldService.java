package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.repository.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    public Field newField(Field field){
        return fieldRepository.save(field);
    }

    public Field findFieldById(Long id){
        if(this.fieldRepository.findById(id).isPresent())
            return fieldRepository.findById(id).get();
        return null;
    }

    public Field updateFieldById(Field field, Long id){
        if(fieldRepository.findById(id).isPresent()){
            return fieldRepository.save(field);
        }
        return null;
    }

    public void deleteFieldById(long id){
        this.fieldRepository.deleteById(id);
    }

    public List<Field> findAvailableFields(){
        return fieldRepository.findAllByOccupiedIsFalse();
    }

    public Optional<Field> findFieldByName(String name){
        return fieldRepository.findByNameEqualsIgnoreCase(name);
    }

    public List<Field> findAllFields(){
        return fieldRepository.findAll();
    }
}