package com.kobe.atmsystem.exception;

/**
 * packageName    : com.kobe.atmsystem.exception
 * fileName       : AccountNotFountException
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
public class AccountNotFountException extends RuntimeException {
	public AccountNotFountException(String message) {
		super(message);
	}

	public AccountNotFountException(String message, Throwable cause) {
		super(message, cause);
	}
}
