package com.wimo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wimo.model.TransactionLog;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Integer> {

	List<TransactionLog> findByTypeIs(String type);

	List<TransactionLog> findByPriceBetween(Double initialPrice, Double finalPrice);

	List<TransactionLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<TransactionLog> findByStockIdIs(int stockId);

	List<TransactionLog> findByUserIdIs(int userId);

}
