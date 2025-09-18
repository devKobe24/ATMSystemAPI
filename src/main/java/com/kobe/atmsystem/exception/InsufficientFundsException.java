package com.kobe.atmsystem.exception;

/**
 * packageName    : com.kobe.atmsystem.exception
 * fileName       : InsufficientFundsException
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
public class InsufficientFundsException extends RuntimeException {
	public InsufficientFundsException(String message) {
		super(message);
	}

	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
	}
}
