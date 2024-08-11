package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.repository.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    public Field save(Field field){
        return fieldRepository.save(field);
    }

    public Page<Field> findAll(Pageable pageable){
        return fieldRepository.findAll(pageable);
    }

    public List<Field> getAllAvailable(){
        return fieldRepository.findAllByStatus("Disponível");
    }

    public Field findById(Long id){
        return fieldRepository.findById(id).orElse(null);
    }

    public Field update(Field field, Long id){
        Field existingField = fieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found."));
        existingField.setName(field.getName());
        existingField.setLocation(field.getLocation());
        existingField.setStatus(field.getStatus());
        return fieldRepository.save(existingField);
    }

    public void delete(Long id){
        this.fieldRepository.deleteById(id);
    }

    public Optional<Field> findByName(String name){
        return fieldRepository.findByNameEqualsIgnoreCase(name);
    }
}