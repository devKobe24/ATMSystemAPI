package com.kobe.atmsystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * packageName    : com.kobe.atmsystem.dto
 * fileName       : WithdrawRequest
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
public class WithdrawRequest {
	@NotBlank(message = "계좌번호는 필수입니다.")
	private String accountNumber;

	@NotBlank(message = "PIN은 필수입니다.")
	private String pin;

	@NotNull(message = "출금액은 필수입니다.")
	@DecimalMin(value = "1000", message = "최소 출금액은 1,000원입니다.")
	private BigDecimal amount;

	@Builder
	public WithdrawRequest(String accountNumber, String pin, BigDecimal amount) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.amount = amount;
	}
}
