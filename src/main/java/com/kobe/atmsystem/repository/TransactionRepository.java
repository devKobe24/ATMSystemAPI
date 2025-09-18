package com.kobe.atmsystem.repository;

import com.kobe.atmsystem.model.Account;
import com.kobe.atmsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.kobe.atmsystem.repository
 * fileName       : TransactionRepository
 * author         : kobe
 * date           : 2025. 9. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 9. 18.        kobe       최초 생성
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByAccountOrderByTimestampDesc(Account account);
}
