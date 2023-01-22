package com.example.photocollagebackend.controller;

import com.example.photocollagebackend.dto.CollageDTO;
import com.example.photocollagebackend.service.CollageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin" )
public class CollageController {

    private CollageService collageService;

    @Autowired
    public void setCollageService(CollageService collageService) {
        this.collageService = collageService;
    }

    @PostMapping("/make-collage")
    public InputStreamResource addToCollageQueue(@RequestBody CollageDTO dto) {
        return collageService.makeCollage(dto);
    }


}
