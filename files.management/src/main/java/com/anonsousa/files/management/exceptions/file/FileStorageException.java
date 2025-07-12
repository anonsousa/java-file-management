package com.anonsousa.files.management.exceptions.file;

public class FileStorageException extends RuntimeException {
    public FileStorageException(Throwable cause) {
        super("Error on save file, " + cause);
    }
}
