package com.anonsousa.files.management.exceptions.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(){
        super("File not found on our system");
    }
}
