package com.anonsousa.files.management.exceptions;

public class CompressionException extends RuntimeException{
    public CompressionException(String message, Exception exception) {
        super("Error on compress data: ", exception);
    }
}
