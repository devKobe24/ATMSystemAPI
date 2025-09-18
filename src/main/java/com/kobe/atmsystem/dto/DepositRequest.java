package com.kobe.atmsystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * packageName    : com.kobe.atmsystem.dto
 * fileName       : DepositRequset
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
public class DepositRequest {
	@NotBlank(message = "계좌번호는 필수입니다.")
	private String accountNumber;

	@NotNull(message = "입금액은 필수입니다.")
	@DecimalMin(value = "1000", message = "최소 입금액은 1,000원입니다.")
	private BigDecimal amount;

	@Builder
	public DepositRequest(String accountNumber, BigDecimal amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
}
