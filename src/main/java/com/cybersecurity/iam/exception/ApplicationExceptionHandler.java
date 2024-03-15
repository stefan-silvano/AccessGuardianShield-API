package com.cybersecurity.iam.exception;

import com.cybersecurity.iam.exception.type.BadRequestException;
import com.cybersecurity.iam.exception.type.ConflictException;
import com.cybersecurity.iam.exception.type.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApplicationExceptionResponse> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        ApplicationExceptionResponse errorResponse = ApplicationExceptionResponse.builder()
                .path(request.getRequestURI())
                .error("Not Found")
                .message(exception.getMessage())
                .status(HttpServletResponse.SC_NOT_FOUND)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApplicationExceptionResponse> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        ApplicationExceptionResponse errorResponse = ApplicationExceptionResponse.builder()
                .path(request.getRequestURI())
                .error("Bad Request")
                .message(exception.getMessage())
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ApplicationExceptionResponse> handleConflictException(ConflictException exception, HttpServletRequest request) {
        ApplicationExceptionResponse errorResponse = ApplicationExceptionResponse.builder()
                .path(request.getRequestURI())
                .error("Conflict")
                .message(exception.getMessage())
                .status(HttpServletResponse.SC_CONFLICT)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        ApplicationExceptionResponse errorResponse = ApplicationExceptionResponse.builder()
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .error(exception.getMessage())
                .message("Authorization is required to access this resource")
                .status(HttpServletResponse.SC_FORBIDDEN)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
