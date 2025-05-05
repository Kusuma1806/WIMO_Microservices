package com.wimo.service;

import java.util.List;

import com.wimo.exceptions.ZoneNotFound;
import com.wimo.model.Zone;


public interface ZoneService {
	public abstract String saveZone(Zone zone);

	public abstract Zone updateZone(Zone zone);

	public abstract String removeZone(int zoneId);

	public abstract Zone getZoneById(int zoneId) throws ZoneNotFound;

	public abstract List<Zone> getAllZones();

}
