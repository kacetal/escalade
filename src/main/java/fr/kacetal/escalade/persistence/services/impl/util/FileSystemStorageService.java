package fr.kacetal.escalade.persistence.services.impl.util;

import fr.kacetal.escalade.persistence.services.util.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService {
    
//    @Value("${upload.path}")
    private Path uploadPath = Paths.get("/static/images/");
    
    @Value("${default.imagename}")
    private String defaultImageName;
    
    @Override
    public String save(MultipartFile file) {
        try {
            
            if (!uploadPath.toFile().exists()) {
                log.info(uploadPath + " is not exist");
                Files.createDirectories(uploadPath);
            }
            
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            
            String uuidFile = UUID.randomUUID().toString();
            
            String resultFileName;
            
            if (file.isEmpty()) {
                log.info("File is empty, default file name is returned");
                return defaultImageName;
            }
            if (originalFileName.contains("..")) {
                // This is a security check
                log.warn("Cannot save file with relative path outside current directory {}", originalFileName);
                return defaultImageName;
            }
            
            resultFileName = uuidFile + "_" + originalFileName;
            
            Path resultPath = uploadPath.resolve(resultFileName);
            
            file.transferTo(resultPath);
            
            return resultFileName;
        } catch (IOException e) {
            log.error("IOException during uploading file: \n", e);
            return defaultImageName;
        }
    }
    
    @Override
    public boolean delete(String fileName) {
        var e = new UnsupportedOperationException("Method is not implemented");
        log.error("Method delete in the StorageService is not implemented", e);
        throw e;
    }
}
