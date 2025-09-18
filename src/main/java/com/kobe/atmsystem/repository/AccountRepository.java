package com.kobe.atmsystem.repository;

import com.kobe.atmsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName    : com.kobe.atmsystem.repository
 * fileName       : AccountRepository
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(String accountNumber);
}
