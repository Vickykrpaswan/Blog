package com.blog.services.impl;

import com.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws Exception {
        // file name
        String name=file.getOriginalFilename();
        // random name generate file
        String randomId= UUID.randomUUID().toString();
        assert name != null;
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
        // full path
        String filePath = path + File.separator + fileName1;

        // create folder if not create
        File file1 = new File(path);
        if (!file1.exists()){
            file1.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
