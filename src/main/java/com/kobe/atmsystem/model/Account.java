package com.kobe.atmsystem.model;

import com.kobe.atmsystem.exception.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * packageName    : com.kobe.atmsystem.model
 * fileName       : Account
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
@Entity
@Getter
@Table(name = "accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String accountNumber;

	@Column(nullable = false)
	private String pin;

	@Column(nullable = false)
	private String holderName;

	@Column(nullable = false)
	private BigDecimal balance;

	@Builder
	public Account(String accountNumber, String pin, String holderName, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.holderName = holderName;
		this.balance = balance;
	}

	//== Business Logic ==//
	public void deposit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	public void withdraw(BigDecimal amount) {
		if (this.balance.compareTo(amount) < 0) {
			throw new InsufficientFundsException("진액이 부족합니다.");
		}
		this.balance = this.balance.subtract(amount);
	}
}
