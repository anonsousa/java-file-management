package com.anonsousa.files.management.exceptions.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNotAllowedException extends RuntimeException{
    public FileNotAllowedException() {
        super("Content Type Not allowed!");
    }
}
