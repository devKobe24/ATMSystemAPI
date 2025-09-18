package com.kobe.atmsystem.exception;

/**
 * packageName    : com.kobe.atmsystem.exception
 * fileName       : InvalidPinException
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
public class InvalidPinException extends RuntimeException {
	public InvalidPinException(String message) {
		super(message);
	}

	public InvalidPinException(String message, Throwable cause) {
		super(message, cause);
	}
}
