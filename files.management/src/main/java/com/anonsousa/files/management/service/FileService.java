package com.anonsousa.files.management.service;

import com.anonsousa.files.management.exceptions.FileDuplicateException;
import com.anonsousa.files.management.exceptions.FileEmptyException;
import com.anonsousa.files.management.exceptions.FileNotFoundException;
import com.anonsousa.files.management.model.FileEntity;
import com.anonsousa.files.management.model.dtos.FileResponseDto;
import com.anonsousa.files.management.repository.FileRepository;
import com.anonsousa.files.management.utils.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileResponseDto storeFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileEmptyException();
        }

        if (fileRepository.existsByFileName(file.getOriginalFilename())) {
            throw new FileDuplicateException();
        }

        try{
            FileEntity fileEntity = new FileEntity(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    FileUtils.compressData(file.getBytes()),
                    file.getSize()
            );
            fileEntity = fileRepository.save(fileEntity);

            return new FileResponseDto(fileEntity);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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
}
