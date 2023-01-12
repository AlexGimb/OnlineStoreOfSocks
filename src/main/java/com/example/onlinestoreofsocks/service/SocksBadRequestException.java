package com.example.onlinestoreofsocks.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Параметры запроса отсутствуют или имеют некорректный данные")
public class SocksBadRequestException extends RuntimeException {
    public SocksBadRequestException(String message) {
        System.out.println(message);
    }
}
