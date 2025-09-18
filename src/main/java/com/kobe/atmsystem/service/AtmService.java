package com.kobe.atmsystem.service;

import com.kobe.atmsystem.dto.BalanceResponse;
import com.kobe.atmsystem.dto.DepositRequest;
import com.kobe.atmsystem.dto.WithdrawRequest;
import com.kobe.atmsystem.exception.AccountNotFountException;
import com.kobe.atmsystem.exception.InvalidPinException;
import com.kobe.atmsystem.model.Account;
import com.kobe.atmsystem.model.Transaction;
import com.kobe.atmsystem.model.TransactionType;
import com.kobe.atmsystem.repository.AccountRepository;
import com.kobe.atmsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * packageName    : com.kobe.atmsystem.service
 * fileName       : AtmService
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
@Service
@Transactional
public class AtmService {

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final AccountService accountService;

	public AtmService(AccountRepository accountRepository, TransactionRepository transactionRepository, AccountService accountService) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
		this.accountService = accountService;
	}

	public BalanceResponse deposit(DepositRequest request) {
		Account account = accountService.getAccount(request.getAccountNumber());
		account.deposit(request.getAmount());

		recordTransaction(account, TransactionType.DEPOSIT, request.getAmount());

		return BalanceResponse.builder()
			.accountNumber(account.getAccountNumber())
			.balance(account.getBalance())
			.timestamp(LocalDateTime.now())
			.build();
	}

	public BalanceResponse withdraw(WithdrawRequest request) {
		Account account = accountService.getAccount(request.getAccountNumber());
		validatePin(account, request.getPin());
		account.withdraw(request.getAmount());

		recordTransaction(account, TransactionType.WITHDRAWAL, request.getAmount());

		return BalanceResponse.builder()
			.accountNumber(account.getAccountNumber())
			.balance(account.getBalance())
			.timestamp(LocalDateTime.now())
			.build();
	}

	@Transactional(readOnly = true)
	public BalanceResponse checkBalance(String accountNumber) {
		Account account = accountService.getAccount(accountNumber);
		return BalanceResponse.builder()
			.accountNumber(account.getAccountNumber())
			.balance(account.getBalance())
			.timestamp(LocalDateTime.now())
			.build();
	}

	private void validatePin(Account account, String pin) {
		if (!account.getPin().equals(pin)) {
			throw new InvalidPinException("PIN 번호가 일치하지 않습니다.");
		}
	}

	private void recordTransaction(Account account, TransactionType type, BigDecimal amount) {
		Transaction transaction = Transaction.builder()
			.account(account)
			.type(type)
			.amount(amount)
			.balanceAfter(account.getBalance())
			.build();
		transactionRepository.save(transaction);
	}
}
