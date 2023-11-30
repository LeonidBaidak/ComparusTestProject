package com.baidak.test_comparus.controller;

import com.baidak.test_comparus.dto.response.ErrorResponse;
import com.baidak.test_comparus.exception.MultithreadingTaskFailedException;
import com.baidak.test_comparus.exception.TargetDataSourceDoesNotDefinedException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Slf4j
@Hidden
@ResponseBody
@ControllerAdvice
public class DefaultControllerAdvice {

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> httpMediaTypeNotAcceptableExceptionHandler(
            HttpServletRequest request, HttpMediaTypeNotAcceptableException exception) {
        log.warn(buildExceptionLog("Handle HttpMediaTypeNotAcceptableException", request), exception);
        return ResponseEntity.status(NOT_ACCEPTABLE)
                .contentType(MediaType.TEXT_PLAIN)
                .body(MediaType.APPLICATION_JSON_VALUE);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse httpMethodNotSupportedExceptionHandler(HttpServletRequest request,
                                                                HttpRequestMethodNotSupportedException exception) {
        log.warn(buildExceptionLog("Handle HttpRequestMethodNotSupportedException", request), exception);
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(METHOD_NOT_ALLOWED.value())
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MultithreadingTaskFailedException.class)
    public ErrorResponse multithreadingTaskFailedExceptionHandler(
            HttpServletRequest request, MultithreadingTaskFailedException exception) {
        log.warn(buildExceptionLog("Handle MultithreadingTaskFailedException", request), exception);
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TargetDataSourceDoesNotDefinedException.class)
    public ErrorResponse targetDataSourceDoesNotDefinedExceptionHandler(
            HttpServletRequest request, TargetDataSourceDoesNotDefinedException exception) {
        log.warn(buildExceptionLog("Handle HttpRequestMethodNotSupportedException", request), exception);
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse generalThrowableHandler(
            HttpServletRequest request, Throwable throwable) {
        log.warn(buildExceptionLog("Handle general Throwable", request), throwable);
        return ErrorResponse.builder()
                .error(throwable.getMessage())
                .status(INTERNAL_SERVER_ERROR.value())
                .build();
    }

    private String buildExceptionLog(String message, HttpServletRequest request) {
        return message +
                "\nPath: " +
                request.getRequestURI() +
                "\nMethod: " +
                request.getMethod();
    }
}
