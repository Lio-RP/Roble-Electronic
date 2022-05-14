package com.roble.springproject.robleelectronic.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(Long productId, MultipartFile file);
}
