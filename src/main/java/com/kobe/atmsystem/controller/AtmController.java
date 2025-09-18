package com.kobe.atmsystem.controller;

import com.kobe.atmsystem.dto.BalanceResponse;
import com.kobe.atmsystem.dto.DepositRequest;
import com.kobe.atmsystem.dto.WithdrawRequest;
import com.kobe.atmsystem.service.AtmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.kobe.atmsystem.controller
 * fileName       : AtmController
 * author         : kobe
 * date           : 2025. 9. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 19.        kobe       최초 생성
 */
@RestController
@RequestMapping("/api/atm")
@RequiredArgsConstructor
public class AtmController {

	private final AtmService atmService;

	@PostMapping("/deposit")
	public ResponseEntity<BalanceResponse> deposit(@Valid @RequestBody DepositRequest request) {
		BalanceResponse response = atmService.deposit(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<BalanceResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
		BalanceResponse response = atmService.withdraw(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<BalanceResponse> checkBalance(@PathVariable String accountNumber) {
		BalanceResponse response = atmService.checkBalance(accountNumber);
		return ResponseEntity.ok(response);
	}
}
