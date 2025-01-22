package com.danthis.backend.application.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.danthis.backend.application.file.response.GetPresignedUrlResponse;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * Presigned URL 발급
   *
   * @param bucketFolder  버킷 디렉토리 이름
   * @param fileExtension 업로드 파일의 확장자
   */
  public GetPresignedUrlResponse getPresignedUrl(String bucketFolder, String fileExtension) {
    String realFileUrl = createPath(bucketFolder, fileExtension);

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        getGeneratePresignedUrlRequest(bucket, realFileUrl, fileExtension);

    String url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    System.out.println("생성된 url=" + url);

    return GetPresignedUrlResponse.of(url, realFileUrl);
  }

  /**
   * Presigned URL 요청 객체 생성
   *
   * @param bucket        S3 버킷 이름
   * @param realFileUrl   S3 버킷 내 파일의 경로
   * @param fileExtension 업로드할 파일의 확장자
   */
  private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket,
      String realFileUrl, String fileExtension) {
    String lowerCaseFileExtension = fileExtension.toLowerCase();

    String contentType = getContentType(lowerCaseFileExtension);

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucket, realFileUrl)
            .withMethod(HttpMethod.PUT)
            .withKey(realFileUrl)
            .withContentType(contentType)
            .withExpiration(getPresignedUrlExpiration());

    return generatePresignedUrlRequest;
  }

  /**
   * 파일 확장자에 따른 MIME 타입 반환 메서드
   */
  private String getContentType(String fileExtension) {
    if ("png".equals(fileExtension)) {
      return "image/png";
    }
    if ("jpg".equals(fileExtension) || "jpeg".equals(fileExtension)) {
      return "image/jpeg";
    }
    if ("gif".equals(fileExtension)) {
      return "image/gif";
    }
    if ("mp4".equals(fileExtension)) {
      return "video/mp4";
    }
    if ("mov".equals(fileExtension)) {
      return "video/quicktime";
    }
    if ("avi".equals(fileExtension)) {
      return "video/x-msvideo";
    }
    throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
  }

  /**
   * Presigned Url 만료 시간 설정
   */
  private Date getPresignedUrlExpiration() {
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();

    expTimeMillis += 1000 * 60 * 3; // 3분
    expiration.setTime(expTimeMillis);

    return expiration;
  }

  /**
   * S3에 업로드할 파일 경로 생성
   */
  private String createPath(String bucketFolder, String fileExtension) {
    String fileId = createFileId();
    return bucketFolder + "/" + fileId + "/" + fileExtension;
  }

  /**
   * 고유한 파일 ID를 생성()
   */
  private String createFileId() {
    return UUID.randomUUID().toString();
  }
}
