package com.my.image;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.image.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void save(String images) throws IOException {
        List<ImageDto> parsedImages = parseJsonToList(images);
        Map<String, ImageDto> dtosMap = new HashMap<>();
        parsedImages.forEach(imageDto -> dtosMap.put(imageDto.getId(), imageDto));
        imageRepository.addAll(dtosMap);
    }

    public ImageDto getById(String id) {
        return imageRepository.getById(id);
    }

    private List<ImageDto> parseJsonToList(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<List<ImageDto>>() {
        });
    }

}
