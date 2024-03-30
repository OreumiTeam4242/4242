package team.ftft.project4242.service.file;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.utils.file.AwsProperties;
import team.ftft.project4242.commons.utils.file.FileNameUtils;
import team.ftft.project4242.exception.file.FileRoadFailedException;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AwsS3Service implements StorageService{

    private final AwsProperties awsProperties;

    private AmazonS3 s3Client;


    @PostConstruct
    private void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(awsProperties.getAccessKey(),
                awsProperties.getSecretKey());

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsProperties.getRegionStatic())
                .build();
    }


    //awsProperties 객체에 있는 bucket 정보를 파일과 함께 매개변수에 담아 실제 업로드 로직이 있는 upload() 메서드를 호출 및 return
    public String uploadFileBucket(MultipartFile file) {
        return uploadFile(file, awsProperties.getBucket());
    }

    public String uploadImageBucket(MultipartFile img){
        return uploadImageFile(img, awsProperties.getBucket());
    }


    public String uploadFile(MultipartFile file, String bucket) {
        String fileName = file.getOriginalFilename();
        String convertedFileName = FileNameUtils.fileNameConvert(fileName);

        try {
            String mimeType = new Tika().detect(file.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();

            FileNameUtils.checkMimeType(mimeType);
            metadata.setContentType(mimeType);
            s3Client.putObject(
                    new PutObjectRequest(bucket, convertedFileName, file.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException exception) {
            throw new FileRoadFailedException("파일 저장에 실패하였습니다.");
        }

        return s3Client.getUrl(bucket, convertedFileName).toString();
    }
    public String uploadImageFile(MultipartFile file, String bucket) {
        String fileName = file.getOriginalFilename();
        String convertedFileName = FileNameUtils.fileNameConvert(fileName);

        try {
            String mimeType = new Tika().detect(file.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();

            FileNameUtils.checkImageMimeType(mimeType);
            metadata.setContentType(mimeType);
            s3Client.putObject(
                    new PutObjectRequest(bucket, convertedFileName, file.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException exception) {
            throw new FileRoadFailedException("이미지 파일 저장에 실패하였습니다.");
        }

        return s3Client.getUrl(bucket, convertedFileName).toString();
    }
}