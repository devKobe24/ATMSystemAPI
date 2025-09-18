package com.kobe.atmsystem.service;

import com.kobe.atmsystem.exception.AccountNotFountException;
import com.kobe.atmsystem.model.Account;
import com.kobe.atmsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.kobe.atmsystem.service
 * fileName       : AccountService
 * author         : kobe
 * date           : 2025. 9. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 19.        kobe       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accountRepository;

	public Account getAccount(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new AccountNotFountException("계좌를 찾을 수 없습니다: " + accountNumber));
	}
}
