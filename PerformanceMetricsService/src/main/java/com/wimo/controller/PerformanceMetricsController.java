package com.wimo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.model.PerformanceMetrics;
import com.wimo.service.PerformanceMetricsService;

@RestController
@RequestMapping("/metrics")
public class PerformanceMetricsController {
	@Autowired
	PerformanceMetricsService service;

    @GetMapping("/bytype/{stype}")
    public List<PerformanceMetrics> getMetrics(@PathVariable("stype") String type) {
           return service.findByType(type);
    }
    @GetMapping("/calmetrics")
    public String calculateAndSaveMetrics() {
    	service.calculateAndSaveMetrics();
    	return "Metrics Generated";
    }
 }

