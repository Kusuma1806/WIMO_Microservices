package com.wimo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceMetrics {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int metricId;
	@NotBlank
	private String type;
	@NotNull
	private double value;
	@CreationTimestamp
	private LocalDateTime timestamp;
}
