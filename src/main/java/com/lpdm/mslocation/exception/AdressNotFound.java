package com.lpdm.mslocation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdressNotFound extends RuntimeException{
    public AdressNotFound(String s) {
        super(s);
    }
}
