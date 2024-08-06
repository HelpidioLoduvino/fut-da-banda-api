package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService service;

    @PostMapping
    public ResponseEntity<Field> newField(@RequestBody Field field){
        return ResponseEntity.ok(service.newField(field));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> findFieldById(@PathVariable Long id){
        return ResponseEntity.ok(service.findFieldById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Field> updateFieldById(@PathVariable Long id, @RequestBody Field field){
        return ResponseEntity.ok(service.updateFieldById(field, id));
    }

    @DeleteMapping("/{id}")
    public void deleteFieldById(@PathVariable Long id){
        this.service.deleteFieldById(id);
    }

    @GetMapping
    public ResponseEntity<List<Field>> findAllFields(){
        return ResponseEntity.ok(service.findAllFields());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Field>> findAvailableFields(){
        return ResponseEntity.ok(service.findAvailableFields());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<Field>> findFieldByName(@PathVariable String name){
        return ResponseEntity.ok(service.findFieldByName(name));
    }
}