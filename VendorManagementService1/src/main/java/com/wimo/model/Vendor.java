package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vendor {
	@Id
	@Positive(message="Id shouldn't be zero or negative value")
	private int vendorId;
	@NotBlank(message="Vendor name can't be null or white space character")
	@Size(min=3,max=15)
	private String vendorName;
	@NotNull(message = "contact info should not be null")
	private long contactInfo;
	@NotNull
	private int stockId;

}
