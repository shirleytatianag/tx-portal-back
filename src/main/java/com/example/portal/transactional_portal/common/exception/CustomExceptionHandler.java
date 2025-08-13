package com.example.portal.transactional_portal.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFoundExceptionHandler(NotFoundException foundException) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Not found");
        map.put("message", foundException.getMessage());
        log.error("ResourceNotFoundException {}", foundException.getMessage());
        return map;
    }

    @ExceptionHandler(AlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> alreadyExistsExceptionHandler(AlreadyExists alreadyExists) {
        Map<String, String> map = new HashMap<>();
        map.put("message", alreadyExists.getMessage());
        log.error("ConflictException {}", alreadyExists.getMessage());
        return map;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Bad Request");
        map.put("message", exception.getMessage());
        log.error("IllegalArgumentException {}", exception.getMessage());
        return map;
    }

}
