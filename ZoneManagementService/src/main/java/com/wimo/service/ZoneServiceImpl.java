package com.wimo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.model.Zone;
import com.wimo.repository.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    ZoneRepository repository;

    @Override
	public String saveZone(Zone zone) {
		repository.save(zone);
		return "Zone Saved!!!";
	}

	@Override
	public Zone updateZone(Zone zone) {
		return repository.save(zone);
	}

	@Override
	public String removeZone(int zoneId) {
		repository.deleteById(zoneId);
		return "Zone Deleted!!!";
	}

	@Override
	public Zone getZoneById(int zoneId) {
		return repository.findById(zoneId).get();
	}

	@Override
	public List<Zone> getAllZones() {
		return repository.findAll();
	}


}

