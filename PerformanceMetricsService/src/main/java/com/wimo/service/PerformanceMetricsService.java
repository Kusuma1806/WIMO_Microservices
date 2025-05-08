package com.wimo.service;

import java.util.List;

import com.wimo.model.PerformanceMetrics;

public interface PerformanceMetricsService {
	public void calculateAndSaveMetrics() ;

	public List<PerformanceMetrics> findByType(String type);

}
