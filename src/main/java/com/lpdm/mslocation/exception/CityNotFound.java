package com.lpdm.mslocation.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFound extends RuntimeException{
    public CityNotFound(String s) {
        super(s);
    }
}
