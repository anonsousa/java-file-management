package com.anonsousa.files.management.service;

import com.anonsousa.files.management.exceptions.file.FileDuplicateException;
import com.anonsousa.files.management.exceptions.file.FileEmptyException;
import com.anonsousa.files.management.exceptions.file.FileNotAllowedException;
import com.anonsousa.files.management.exceptions.file.FileNotFoundException;
import com.anonsousa.files.management.exceptions.file.FileStorageException;
import com.anonsousa.files.management.exceptions.file.FileUnreadableException;
import com.anonsousa.files.management.model.FileEntity;
import com.anonsousa.files.management.model.dtos.FileResponseDto;
import com.anonsousa.files.management.repository.FileRepository;
import com.anonsousa.files.management.utils.AllowedFileTypes;
import com.anonsousa.files.management.utils.FileUtils;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileResponseDto storeFile(MultipartFile file) {
        validateFileNotEmpty(file);

        String detectedType;
        byte[] fileBytes;

        try {
            fileBytes = file.getBytes();
            detectedType = detectContentType(fileBytes);
            validateAllowedFileType(detectedType);
        } catch (IOException e) {
            throw new FileUnreadableException(e);
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        validateFileNameNotExists(originalFileName);

        FileEntity fileEntity = new FileEntity(
                originalFileName,
                detectedType,
                FileUtils.compressData(fileBytes),
                file.getSize()
        );
        fileEntity = fileRepository.save(fileEntity);

        return new FileResponseDto(fileEntity);
    }

    @Transactional(readOnly = true)
    public FileEntity getFile(String fileName) {

        FileEntity fileEntity = fileRepository.findByFileName(fileName)
                .orElseThrow(FileNotFoundException::new);

        byte[] decompressedData = FileUtils.decompressData(fileEntity.getData());
        fileEntity.setData(decompressedData);
        return fileEntity;
    }

    public Page<FileResponseDto> getFiles(Pageable pageable) {
        Page<FileEntity> files = fileRepository.findAll(pageable);
        return files.map(FileResponseDto::new);
    }




    private void validateFileNotEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileEmptyException();
        }
    }

    private void validateAllowedFileType(String detectedType) {
        if (!AllowedFileTypes.isAllowed(detectedType)) {
            throw new FileNotAllowedException();
        }
    }

    private void validateFileNameNotExists(String originalFileName) {
        if (fileRepository.existsByFileName(originalFileName)) {
            throw new FileDuplicateException();
        }
    }

    private String detectContentType(byte[] fileBytes) {
        return new Tika().detect(fileBytes);
    }
}
