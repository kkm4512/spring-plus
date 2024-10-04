package org.example.expert.domain.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final S3Uploader s3Uploader;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        String fileName = "";
        if (multipartFile != null) { // 파일 업로드한 경우에만
            try {// 파일 업로드
                fileName = s3Uploader.upload(multipartFile, "images"); // S3 버킷의 images 디렉토리 안에 저장됨
                System.out.println("fileName = " + fileName);
            } catch (IOException e) {
                return ResponseEntity.ok(e.getMessage());
            }
        }
        return ResponseEntity.ok(fileName);
    }
}
