package com.example.photocollagebackend.controller;

import com.example.photocollagebackend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin" )
public class FileController {

    private FileService fileService;

    @Autowired
    public void setUploadService(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestBody MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        fileService.uploadFile(file, redirectAttributes);
    }

    @PostMapping("/delete")
    public boolean deleteFile(String filename) {
        return fileService.deleteFile(filename);
    }

}
