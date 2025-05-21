package com.pranjal.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.Map;

public interface FileUploadService {
    Map<String, String> uploadFile(MultipartFile file);

    String delete(String public_id);
}
