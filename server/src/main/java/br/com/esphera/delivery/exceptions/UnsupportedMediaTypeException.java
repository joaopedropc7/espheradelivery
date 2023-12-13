package br.com.esphera.delivery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnsupportedMediaTypeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnsupportedMediaTypeException(String ex) {
        super(ex);
    }

    public UnsupportedMediaTypeException(String ex, Throwable cause){
        super(ex, cause);
    }
}
