package com.yh.yhadmin.controller;

import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.util.StaticUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/6 16:25
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${yhadmin.filepath}")
    private String filePath;

    @Value("${yhadmin.imgpath}")
    private String imgPath;

    @PostMapping("/file")
    public ResponseEntity uploadFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        originalFilename = StaticUtil.uniqueKeyByTime(new Date()) + originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String s = StaticUtil.uploadFile(multipartFile.getBytes(), filePath, originalFilename);
        return ResponseEntity.success("/file/" + originalFilename);
    }

    @PostMapping("/image")
    public ResponseEntity uploadImg(MultipartFile[] file){
        List<MultipartFile> multipartFiles = Arrays.asList(file);
        StringBuffer path = new StringBuffer();
        multipartFiles.stream().forEach( m -> {
            String originalFilename = m.getOriginalFilename();
            originalFilename = StaticUtil.uniqueKeyByTime(new Date()) + originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            try {
                StaticUtil.uploadFile(m.getBytes(), imgPath, originalFilename);
                path.append("/img/" + originalFilename + ",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.success(path.toString().substring(0,path.length()-1));
    }
}
