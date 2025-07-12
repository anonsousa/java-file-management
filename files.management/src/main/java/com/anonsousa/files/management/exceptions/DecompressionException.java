package com.anonsousa.files.management.exceptions;

public class DecompressionException extends RuntimeException{
    public DecompressionException(String message, Exception exception) {
        super("Error on decompress data: ", exception);
    }
}
