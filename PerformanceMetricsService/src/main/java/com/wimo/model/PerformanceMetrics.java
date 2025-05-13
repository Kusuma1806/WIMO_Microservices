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

	@NotBlank(message = "Type cannot be blank")
	private String type;

	@NotNull(message = "Value cannot be null")
	private double value;

	@CreationTimestamp
	private LocalDateTime timestamp;

	public PerformanceMetrics(String type,double value) {
		super();
		this.type = type;
		this.value = value;
	}
}
