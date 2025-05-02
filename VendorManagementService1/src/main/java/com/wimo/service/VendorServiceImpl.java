package com.wimo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.model.Vendor;
import com.wimo.repository.VendorRepository;
@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	VendorRepository repository;
	
	@Override
	public String saveVendor(Vendor vendor) {
		repository.save(vendor);
		return "Vendor Saved!!!";
	}

	@Override
	public Vendor updateVendor(Vendor vendor) {
		return repository.save(vendor);
	}

	@Override
	public String removeVendor(int vendorId) {
		repository.deleteById(vendorId);
		return "Vendor Deleted!!!";
	}

	@Override
	public Vendor getVendorById(int vendorId) {
		return repository.findById(vendorId).get();
	}

	@Override
	public List<Vendor> getAllVendors() {
		return repository.findAll();
	}

}
