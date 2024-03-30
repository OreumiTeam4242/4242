package team.ftft.project4242.service.file;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.File;
import team.ftft.project4242.domain.ImgFile;
import team.ftft.project4242.repository.FileRepository;
import team.ftft.project4242.repository.ImgFileRespository;

// s3 저장소로 저장과
// 파일 path을 따로 관리하는 파일 도메인에서의 저장
@Service
public class FileService {
    private final AwsS3Service awsS3Service;
    private final FileRepository fileRepository;
    private final ImgFileRespository imgFileRespository;

    public FileService(AwsS3Service awsS3Service,FileRepository fileRepository,ImgFileRespository imgFileRespository){
        this.awsS3Service = awsS3Service;
        this.fileRepository = fileRepository;
        this.imgFileRespository = imgFileRespository;
    }

    @Transactional
    public void createFileInfo(MultipartFile file){
        if(!file.isEmpty()){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            File fileInfo = new File(s3FilePath);
            fileRepository.save(fileInfo);
        }

    }
    @Transactional
    public void createImgFileInfo(MultipartFile file){
        if(!file.isEmpty()){
            String s3FilePath = awsS3Service.uploadImageBucket(file);
            ImgFile imgFileInfo = new ImgFile(s3FilePath);
            imgFileRespository.save(imgFileInfo);
        }

    }
}
