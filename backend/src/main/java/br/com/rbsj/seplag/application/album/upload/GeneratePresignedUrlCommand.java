package br.com.rbsj.seplag.application.album.upload;

public record GeneratePresignedUrlCommand(
        String albumId,
        String contentType
) {
}
