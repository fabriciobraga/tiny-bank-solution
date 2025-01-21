package com.tinybank.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tinybank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	public List<Transaction> findByLabelCointaingIgnoreCase(String keyword);
	
	@Query(value = "select o from Transaction where o.label like :keyword and o.amount >= :amount order by o.timestamp", nativeQuery = true)
	public List<Transaction> findByLabelAndValue(String keyword, BigDecimal amount);
}
