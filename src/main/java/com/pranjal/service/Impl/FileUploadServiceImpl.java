package com.pranjal.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pranjal.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map<String, String> uploadFile(MultipartFile file) {
        try {
            Map upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap());
            return Map.of("secure_url", upload.get("secure_url").toString(), "public_id",
                    upload.get("public_id").toString());
        }catch(IOException e){
            e.printStackTrace();
        }
        return Map.of("secure_url", "Null", "public_id", "Null");
    }

    @Override
    public String delete(String public_id) {
        try {
            Map delete = cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
            return delete.get("result").toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return "Null";
    }
}
