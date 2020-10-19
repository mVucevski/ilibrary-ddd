package com.mvucevski.bookcatalog.application;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.mvucevski.bookcatalog.config.Constants.FILE_PATH_IMG_STORAGE;

@Service
public class ImagesService {

    private final Logger logger = LoggerFactory.getLogger(BooksService.class);

    public String uploadImage(MultipartFile file) throws IOException {

        String originalName = file.getOriginalFilename();
        String contentType = file.getContentType();

        if(!(contentType.equals("image/jpeg") || contentType.equals("image/png"))){
            logger.error("Wrong format while trying to save image with format: " + contentType);
            throw new RuntimeException("Please upload an jpeg or png image");
        }
        
        InputStream is = file.getInputStream();

        File a = new File(FILE_PATH_IMG_STORAGE);


        Files.copy(is, Paths.get(FILE_PATH_IMG_STORAGE + originalName),
                StandardCopyOption.REPLACE_EXISTING);

        logger.info("Saving image with name: " + originalName);
        return (originalName);
    }

    public byte[] getImage(String name) throws IOException {
        File file = new File(FILE_PATH_IMG_STORAGE + name);

        if(file == null || !file.exists()){
            throw new FileNotFoundException("Not found!");
        }

        InputStream newInput = FileUtils.openInputStream(file);

        if(newInput == null){
            throw new FileNotFoundException("Not found!");
        }

        return IOUtils.toByteArray(newInput);
    }

}
