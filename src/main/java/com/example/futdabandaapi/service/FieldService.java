package com.example.futdabandaapi.service;

import com.example.futdabandaapi.configuration.UploadPath;
import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.Field;
import com.example.futdabandaapi.repository.FieldRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.example.futdabandaapi.service.FileUploadService.getResourceResponseEntity;

@Service
@AllArgsConstructor
@Transactional
public class FieldService {

    private final FieldRepository fieldRepository;
    private final UploadPath uploadPath;
    private final FileUploadService fileUploadService;

    public Field save(Field field, MultipartFile file){
        try{
            String filename = fileUploadService.generateUniqueFileName(file.getOriginalFilename());
            fileUploadService.saveFile(file, uploadPath.getFieldUploadDir(), filename);
            field.setPhoto(uploadPath.getFieldUploadDir() + filename);
            return fieldRepository.save(field);
        }catch (Exception e){
            throw new RuntimeException("Error saving field", e);
        }
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

    public Field update(Field field, MultipartFile file, Long id){
        try{
            Field existingField = findById(id);
            if(existingField != null){
                existingField.setName(field.getName());
                existingField.setLocation(field.getLocation());
                existingField.setStatus(field.getStatus());
                existingField.setPrice(field.getPrice());
                existingField.setGeolocation(field.getGeolocation());
                existingField.setType(field.getType());
                if(file != null && !file.isEmpty()){
                    fileUploadService.deleteFile(existingField.getPhoto());
                    String filename = fileUploadService.generateUniqueFileName(file.getOriginalFilename());
                    fileUploadService.saveFile(file, uploadPath.getFieldUploadDir(), filename);
                    field.setPhoto(uploadPath.getFieldUploadDir() + filename);
                } else {
                    existingField.setPhoto(existingField.getPhoto());
                }
                return fieldRepository.save(existingField);
            }
            return null;
        } catch (Exception e){
            throw new RuntimeException("Error updating field", e);
        }
    }

    public void delete(Long id){
        this.fieldRepository.deleteById(id);
    }

    public Optional<Field> findByName(String name){
        return fieldRepository.findByNameEqualsIgnoreCase(name);
    }

    public Resource displayCover(Long id){
        try{
            Field field = fieldRepository.findById(id).orElse(null);
            if(field != null){
                Path path = Paths.get(field.getPhoto());
                return getResourceResponseEntity(path);
            }
        }catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return null;
    }
}