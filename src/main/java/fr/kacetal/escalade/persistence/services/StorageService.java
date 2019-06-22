package fr.kacetal.escalade.persistence.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    
    String save(MultipartFile file);
    
    boolean delete(String fileName);
}