package com.anonsousa.files.management.controller;

import com.anonsousa.files.management.model.FileEntity;
import com.anonsousa.files.management.model.dtos.FileResponseDto;
import com.anonsousa.files.management.service.FileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(
            value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<FileResponseDto> storeFile(@Valid @RequestParam("file") MultipartFile file) {
        FileResponseDto response = fileService.storeFile(file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.fileId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping(
            value = "/download",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName) {
        FileEntity response = fileService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.getContentType()))
                .contentLength(response.getSize())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(response.getData());

    }

    @GetMapping(
            value = "/files",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Page<FileResponseDto>> getFiles(Pageable pageable){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        fileService.getFiles(pageable)
                );
    }
}
