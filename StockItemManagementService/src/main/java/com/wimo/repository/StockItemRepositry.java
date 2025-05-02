package com.wimo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wimo.model.StockItem;

public interface StockItemRepositry extends JpaRepository<StockItem,Integer> {

}
