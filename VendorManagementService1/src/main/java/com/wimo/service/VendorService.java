package com.wimo.service;

import java.util.List;

import com.wimo.model.Vendor;

public interface VendorService {
	public abstract String saveVendor(Vendor vendor);

	public abstract Vendor updateVendor(Vendor vendor);

	public abstract String removeVendor(int vendorId);

	public abstract Vendor getVendorById(int vendorId);

	public abstract List<Vendor> getAllVendors();

}
