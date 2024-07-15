package com.example.futdabandaapi.controllers;

import com.example.futdabandaapi.entities.Field;
import com.example.futdabandaapi.services.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/field")
public class FieldController {
    private final FieldService service;

    @PostMapping("/register")
    public ResponseEntity<Field> newField(@RequestBody Field field){
        return this.service.newField(field);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> findFieldById(@PathVariable Long id){
        return this.service.findFieldById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Field> updateFieldById(@PathVariable Long id, @RequestBody Field field){
        return this.service.updateFieldById(field, id);
    }

    @DeleteMapping("/{id}")
    public void deleteFieldById(@PathVariable Long id){
        this.service.deleteFieldById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Field>> findAllFields(){
        return this.service.findAllFields();
    }

    @GetMapping("/available")
    public ResponseEntity<List<Field>> findAvailableFields(){
        return this.service.findAvailableFields();
    }

    @GetMapping("/{location}")
    public ResponseEntity<List<Field>> findAllByLocation(@PathVariable String location){
        return this.service.findFieldsByLocation(location);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<Field>> findFieldByName(@PathVariable String name){
        return this.service.findFieldByName(name);
    }
}