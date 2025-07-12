package com.anonsousa.files.management.exceptions.compression;

public class DecompressionException extends RuntimeException{
    public DecompressionException(Throwable exception) {
        super("Error on decompress data: ", exception);
    }
}
