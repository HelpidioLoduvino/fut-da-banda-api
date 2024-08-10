package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<Field> newField(@RequestBody Field field){
        return ResponseEntity.ok(fieldService.newField(field));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> findFieldById(@PathVariable Long id){
        return ResponseEntity.ok(fieldService.findFieldById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Field> updateFieldById(@PathVariable Long id, @RequestBody Field field){
        return ResponseEntity.ok(fieldService.updateFieldById(field, id));
    }

    @DeleteMapping("/{id}")
    public void deleteFieldById(@PathVariable Long id){
        this.fieldService.deleteFieldById(id);
    }

    @GetMapping
    public ResponseEntity<Page<Field>> findAllFields(Pageable pageable){
        return ResponseEntity.ok(fieldService.findAll(pageable));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<Field>> findFieldByName(@PathVariable String name){
        return ResponseEntity.ok(fieldService.findFieldByName(name));
    }
}