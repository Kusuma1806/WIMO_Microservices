package com.wimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wimo.dto.TransactionLog;
import com.wimo.dto.Zone;
import com.wimo.feignclient.TransactionLogClient;
import com.wimo.feignclient.ZoneClient;
import com.wimo.model.PerformanceMetrics;
import com.wimo.repository.PerformanceMetricsRepository;
import com.wimo.service.PerformanceMetricsServiceImpl;

@SpringBootTest
class PerformanceMetricsServiceApplicationTests {

	    @Mock
	    private PerformanceMetricsRepository repository;

	    @Mock
	    private ZoneClient zoneClient;

	    @Mock
	    private TransactionLogClient transactionClient;

	    @InjectMocks
	    private PerformanceMetricsServiceImpl performanceMetricsService;
	    
	    private Zone zone;
	    private PerformanceMetrics performanceMetrics;

	    @BeforeEach
	    public void setUp() {
	        performanceMetrics = new PerformanceMetrics("Turnover", 5.0);
	    }

	    @Test
	    public void testCalculateAndSaveMetrics() {
	        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
	        LocalDateTime endDate = LocalDateTime.now();
	        List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000),
					new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
	        List<Zone> zones = Arrays.asList(new Zone(1, "soaps", 5, 3), new Zone(1, "soaps", 5, 3));

	        when(transactionClient.findByTimestampBetween(startDate, endDate)).thenReturn(transactionLogs);
	        when(zoneClient.viewAll()).thenReturn(zones);
	        when(repository.save(performanceMetrics)).thenReturn(performanceMetrics);

	        performanceMetricsService.calculateAndSaveMetrics();
	    }

	    @Test
	    public void testFindByType() {
	        when(repository.findByTypeIs("Turnover")).thenReturn(List.of(performanceMetrics));
	        List<PerformanceMetrics> result = performanceMetricsService.findByType("Turnover");

	        assertEquals(List.of(performanceMetrics), result);
	    }
	}

