package com.wimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.wimo.exceptions.VendorNotFound;
import com.wimo.model.Vendor;
import com.wimo.repository.VendorRepository;
import com.wimo.service.VendorServiceImpl;

@SpringBootTest
class VendorManagementServiceApplicationTests {

	@Mock
	VendorRepository repository;
 
	@InjectMocks
	VendorServiceImpl service;

	@Test
	void saveVendorTest() {
		Vendor vendor =new Vendor(2,"kusumaaaa",98765544444L);
		Mockito.when(repository.save(vendor)).thenReturn(vendor);
		String response = service.saveVendor(vendor);
		assertEquals("Vendor Saved!!!", response);
	}
	@Test
	void updateVendorTest() {
	Vendor vendor = new Vendor(2,"kusumaaaa",98765544444L);
	vendor.setVendorId(1);

		Mockito.when(repository.save(vendor)).thenReturn(vendor);
 
		Vendor updatedVendor = service.updateVendor(vendor);
		assertEquals(vendor, updatedVendor);
	}

	@Test
	void removeVendorTest() {
		int vendorId = 1;
 		Mockito.doNothing().when(repository).deleteById(vendorId);

		String response = service.removeVendor(vendorId);
		assertEquals("Vendor Deleted!!!", response);
	}

	@Test
	void getVendorTest() throws VendorNotFound {
		int vendorId = 1;
		Vendor vendor = new Vendor(2,"kusumaaaa",98765544444L);
		vendor.setVendorId(vendorId);
 
		Mockito.when(repository.findById(vendorId)).thenReturn(Optional.of(vendor));
 
		Vendor foundVendor= service.getVendorById(vendorId);
		assertEquals(vendor, foundVendor);
	}

	@Test
	void getVendorNotFoundTest() {
		int vendorId = 1;
		Mockito.when(repository.findById(vendorId)).thenReturn(Optional.empty());
 
		assertThrows(VendorNotFound.class, () -> {
			service.getVendorById(vendorId);
		});
	}

	@Test
	void getAllVendorsTest() {
		List<Vendor> vendors = Arrays.asList(new Vendor(2,"kusumaaaa",98765544444L),
			new Vendor(2,"kusumaaaa",98765544444L));
 
		Mockito.when(repository.findAll()).thenReturn(vendors);
 
		List<Vendor> allVendors = service.getAllVendors();
		assertEquals(vendors, allVendors);
	}

}
