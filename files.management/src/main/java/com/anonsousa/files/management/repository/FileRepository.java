package com.anonsousa.files.management.repository;

import com.anonsousa.files.management.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByFileName(String fileName);

    Boolean existsByFileName(String fileName);
}
