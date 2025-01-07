package com.danthis.backend.api;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();

    List<String> errors = bindingResult.getFieldErrors()
                                       .stream()
                                       .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                       .toList();

    String combinedErrors = String.join(", ", errors);

    log.warn("MethodArgumentNotValidException: {}", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), combinedErrors));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    log.info("BusinessException: {}", e.getMessage());
    return ResponseEntity.status(e.getCode()).body(ErrorResponse.of(e.getCode(), e.getMessage()));
  }
}
