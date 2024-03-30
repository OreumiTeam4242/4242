package team.ftft.project4242.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadFile(MultipartFile file, String destLocation);

    String uploadImageFile(MultipartFile file, String destLocation);
}
