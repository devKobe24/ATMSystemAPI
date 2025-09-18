package com.kobe.atmsystem.model;

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
 * fileName       : Transaction
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
@Table(name = "transactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private BigDecimal balanceAfter;

	@CreationTimestamp
	private LocalDateTime timestamp;

	@Builder
	public Transaction(Account account, TransactionType type, BigDecimal amount, BigDecimal balanceAfter) {
		this.account = account;
		this.type = type;
		this.amount = amount;
		this.balanceAfter = balanceAfter;
	}
}
