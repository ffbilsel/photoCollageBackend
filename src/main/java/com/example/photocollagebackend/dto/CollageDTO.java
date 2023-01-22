package com.example.photocollagebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CollageDTO {

    private List<String> files;
    private Direction direction;
    private int border;
    private String color;
}
