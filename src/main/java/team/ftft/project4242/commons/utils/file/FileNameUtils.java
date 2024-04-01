package team.ftft.project4242.commons.utils.file;

import team.ftft.project4242.exception.file.IllegalMimeTypeException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileNameUtils {

    public static void checkMimeType(String mimeType) {
        List<String> allowedMimeTypes = Arrays.asList(
                "image/jpg", "image/jpeg", "image/png", "image/gif",
                "application/pdf", // PDF 파일의 MIME 타입
                "application/vnd.ms-powerpoint", // PPT 파일의 MIME 타입
                "application/msword", // MS Word 파일의 MIME 타입
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // MS Word (docx) 파일의 MIME 타입
                "application/vnd.ms-excel", // MS Excel 파일의 MIME 타입
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // MS Excel (xlsx) 파일의 MIME 타입
                // 다른 파일 추후 추가
        );

        if (!allowedMimeTypes.contains(mimeType)) {
            throw new IllegalMimeTypeException();
        }
    }
    public static void checkImageMimeType(String mimeType) {
        if (!(mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")
                || mimeType.equals("image/png") || mimeType.equals("image/gif"))) {
            throw new IllegalMimeTypeException();
        }
    }

    public static String fileNameConvert(String fileName){
        StringBuilder builder = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        String extension = getExtension(fileName);

        builder.append(uuid).append(".").append(extension);

        return builder.toString();
    }
    //확장자 추출 -> jpg,png
    private static String getExtension(String fileName){
        int pos = fileName.lastIndexOf(".");

        return fileName.substring(pos+1);
    }

}
