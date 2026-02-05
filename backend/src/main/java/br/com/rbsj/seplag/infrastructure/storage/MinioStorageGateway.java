package br.com.rbsj.seplag.infrastructure.storage;

import br.com.rbsj.seplag.domain.storage.StorageGateway;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MinioStorageGateway implements StorageGateway {

    private final MinioClient minioClient;
    private final String bucketName;
    private final int expiration;

    public MinioStorageGateway(
            MinioClient minioClient,
            @Value("${minio.bucket-name}") String bucketName,
            @Value("${minio.presigned-url-expiration}") int expiration
    ) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
        this.expiration = expiration;
    }

    @Override
    public String generatePresignedUrl(String objectName, String contentType) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expiration, TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar URL presigned PUT do MinIO: objectName={}", objectName, e);
            throw new RuntimeException("Erro ao gerar URL presigned do MinIO", e);
        }
    }

    @Override
    public String generatePresignedGetUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expiration, TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar URL presigned GET do MinIO: objectName={}", objectName, e);
            throw new RuntimeException("Erro ao gerar URL GET presigned do MinIO", e);
        }
    }
}
