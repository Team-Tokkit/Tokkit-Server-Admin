package dev.admin.global.controller;

import dev.admin.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/s3")
public class S3Controller {

    private final S3Service s3Service;

    // Presigned URL을 생성하는 API
    @GetMapping("/presigned-url/{fileName}")
    public ResponseEntity<String> getPresignedUrl(
            @PathVariable String fileName,
            @RequestParam String contentType
    ) {
        URL url = s3Service.generatePresignedUrl(fileName, contentType);
        return ResponseEntity.ok(url.toString());
    }

    // S3에서 이미지를 가져오는 API
    @GetMapping ("images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        byte[] imageBytes = s3Service.getImageBytes(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }
}
