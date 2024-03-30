package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.service.file.FileService;

@RestController
public class FileTestController {
    private final FileService fileService;

    public FileTestController(FileService fileService) {
        this.fileService = fileService;
    }

    // TODO : 다른 api 안에 해당 메소드 합치기
    @PostMapping("/api/test/file")
    public ResponseEntity<HttpStatus> uploadFile(@RequestPart(required = false)MultipartFile file){
        fileService.createFileInfo(file);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/api/test/img")
    public ResponseEntity<HttpStatus> uploadImgFile(@RequestPart(required = false)MultipartFile file){
        fileService.createImgFileInfo(file);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
