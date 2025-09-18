package com.kobe.atmsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.kobe.atmsystem.exception
 * fileName       : GlobalExceptionHandler
 * author         : kobe
 * date           : 2025. 9. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 19.        kobe       최초 생성
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({AccountNotFountException.class, InvalidPinException.class})
	public ResponseEntity<String> handleBadRequest(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<String> handleInsufficientFunds(InsufficientFundsException ex) {
		return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
			errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		// 실제 운영 환경에서는 로그를 남기는 것이 중요합니다. e.g., log.error("Unhandled exception", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
	}
}
