package com.anonsousa.files.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileDuplicateException extends RuntimeException{
    public FileDuplicateException(){
        super("A file with this name already exists on our system");
    }
}
