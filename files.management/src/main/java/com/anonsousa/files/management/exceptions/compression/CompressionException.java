package com.anonsousa.files.management.exceptions.compression;

public class CompressionException extends RuntimeException{
    public CompressionException(Throwable exception) {
        super("Error on compress data: ", exception);
    }
}
