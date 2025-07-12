package com.anonsousa.files.management.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;
    private String fileName;
    private String contentType;
    private long size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    @CreationTimestamp
    private Instant creationDate;

    public FileEntity() {}

    public FileEntity(String fileName,
                      String contentType,
                      byte[] data,
                      long size) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.data = data;
        this.size = size;
    }

    public long getFileId() {return fileId;}

    public String getFileName() {return fileName;}

    public String getContentType() {return contentType;}

    public long getSize() {return size;}

    public byte[] getData() {return data;}

    public void setData(byte[] data) {this.data = data;}

    public Instant getCreationDate() {return creationDate;}
}
