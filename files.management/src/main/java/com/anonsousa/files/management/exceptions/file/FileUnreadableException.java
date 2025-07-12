package com.anonsousa.files.management.exceptions.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileUnreadableException extends RuntimeException{
    public FileUnreadableException(Throwable exception) {
        super("Error on read file, " + exception);
    }
}
