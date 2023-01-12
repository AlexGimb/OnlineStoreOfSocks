package com.example.onlinestoreofsocks.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Произошла ошибка, не зависящая от вызывающей стороны")
public class SocksInternalServerErrorException extends RuntimeException {
    public SocksInternalServerErrorException(String message) {
        System.out.println(message);
    }
}
