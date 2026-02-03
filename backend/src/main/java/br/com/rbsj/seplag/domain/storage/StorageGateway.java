package br.com.rbsj.seplag.domain.storage;

public interface StorageGateway {
    String generatePresignedUrl(String objectName, String contentType);
    String generatePresignedGetUrl(String objectName);
}
