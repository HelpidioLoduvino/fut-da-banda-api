package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.repository.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public void deleteFieldById(Long id){
        this.fieldRepository.deleteById(id);
    }

    public Page<Field> findAll(Pageable pageable){
        return fieldRepository.findAll(pageable);
    }

    public Optional<Field> findFieldByName(String name){
        return fieldRepository.findByNameEqualsIgnoreCase(name);
    }
}