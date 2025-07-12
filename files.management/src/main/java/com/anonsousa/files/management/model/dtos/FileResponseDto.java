package com.anonsousa.files.management.model.dtos;

import com.anonsousa.files.management.model.FileEntity;

import java.time.Instant;

public record FileResponseDto(
        long fileId,
        String fileName,
        String contentType,
        long size,
        Instant creationDate) {

    public FileResponseDto(FileEntity fileEntity) {
        this(
                fileEntity.getFileId(),
                fileEntity.getFileName(),
                fileEntity.getContentType(),
                fileEntity.getSize(),
                fileEntity.getCreationDate()
        );
    }
}
