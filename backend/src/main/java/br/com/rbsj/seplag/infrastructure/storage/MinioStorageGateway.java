package br.com.rbsj.seplag.infrastructure.storage;

import br.com.rbsj.seplag.domain.storage.StorageGateway;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
                            //.extraQueryParams(Map.of("response-content-type", contentType))
                            .build()
            );
        } catch (Exception e) {
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
            // Se der erro ao gerar URL (ex: objeto não existe), retornamos o próprio nome ou tratamos.
            // Aqui vamos logar e retornar o path original ou lançar erro.
            // Para "listar", pode ser melhor só retornar null ou o path original se falhar.
            throw new RuntimeException("Erro ao gerar URL GET presigned do MinIO", e);
        }
    }
}
