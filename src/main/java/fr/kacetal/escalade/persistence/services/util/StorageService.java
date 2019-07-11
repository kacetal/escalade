package fr.kacetal.escalade.persistence.services.util;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    
    String save(MultipartFile file);
    
    boolean delete(String fileName);
}