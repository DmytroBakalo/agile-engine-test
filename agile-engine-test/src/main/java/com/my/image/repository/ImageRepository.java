package com.my.image.repository;

import com.my.image.ImageDto;

import java.util.HashMap;
import java.util.Map;

public class ImageRepository {

    private static Map<String, ImageDto> dtos;

    public ImageRepository() {
        dtos = new HashMap<>();
    }

    public void addAll(Map<String, ImageDto> newDtos) {
        dtos.putAll(newDtos);
    }

    public void add(ImageDto imageDto) {
        dtos.put(imageDto.getId(), imageDto);
    }

    public void remove(ImageDto imageDto) {
        dtos.remove(imageDto.getId());
    }

    public ImageDto getById(String id) {
        return dtos.get(id);
    }

}
