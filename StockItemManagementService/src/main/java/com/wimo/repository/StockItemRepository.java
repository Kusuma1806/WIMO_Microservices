package com.wimo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wimo.model.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem,Integer> {

	List<StockItem> findByStockCategoryIs(String stockCategory);

	List<StockItem> findByZoneIdIs(int zoneId);

}
