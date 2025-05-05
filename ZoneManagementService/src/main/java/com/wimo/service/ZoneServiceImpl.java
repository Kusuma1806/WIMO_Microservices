package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.exceptions.ZoneNotFound;
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
	public Zone getZoneById(int userId) throws ZoneNotFound{
		Optional<Zone> optional=repository.findById(userId);
		if(optional.isPresent())
			return optional.get();
		else
			throw new ZoneNotFound("Zone is not Found........");
	}

	@Override
	public List<Zone> getAllZones() {
		return repository.findAll();
	}


}

