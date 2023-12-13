package br.com.esphera.delivery.models.DTOS.responseDtos;

public record UploadFileResponse(
        String fileName,
        String fileDownloadUri,
        String fileType,
        long size
) {
    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
