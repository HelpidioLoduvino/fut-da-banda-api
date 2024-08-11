package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<Field> save(@RequestBody Field field){
        return ResponseEntity.ok(fieldService.save(field));
    }

    @GetMapping
    public ResponseEntity<Page<Field>> findAll(Pageable pageable){
        return ResponseEntity.ok(fieldService.findAll(pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Field>> findAllAvailable() {
        return ResponseEntity.ok(fieldService.getAllAvailable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> findById(@PathVariable Long id){
        return ResponseEntity.ok(fieldService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Field> update(@RequestBody Field field, @RequestParam("id") Long id){
        return ResponseEntity.ok(fieldService.update(field, id));
    }

    @DeleteMapping()
    public ResponseEntity<Object> delete(@RequestParam("id") Long id){
        this.fieldService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Optional<Field>> findByName(@PathVariable String name){
        return ResponseEntity.ok(fieldService.findByName(name));
    }
}