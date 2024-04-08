package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.ftft.project4242.service.file.AwsS3Service;

@Tag(name = "파일 다운로드")
@RestController
public class FileController {

    private final AwsS3Service awsS3Service;

    public FileController(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    // 다운로드
    @Operation(summary = "파일 다운로드 API",description = "s3 저장소에 업로드한 파일 다운로드")
    @GetMapping("/api/file/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "file") String file) {

        //  ex. image=https://board-example.s3.ap-northeast-2.amazonaws.com/2b8359b2-de59-4765-8da0-51f5d4e556c3.jpg

        byte[] data = awsS3Service.downloadFile(file);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
                .body(resource);
    }
}
