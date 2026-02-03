package br.com.rbsj.seplag.application.album.upload;

public record GeneratePresignedUrlOutput(
        String url,
        String objectName
) {
}
