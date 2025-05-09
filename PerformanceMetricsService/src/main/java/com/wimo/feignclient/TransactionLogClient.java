package com.wimo.feignclient;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.TransactionLog;
@FeignClient(name="TRANSACTIONLOGMANAGEMENTSERVICE",path="/transactionlog")
public interface TransactionLogClient {
	@GetMapping("/fetchByTimestamp/{start}/{end}")
	public List<TransactionLog> findByTimestampBetween(@PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDate,
			 @PathVariable("end") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) ;

}
