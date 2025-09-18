package com.kobe.atmsystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * packageName    : com.kobe.atmsystem.dto
 * fileName       : BalanceResponse
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
public class BalanceResponse {
	private String accountNumber;
	private BigDecimal balance;
	private LocalDateTime timestamp;

	@Builder
	public BalanceResponse(String accountNumber, BigDecimal balance, LocalDateTime timestamp) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.timestamp = timestamp;
	}
}
