package com.example.futdabandaapi.controllers;

import com.example.futdabandaapi.entities.Field;
import com.example.futdabandaapi.services.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/field")
public class FieldController {
    private final FieldService service;

    @PostMapping
    public ResponseEntity<Field> newField(@RequestBody Field field){
        return this.service.newField(field);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> findFieldById(@PathVariable String id){
        return this.service.findFieldById(Long.parseLong(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Field> updateFieldById(@PathVariable String id, @RequestBody Field field){
        return this.service.updateFieldById(field, Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public void deleteFieldById(@PathVariable String id){
        this.service.deleteFieldById(Long.parseLong(id));
    }

    @GetMapping()
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
