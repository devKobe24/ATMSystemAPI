package com.kobe.atmsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * packageName    : com.kobe.atmsystem.dto
 * fileName       : ErrorResponse
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String errorCode;
	private String message;
	private LocalDateTime timestamp;

	@Builder
	public ErrorResponse(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
