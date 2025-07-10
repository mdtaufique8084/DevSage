package com.devSage.blog.blog_app_apis.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class  FileServiceImplementation implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get original file name
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IOException("Invalid file: File name is empty or null.");
        }

        // Generate unique file name
        String randomId = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileName = randomId.concat(extension);

        // Create full file path
        String filePath = path + File.separator + fileName;

        // Ensure directory exists
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs(); // creates parent folders if needed
        }

        // Save file to path
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        // Build full path
        String fullPath = path + File.separator + fileName;

        // Create input stream
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found at path: " + fullPath);
        }

        return new FileInputStream(file);
    }
}
