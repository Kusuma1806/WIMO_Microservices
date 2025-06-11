package com.wimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorNotificationRequest {
    private String stockName;
    private String vendorEmail;
}
