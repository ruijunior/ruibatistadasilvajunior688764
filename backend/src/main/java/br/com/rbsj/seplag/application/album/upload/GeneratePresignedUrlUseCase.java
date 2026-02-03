package br.com.rbsj.seplag.application.album.upload;

import br.com.rbsj.seplag.domain.storage.StorageGateway;
import java.util.Objects;
import java.util.UUID;

public class GeneratePresignedUrlUseCase {

    private final StorageGateway storageGateway;

    public GeneratePresignedUrlUseCase(StorageGateway storageGateway) {
        this.storageGateway = Objects.requireNonNull(storageGateway);
    }

    public GeneratePresignedUrlOutput execute(GeneratePresignedUrlCommand command) {
        String extension = getExtension(command.contentType());
        String objectName = "albums/" + command.albumId() + "/cover-" + UUID.randomUUID() + extension;

        String url = storageGateway.generatePresignedUrl(objectName, command.contentType());

        return new GeneratePresignedUrlOutput(url, objectName);
    }

    private String getExtension(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }
}
