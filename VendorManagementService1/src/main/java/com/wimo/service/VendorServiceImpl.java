package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.exceptions.VendorNotFound;
import com.wimo.model.Vendor;
import com.wimo.repository.VendorRepository;


@Service
//@AllArgsConstructor
public class VendorServiceImpl implements VendorService {
// Track the flow of execution and important events
    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    VendorRepository repository;
    
    /**
     * Saves a vendor to the repository.
     *
     * @param vendor the vendor to be saved
     * @return a success message indicating the vendor was saved
     */
    @Override
    public String saveVendor(Vendor vendor) {
        logger.info("Saving vendor: {}", vendor);
        repository.save(vendor);
        logger.info("Vendor saved successfully: {}", vendor);
        return "Vendor Saved!!!";
    }

    /**
     * Updates a vendor in the repository.
     *
     * @param vendor the vendor to be updated
     * @return the updated vendor
     */
    @Override
    public Vendor updateVendor(Vendor vendor) {
        logger.info("Updating vendor: {}", vendor);
        Vendor updatedVendor = repository.save(vendor);
        logger.info("Vendor updated successfully: {}", updatedVendor);
        return updatedVendor;
    }

    /**
     * Removes a vendor by its ID.
     *
     * @param vendorId the ID of the vendor to be removed
     * @return a success message indicating the vendor was deleted
     */
    @Override
    public String removeVendor(int vendorId) {
        logger.info("Removing vendor with ID: {}", vendorId);
        repository.deleteById(vendorId);
        logger.info("Vendor removed successfully: {}", vendorId);
        return "Vendor Deleted!!!";
    }

    /**
     * Retrieves a vendor by its ID.
     *
     * @param userId the ID of the vendor to be retrieved
     * @return the vendor with the specified ID
     * @throws VendorNotFound if the vendor is not found
     */
    @Override
    public Vendor getVendorById(int userId) throws VendorNotFound {
        logger.info("Retrieving vendor with ID: {}", userId);
        Optional<Vendor> optional = repository.findById(userId);
        
        if (optional.isPresent()) {
            logger.info("Vendor found: {}", optional.get());
            return optional.get();
        } else {
            logger.error("Vendor not found with ID: {}", userId);
            throw new VendorNotFound("Vendor is not Found........");
        }
    }

    /**
     * Retrieves all vendors from the repository.
     *
     * @return a list of all vendors
     */
    @Override
    public List<Vendor> getAllVendors() {
        logger.info("Retrieving all vendors");
        List<Vendor> vendors = repository.findAll();
        logger.info("All vendors retrieved successfully");
        return vendors;
    }
}
