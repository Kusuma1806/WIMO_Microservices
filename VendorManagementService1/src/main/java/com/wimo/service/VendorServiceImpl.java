package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wimo.exceptions.VendorNotFound;
import com.wimo.model.Vendor;
import com.wimo.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {
	//track the flow of execution and important event
    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    VendorRepository repository;
    
    @Override
    public String saveVendor(Vendor vendor) {
        logger.info("Saving vendor: {}", vendor);
        // Save the vendor to the repository
        repository.save(vendor);
        logger.info("Vendor saved successfully: {}", vendor);
        
        // Return a success message
        return "Vendor Saved!!!";
    }

    @Override
    public Vendor updateVendor(Vendor vendor) {
        logger.info("Updating vendor: {}", vendor);
        // Update the vendor in the repository
        Vendor updatedVendor = repository.save(vendor);
        logger.info("Vendor updated successfully: {}", updatedVendor);
        return updatedVendor;
    }

    @Override
    public String removeVendor(int vendorId) {
        logger.info("Removing vendor with ID: {}", vendorId);
        // Delete the vendor using the vendor ID
        repository.deleteById(vendorId);
        logger.info("Vendor removed successfully: {}", vendorId);
        
        // Return a success message
        return "Vendor Deleted!!!";
    }

    @Override
    public Vendor getVendorById(int userId) throws VendorNotFound {
        logger.info("Retrieving vendor with ID: {}", userId);
        // Retrieve the vendor using the user ID
        Optional<Vendor> optional = repository.findById(userId);
        
        // Check if the vendor exists
        if (optional.isPresent()) {
            logger.info("Vendor found: {}", optional.get());
            return optional.get();
        } else {
            logger.error("Vendor not found with ID: {}", userId);
            throw new VendorNotFound("Vendor is not Found........");
        }
    }

    @Override
    public List<Vendor> getAllVendors() {
        logger.info("Retrieving all vendors");
        // Retrieve all vendors from the repository
        List<Vendor> vendors = repository.findAll();
        logger.info("All vendors retrieved successfully");
        return vendors;
    }
}
